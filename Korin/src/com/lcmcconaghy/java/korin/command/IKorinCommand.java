package com.lcmcconaghy.java.korin.command;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface IKorinCommand
{
	
	public boolean run();
	
	public boolean execute();
	
	public List<String> tabComplete(CommandSender sender, String[] args);

	public String getPermission();
	
}
