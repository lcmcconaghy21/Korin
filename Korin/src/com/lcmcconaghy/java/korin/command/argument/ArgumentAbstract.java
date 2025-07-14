package com.lcmcconaghy.java.korin.command.argument;

public abstract class ArgumentAbstract<T> implements IArgument<T>
{
	
	// ====== //
	// FIELDS //
	// ====== //
	
	protected boolean optional = false;
	protected String identifier;
	
	// ======== //
	// OPTIONAL //
	// ======== //
	
	public void toggleOptional(boolean arg0)
	{
		this.optional = arg0;
	}
	
	public boolean isOptional()
	{
		return this.optional;
	}
	
	// ====== //
	// NAMING //
	// ====== //
	
	public void setName(String arg0)
	{
		this.identifier = arg0;
	}
	
	public String getName()
	{
		return this.identifier;
	}
	
}
