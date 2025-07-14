package com.lcmcconaghy.java.korin.command.argument;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public interface IArgument<T>
{
	
	public T read(String arg0);
	public List<String> tabComplete(CommandSender arg0, Location arg1);
	public boolean isValid(String arg0);
	
}
