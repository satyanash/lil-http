package satyanash.jHTTP;

import java.net.*;
import java.io.*;
import java.util.Date;

public class HttpServiceThread extends Thread
{
	Socket client;
	HttpRequest request;
	HttpResponse response;

	public HttpServiceThread() { }

	public HttpServiceThread( Socket client) {
		this.client = client;
	}

	@Override
	public void run()
	{
		try{
			System.out.println("Client: " + client);

			response = new HttpResponse();
			try{
				request = HttpParser.parseRequest( client.getInputStream());
				System.out.println("Request Parsed");
				response.setResource( request.getResource());
				response.setStatus( HttpResponse.HttpResponseStatus.HTTP_200_OK);
			}catch( InvalidHttpRequestException e) {
				//Inform client of bad request
				response.setStatus( e.getStatus());
			}
			HttpResponder.respond( response, client.getOutputStream(), true);
			System.out.println("Request Served");
		}
		catch( IOException e)
		{
			System.err.println("Socket Error..");
		}
		finally
		{
			try{
				client.close();
			}catch( IOException e) {
				System.err.println("Error while closing socket..");
			}
		}
	}
}
