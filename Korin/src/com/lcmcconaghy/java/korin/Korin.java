package com.lcmcconaghy.java.korin;

import java.lang.reflect.Field;
import java.util.logging.Level;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import com.lcmcconaghy.java.korin.command.defaults.CmdKorin;

public class Korin extends KorinPlugin
{
	
	// ========= //
	// SINGLETON //
	// ========= //
	
	private static Korin i;
	public static Korin get() { return Korin.i; }
	
	// =========== //
	// CONSTRUCTOR //
	// =========== //
	
	public Korin()
	{
		Korin.i = this;
	}
	
	// ========== //
	// JAVAPLUGIN //
	// ========== //
	
	public Plugin getPlugin()
	{
		return this;
	}
	
	// ================ //
	// COMMAND MAPPINGS //
	// ================ //
	
	protected SimpleCommandMap commandMap;
	
	public boolean retrieveCommandMap()
	{
		Field fieldCommandMap = null;
		
		try
		{
			fieldCommandMap = this.getServer().getClass().getDeclaredField("commandMap");
		}
		catch (NoSuchFieldException | SecurityException e)
		{
			e.printStackTrace();
			return false;
		}
		
		fieldCommandMap.setAccessible(true);
		
		try
		{
			this.commandMap = (SimpleCommandMap) fieldCommandMap.get( this.getServer() );
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public SimpleCommandMap getCommandMap()
	{
		return this.commandMap;
	}
	
	// ================= //
	// STARTUP / DISABLE //
	// ================= //
	
	@Override
	public void onStart()
	{
		log("Getting <b>CommandMap<e>...");
		
		if (! retrieveCommandMap())
		{
			log(Level.SEVERE, "Unable to attach <b>CommandMap <c>object. Contact developer.");
			
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		log("Successfully attached <b>CommandMap <e>object.");
		log("Loading <d>Korin <e>commands...");
		
		this.registerCommands(CmdKorin.get());
		
		log("Commands initialized!");
	}

	@Override
	public void onEnd()
	{
		
	}

}
