package com.lcmcconaghy.java.korin.command.defaults;

import org.bukkit.plugin.PluginDescriptionFile;

import com.lcmcconaghy.java.korin.Korin;
import com.lcmcconaghy.java.korin.command.KorinCommand;
import com.lcmcconaghy.java.korin.util.Message;

public class CmdKorinVersion extends KorinCommand
{
	
	//
	// SINGLETON
	//
	
	private static CmdKorinVersion i = new CmdKorinVersion();
	public static CmdKorinVersion get() { return CmdKorinVersion.i; }
	
	// =========== //
	// CONSTRUCTOR //
	// =========== //
	
	public CmdKorinVersion()
	{
		super(Korin.get(), "version", "v");
	}
	
	// ======= //
	// VERSION //
	// ======= //
	
	public boolean run()
	{
		PluginDescriptionFile desc = Korin.get().getDescription();
		
		msg(new Message().data("Korin Version", 
		"Name", desc.getName(),
		"Version", desc.getVersion()
		));
		
		return true;
	}
	
}
