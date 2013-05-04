package satyanash.jHTTP;

import java.io.IOException;

public class MainClass
{
	public static void main( String[] args) throws IOException
	{
		System.out.println("Starting Server..");
		new MyServer().start();
		System.out.println("Server Started..");
	}
}
