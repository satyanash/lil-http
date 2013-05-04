package satyanash.jHTTP;

import java.net.*;
import java.io.*;

public class MyServer extends Thread
{
	private ServerSocket socket;
	
	public static final int DEFAULT_PORT=8080;

	public MyServer() throws IOException
	{
		socket = new ServerSocket( DEFAULT_PORT);
	}
	public MyServer( int port) throws IOException
	{
		socket = new ServerSocket( port);
	}

	@Override
	public void run()
	{
		while( true)
		{
			try{
				new MyServiceThread( socket.accept() ).start();
			}catch( IOException e)
			{
			}
		}
	}
}
