package com.lcmcconaghy.java.korin.command;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;

public class KorinSpigotCommand extends BukkitCommand implements TabCompleter
{
	
	// ====== //
	// FIELDS //
	// ====== //
	
	protected KorinCommand command;
	
	protected CommandSender sender;
	protected String[] args;
	
	// =========== //
	// CONSTRUCTOR //
	// =========== //
	
	public KorinSpigotCommand(KorinCommand arg0)
	{
		super(arg0.commandLabel);
		
		this.command = arg0;
	}
	
	// ========= //
	// EXECUTION //
	// ========= //
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args)
	{
		adjustExecution(sender, args);
		
		this.command.execute();
		
		return true;
	}
	
	// ============= //
	// INSTANTIATION //
	// ============= //
	
	public void adjustExecution(CommandSender arg0, String[] args)
	{
		this.sender = arg0;
		this.args = args;
		
		this.command.sender = arg0;
		this.command.args = args;
	}
	
	// ============ //
	// TAB COMPLETE //
	// ============ //
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
	{
		return this.command.tabComplete(sender, args);
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args)
	{
		return onTabComplete(sender, this, alias, args);
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location)
	{
		return onTabComplete(sender, this, alias, args);
	}
	
}
