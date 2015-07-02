package satyanash.jHTTP;

import java.net.ServerSocket;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Properties;

public class HttpServer extends Thread
{
	private ServerSocket socket;
	
	public static final int DEFAULT_PORT=8080;
	public static final String DEFAULT_SERVER_STRING="Quick jHTTP Server";
	public static final String DEFAULT_HTTP_ROOT="./";
	public static final String DEFAULT_ERROR_PAGE_DIR= DEFAULT_HTTP_ROOT + "/errdir/";

	public static final int PORT;
	public static final String SERVER_STRING;
	public static final String HTTP_ROOT;
	public static final String ERROR_PAGE_DIR;

	static
	{
		Properties confFile = new Properties();
		try{
			confFile.load( new FileInputStream("jHTTP.properties"));
		}catch( FileNotFoundException f)
		{
			//If file does not exist, make a sample config using default values
			try{
				System.err.println("Configuration file 'jHTTP.properties' not found");
				PrintWriter pr = new PrintWriter(new FileWriter("./jHTTP.properties"));
				pr.println("###");
				pr.println("#Automatically written, since one did not exist already");
				pr.println("#####");
				pr.println("PORT=" + DEFAULT_PORT);
				pr.println("SERVER_STRING=" + DEFAULT_SERVER_STRING);
				pr.println("HTTP_ROOT=" + DEFAULT_HTTP_ROOT);
				pr.println("ERROR_PAGE_DIR=" + DEFAULT_ERROR_PAGE_DIR);
				pr.flush();
				pr.close();
			}catch( IOException e)
			{}
		}
		catch( IOException e) {
			System.err.println("IOException Occured");
			System.exit(-1);
		}

		PORT = Integer.parseInt( confFile.getProperty( "PORT", Integer.toString( DEFAULT_PORT)));
		SERVER_STRING = confFile.getProperty( "SERVER_STRING", DEFAULT_SERVER_STRING);
		HTTP_ROOT = confFile.getProperty( "HTTP_ROOT", DEFAULT_HTTP_ROOT);
		ERROR_PAGE_DIR = confFile.getProperty( "ERROR_PAGE_DIR", DEFAULT_ERROR_PAGE_DIR);
	}

	public HttpServer() throws IOException
	{
		socket = new ServerSocket( PORT);
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

	public static void main( String[] args)
	{
		System.out.println("Starting Server..");
		try{
			new HttpServer().start();
		}catch( IOException e)
		{ }
		System.out.println("Server Started..");
	}
}
