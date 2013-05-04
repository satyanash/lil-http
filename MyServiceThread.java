package satyanash.jHTTP;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;

public class MyServiceThread extends Thread
{
	Socket client;
	PrintWriter pr;
	BufferedReader br;
	HttpRequest request;
	HttpResponse response;

	public MyServiceThread() { }

	public MyServiceThread( Socket client) {
		this.client = client;
	}

	@Override
	public void run()
	{
		try{
			System.out.println("Client: " + client);
			pr = new PrintWriter( client.getOutputStream());
			br = new BufferedReader( new InputStreamReader( client.getInputStream()));

			ArrayList<String> lines = new ArrayList<String>();
			String line;
			//Wait for an empty line containing only a CRLF \r\n
			while( ! (line = br.readLine()).isEmpty())
			{
				System.out.println(line);
				lines.add( line);
			}
			System.out.println("Out of the loop");

			try{
				request = HttpParser.parseRequest( lines.toArray( new String[] {}));
			}catch( InvalidHttpRequestException e) {
				response.setStatus( e.getStatus());
			}

			System.out.println("request parsed");

			pr.println("HTTP/1.1 200 OK");
			pr.println("Content-Type : text/html");
			pr.println("Date: " + new Date());
			pr.println("Server: Custom Java Based");
			pr.println();
			pr.println("<!DOCTYPE html><html><body><h1> " + request.getMethod()  + " "
				+ request.getHost() + " "
				+ request.getConnection() + " "
				+ request.getProtocolVersion() + " "
				+ request.getResource() + " "
				+ " </h1></body></html>");

			pr.flush();
		}
		catch( IOException e)
		{
		}
		finally
		{
			try{
				client.close();
			}catch( IOException e) { }
		}
	}
}
