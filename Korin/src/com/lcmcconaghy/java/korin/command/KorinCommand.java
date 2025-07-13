package com.lcmcconaghy.java.korin.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.korin.IKorinPlugin;
import com.lcmcconaghy.java.korin.command.argument.IArgument;

public abstract class KorinCommand implements IKorinCommand
{
	// ====== //
	// FIELDS //
	// ====== //
	
	protected CommandSender sender;
	protected String[] args;
	
	protected final String commandLabel;
	protected String description;
	protected final KorinSpigotCommand spigotCommand;
	protected final String permission;
	
	// Korin fields
	protected ArrayList<String> commandAliases = new ArrayList<String>();
	protected LinkedHashMap<String, IKorinCommand> subCommands = new LinkedHashMap<String, IKorinCommand>();
	protected LinkedList<IArgument<?>> arguments = new LinkedList<IArgument<?>>();
	
	// ======== //
	// ALIASING //
	// ======== //
	
	public Collection<String> getAliases()
	{
		return this.commandAliases;
	}
	
	public String getLabel()
	{
		return this.commandAliases.get(0);
	}
	
	// =========== //
	// CONSTRUCTOR //
	// =========== //
	
	/**
	 * Constructor object, containing all command aliases
	 * @param aliases
	 */
	public KorinCommand(IKorinPlugin plugin, String... aliases)
	{
		for (String alias : aliases)
		{
			this.commandAliases.add(alias);
		}
		
		this.commandLabel = aliases[0];
		
		this.spigotCommand = new KorinSpigotCommand( this );
		
		this.permission = plugin.getPlugin().getDescription().getMain().substring(0, plugin.getPlugin().getDescription().getMain().lastIndexOf('.'))
				          + commandLabel.toLowerCase();
	}
	
	// ============== //
	// TAB COMPLETION //
	// ============== //
	
	@Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(name -> name.startsWith(args[0].toLowerCase()))
                    .filter(name -> {
                        IKorinCommand subCmd = subCommands.get(name);
                        return subCmd.getPermission() == null || sender.hasPermission(subCmd.getPermission());
                    })
                    .collect(Collectors.toList());
        }

        if (args.length > 1 && subCommands.containsKey(args[0].toLowerCase())) {
            IKorinCommand subCommand = subCommands.get(args[0].toLowerCase());
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            return subCommand.tabComplete(sender, subArgs);
        }

        return new ArrayList<String>();
    }
	
	// ============== //
	// INITIALIZATION //
	// ============== //
	
	public KorinSpigotCommand getSpigotCommand()
	{
		return this.spigotCommand;
	}
	
	// =========== //
	// PERMISSIBLE //
	// =========== //
	
	public String getPermission()
	{
		return this.permission;
	}
	
	public boolean hasPermission(CommandSender arg0)
	{
		return arg0.hasPermission(permission);
	}
	
	// ========= //
	// EXECUTION //
	// ========= //
	
	@Override
	public boolean execute()
	{
		if (! hasPermission(sender) ) 
		{
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        if (args.length > 0 && subCommands.containsKey(args[0].toLowerCase()))
        {
            KorinCommand subCommand = (KorinCommand) subCommands.get(args[0].toLowerCase());
            
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            subCommand.args = subArgs;
            
            return subCommand.execute();
        }

        return this.spigotCommand.execute(sender, this.commandLabel, args);
	}
}
