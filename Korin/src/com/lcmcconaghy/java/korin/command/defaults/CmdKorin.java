package com.lcmcconaghy.java.korin.command.defaults;

import com.lcmcconaghy.java.korin.Korin;
import com.lcmcconaghy.java.korin.command.HelpCommand;
import com.lcmcconaghy.java.korin.command.KorinCommand;

public class CmdKorin extends KorinCommand
{
	
	// ========= //
	// SINGLETON //
	// ========= //
	
	private static CmdKorin i = new CmdKorin();
	public static CmdKorin get() { return CmdKorin.i; }
	
	// =========== //
	// CONSTRUCTOR //
	// =========== //
	
	public CmdKorin()
	{
		super(Korin.get(), "korin", "kr");
		
		this.addSubCommand(new HelpCommand( this ));
		this.addSubCommand(CmdKorinVersion.get());
	}
}
