package com.decker.essentiallib;

public class Reactor
{
    
    public Reactor()
    {

    }

    private static Reactor instance;

    public static Reactor getReactor()
    {
	if (instance == null)
	{
	    instance = new Reactor();
	}
	return instance;
    }
}
