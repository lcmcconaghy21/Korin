package com.lcmcconaghy.java.korin.command.defaults;

import com.lcmcconaghy.java.korin.Korin;
import com.lcmcconaghy.java.korin.command.KorinCommand;

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
		
		
		return true;
	}
	
}
