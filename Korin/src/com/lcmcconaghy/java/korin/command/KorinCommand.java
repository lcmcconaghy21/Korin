package com.lcmcconaghy.java.korin.command;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import com.lcmcconaghy.java.korin.IKorinPlugin;
import com.lcmcconaghy.java.korin.command.argument.ArgumentAbstract;
import com.lcmcconaghy.java.korin.command.argument.IArgument;
import com.lcmcconaghy.java.korin.util.Message;

public abstract class KorinCommand implements IKorinCommand
{
	// ====== //
	// FIELDS //
	// ====== //
	
	protected CommandSender sender;
	protected String[] args;
	
	protected final String commandLabel;
	protected String description;
	protected String usage;
	protected final KorinSpigotCommand spigotCommand;
	protected final String permission;
	
	// Korin fields
	protected ArrayList<String> commandAliases = new ArrayList<String>();
	protected LinkedHashMap<String, IKorinCommand> subCommands = new LinkedHashMap<String, IKorinCommand>();
	protected LinkedHashMap<String, ArgumentAbstract<?>> arguments = new LinkedHashMap<String, ArgumentAbstract<?>>();
	
	protected int argTracker = -1;
	
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
	
	// ========= //
	// ARGUMENTS //
	// ========= //
	
	public IArgument<?> addArgument(String arg0, Class<ArgumentAbstract<?>> arg1)
	{
		ArgumentAbstract<?> target = null;
		
		try
		{
			target = arg1.getConstructor().newInstance();
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		
		this.arguments.put(arg0, target);
		
		return target;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readArgument(int position)
	{
		String[] keys = this.arguments.keySet().toArray( new String[this.arguments.keySet().size()] );
		ArgumentAbstract<?> argument = this.arguments.get( keys[position] );
		
		if (argument.shouldConcat())
		{
			String parsedText = "";
			
			for (int i = this.argTracker; i<this.args.length; i++)
			{
				parsedText += this.args[i];
			}
			
			return (T) argument.read(parsedText);
		}
		
		return (T) argument.read( this.args[ position ] );
	}
	
	public <T> T readArgument()
	{
		if (this.argTracker >= this.args.length)
		{
			this.argTracker = -1;
		}
		
		this.argTracker++;
		
		return readArgument( this.argTracker );
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
	
	// ===== //
	// USAGE //
	// ===== //
	
	public String getUsage()
	{
		if (this.usage != null) return this.usage;
		
		this.usage = new Message("<e>/" + this.getLabel() + " ").toString();
		
		return this.usage;
	}
	
	// ========= //
	// EXECUTION //
	// ========= //
	
	@Override
	public boolean execute()
	{
		if (!hasPermission(sender))
		{
            msg("<c>You don't have permission to use this command!");
            return true;
        }

        if ( args.length > 0 && subCommands.containsKey( args[0].toLowerCase() ) )
        {
            KorinCommand subCommand = (KorinCommand) subCommands.get( args[0].toLowerCase() );
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            
            subCommand.sender = sender;
            subCommand.args = subArgs;
            
            return subCommand.execute();
        }

        if ( ! subCommands.isEmpty() )
        {
            return run();
        }
        
        String[] keyArray = new String[arguments.size()];
        
        // Validate arguments if no subcommands
        for (int i = 0; i < arguments.size() && i < args.length; i++)
        {
            ArgumentAbstract<?> argument = arguments.get( keyArray[i] );
            if (! argument.isValid( args[i] ) )
            {
                msg("<c>Invalid argument for " + argument.getName() + ": " + args[i]);
                msg("<c>Usage: " + getUsage());
                return false;
            }
        }

        // Check if required arguments are missing
        if (args.length < arguments.size())
        {
            for (int i = args.length; i < arguments.size(); i++)
            {
                if (!arguments.get( keyArray[i] ).isOptional())
                {
                    sender.sendMessage("<c>Missing required argument: " + arguments.get( keyArray[i] ).getName());
                    return false;
                }
            }
        }

        return run();
	}
	
	// ========= //
	// MESSAGING //
	// ========= //
	
	public void msg(String arg0)
	{
		String msg = new Message(arg0).format().toString();
		
		this.sender.sendMessage(msg);
	}
	
	public void msg(Message arg0)
	{
		msg( arg0.toString() );
	}
	
	// ======== //
	// RUNNABLE //
	// ======== //
		
	public boolean run()
	{
		return false;
	}
	
	// =========== //
	// SUBCOMMANDS //
	// =========== //
	
	public void addSubCommand(KorinCommand arg0)
	{
		for (String alias : arg0.getAliases())
		{
			this.subCommands.put(alias, arg0);
		}
	}
	
}
