package com.lcmcconaghy.java.korin.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.bukkit.command.CommandSender;

public abstract class KorinCommand implements IKorinCommand
{
	// ====== //
	// FIELDS //
	// ====== //
	
	protected CommandSender sender;
	protected String[] args;
	
	protected String commandLabel;
	protected String description;
	protected KorinSpigotCommand spigotCommand;
	
	// Korin fields
	protected ArrayList<String> commandAliases = new ArrayList<String>();
	protected LinkedHashMap<String, KorinCommand> subCommands = new LinkedHashMap<String, KorinCommand>();
	
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
	public KorinCommand(String... aliases)
	{
		for (String alias : aliases)
		{
			this.commandAliases.add(alias);
		}
		
		this.commandLabel = aliases[0];
		
		this.spigotCommand = new KorinSpigotCommand( this );
	}
	
	// ============== //
	// INITIALIZATION //
	// ============== //
	
	public KorinSpigotCommand getSpigotCommand()
	{
		return this.spigotCommand;
	}
	
	// ========= //
	// EXECUTION //
	// ========= //
	
	public void execute()
	{
		
	}
}
