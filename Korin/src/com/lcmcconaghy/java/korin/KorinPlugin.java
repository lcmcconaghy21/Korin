package com.lcmcconaghy.java.korin;

import com.lcmcconaghy.java.korin.command.KorinCommand;
import com.lcmcconaghy.java.korin.util.Message;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class KorinPlugin extends JavaPlugin implements IKorinPlugin
{
	// ====== //
	// FIELDS //
	// ====== //
	
	protected final ConsoleCommandSender console = Bukkit.getConsoleSender();
	
	// ==================== //
	// JAVAPLUGIN OVERRIDES //
	// ==================== //
	
	public void onEnable()
	{
		long timeStart = System.currentTimeMillis();
		log("Starting <a>" + this.getDescription().getName() + "<e>...");
		
		this.onStart();
		
		long finalTimeStart = System.currentTimeMillis() - timeStart;
		
		log("Startup finished! Took <b>" + Message.timify(finalTimeStart) +"<e>.");
	}
	
	public void onDisable()
	{
		this.onEnd();
	}
	
	// =========== //
	// COMMANDDMAP //
	// =========== //
	
	public boolean registerCommand(KorinCommand cmd)
	{
		return Korin.get().commandMap.register(cmd.getLabel(), cmd.getSpigotCommand());
	}
	
	public void registerCommands(KorinCommand... commands)
	{
		for (KorinCommand cmd : commands)
		{
			registerCommand(cmd);
		}
	}
	
	// ====== //
	// LOGGER //
	// ====== //
	
	public void log(String... args)
	{
		for (String arg : args)
		{
			log(Level.INFO, arg);
		}
	}
	
	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public void log(Level arg0, String arg1)
	{
		String messageFormatted = new Message(arg1).format().toString();
		
		if ( arg0 == Level.SEVERE )
		{
			console.sendMessage( ChatColor.DARK_RED + "[SEVERE]:" + ChatColor.RED + messageFormatted );
		}
		else if ( arg0 == Level.WARNING )
		{
			console.sendMessage( ChatColor.GOLD + "[WARNING]: " + ChatColor.RED + messageFormatted );
		}
		else
		{
			console.sendMessage( ChatColor.GREEN + "[INFO]: " + ChatColor.YELLOW + messageFormatted );
		}
	}
	
}
