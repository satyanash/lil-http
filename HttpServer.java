package satyanash.jHTTP;

import java.net.*;
import java.io.*;

public class HttpServer extends Thread
{
	private ServerSocket socket;
	
	public static final int DEFAULT_PORT=8080;
	public static final String ERROR_PAGE_DIR="./errdir/";
	public static final String SERVER_STRING="Custom Java Based";

	public HttpServer() throws IOException
	{
		socket = new ServerSocket( DEFAULT_PORT);
	}
	public HttpServer( int port) throws IOException
	{
		socket = new ServerSocket( port);
	}

	@Override
	public void run()
	{
		while( true)
		{
			try{
				new HttpServiceThread( socket.accept() ).start();
			}catch( IOException e)
			{
				System.err.println("Could not open Socket, closing...");
				System.exit(-1);
			}
		}
	}
}
