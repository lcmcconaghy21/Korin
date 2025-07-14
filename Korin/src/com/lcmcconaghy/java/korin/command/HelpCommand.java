package com.lcmcconaghy.java.korin.command;

import com.lcmcconaghy.java.korin.util.Message;

public class HelpCommand extends KorinCommand
{
	
	protected KorinCommand parent;
	protected String header;
	
	// =========== //
	// CONSTRUCTOR //
	// =========== //
	
	public HelpCommand(KorinCommand parent)
	{
		super(parent.plugin, "help", "h");
		
		this.parent = parent;
		KorinCommand tracer = parent;
		
		String tempHeader = "";
		
		while (tracer != null && tracer.parent != null)
		{
			tracer = tracer.parent;
			
			tempHeader = tracer.getLabel() + " " + tempHeader;
		}
	}
	
	// ======== //
	// RUNNABLE //
	// ======== //
	
	public boolean run()
	{
		Message ret = new Message().data(parent.getLabel());
		
		for (String cmdLabel : this.parent.subCommands.keySet())
		{
			ret.append("\n<b>/" + this.header + " " + cmdLabel + ": <e> placeholder" );
		}
		
		msg(ret);
		
		return true;
	}
	
}
