package com.lcmcconaghy.java.korin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class KorinSpigotCommand extends BukkitCommand
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
	
}
