package javaProjectV2;

import uca.jetty.Container;

public class Driver 
{
	public static void main(String[] args) 
	{
		Container.startServer(81);
		Container.addHandler(new CustomerJSON(),"/customerjson");
		Container.addHandler(new StoreJSON(),"/orderjson");
		Container.addHandler(new Receipt(), "/Receipt");
		Container.addHandler(new PlacingOrder(), "/Order");
	}	
}