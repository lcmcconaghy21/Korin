package com.lcmcconaghy.java.korin.util;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Message
{
	
	// ====== //
	// FIELDS //
	// ====== //
	
	private static HashMap<String, String> formatting = new HashMap<String, String>();
	
	protected String message;
	
	// ====== //
	// STATIC //
	// ====== //
		
	static
	{
		formatting.put("<0>", "§0");
		formatting.put("<1>", "§1");
		formatting.put("<2>", "§2");
		formatting.put("<3>", "§3");
		formatting.put("<4>", "§4");
		formatting.put("<5>", "§5");
		formatting.put("<6>", "§6");
		formatting.put("<7>", "§7");
		formatting.put("<8>", "§8");
		formatting.put("<9>", "§9");
		
		formatting.put("<a>", "§a");
		formatting.put("<b>", "§b");
		formatting.put("<c>", "§c");
		formatting.put("<d>", "§d");
		formatting.put("<e>", "§e");
		formatting.put("<f>", "§f");
		
		formatting.put("<k>", "§k");
		formatting.put("<l>", "§l");
		formatting.put("<m>", "§m");
		formatting.put("<n>", "§n");
		formatting.put("<o>", "§o");
	}
	
	// =========== //
	// CONSTRUCTOR //
	// =========== //
	
	public Message(String arg0)
	{
		this.message = arg0;
	}
	
	public Message()
	{
		this.message = "";
	}
	
	// ========== //
	// FORMATTING //
	// ========== //
	
	public Message format()
	{
		for ( String key : formatting.keySet() )
		{
			if ( !this.message.contains(key) ) continue;
			
			this.message = message.replaceAll( key, formatting.get(key) );
		}
		
		return this;
	}
	
	public static String timify(long millis)
	{
		 String complete = "";
		 
		 if (millis > TimeUnit.DAYS.toMillis(1))
		 {
			 long days = TimeUnit.MILLISECONDS.toDays(millis);
			 int years = (int) (days/365);
			 int months = (int) (days/30);
			 
			 if (years > 0)
			 {
				 complete += "<b>" + years + "<e> year(s), ";
				 
				 months -= years*12;
				 days -= years*365;
			 }
			 if (years > 0)
			 {
				 complete += "<b>" + months + "<e> month(s), ";
				 
				 days -= months*30;
			 }
			 
			 complete += "<b>" + TimeUnit.MILLISECONDS.toDays(millis) + "<e> day(s), ";
		 }
		 
		 if (millis > TimeUnit.HOURS.toMillis(1))
		 {
			 complete += "<b>" + TimeUnit.MILLISECONDS.toHours(millis) + "<e> hour(s), ";
		 }
		 
		 if (millis > TimeUnit.MINUTES.toMillis(1))
		 {
			 complete += "<b>" + TimeUnit.MILLISECONDS.toMinutes(millis) + "<e> minute(s) and ";
		 }
		 
		 complete += "<b>" + TimeUnit.MILLISECONDS.toSeconds(millis) + "<e> second(s)";
		 
		 return complete;
	}
	
	public Message data(String arg0, String arg1, String arg2, String...args)
	{
		if (args.length % 2 != 0) throw new IllegalStateException();
		
		String ret = "<e>======={ <d><l>"+ arg0 + "<e>}=======\n";
		ret += "<e>" + arg1 + ": <b>" + arg2;
		
		for (int i = 0; i < (args.length/2); i++)
		{
			int keyPos = i*2;
			int objectPos = i*2+1;
			
			ret += "\n<e>" + args[keyPos] + ": <b>" + args[objectPos];
		}
		
		this.message = ret;
		
		return this;
	}
	
	// ========= //
	// STRINGIFY //
	// ========= //
	
	@Override
	public String toString()
	{
		return this.message;
	}
	
}
