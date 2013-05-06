package satyanash.jHTTP;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URLConnection;
import java.net.FileNameMap;

import java.util.Date;

public class HttpResponder
{
	public static void respond( HttpResponse response, OutputStream clientOutputStream, boolean autoFlush) throws IOException
	{
		System.out.println("Serving....Request....");
		HttpResponse.HttpResponseStatus status = response.getStatus();
		//If request has already failed due to some reason
		if( status.getStatusCode() >= 300 )
		{
			clientOutputStream.write( generateHttpErrorPage( status));
			System.err.println("Error: " + status + " occured while serving request");
		}
		else
		{
			try{
				clientOutputStream.write( generateHttpResponsePage( response));
				System.out.println("Request Written");
			}catch( FileNotFoundException f)
			{
				//A 404 - NOT FOUND error has occured
				response.setStatus( HttpResponse.HttpResponseStatus.HTTP_404_NOT_FOUND);
				clientOutputStream.write( generateHttpErrorPage( HttpResponse.HttpResponseStatus.HTTP_404_NOT_FOUND));
				System.err.println("Error: " + response.getStatus() + " occured while serving request\n" + f);
			}
		}

		if( autoFlush)
			clientOutputStream.flush();
	}

	protected static byte[] generateHttpResponsePage( HttpResponse response) throws FileNotFoundException, IOException
	{
		HttpResponse.HttpResponseStatus status = response.getStatus();
		StringBuffer buf = new StringBuffer();

		File resource = new File( "./" + response.getResource());
		FileInputStream fr = new FileInputStream( resource);

		buf.append("HTTP/1.1 " + status.getStatusCode() + " " + status.getReason());
		buf.append("\nServer: " + HttpServer.SERVER_STRING);
		buf.append("\nContent-Type: " + URLConnection.getFileNameMap().getContentTypeFor( response.getResource()));
		buf.append("\nContent-Length: " + resource.length());
		buf.append("\nLast-Modified: " + new Date( resource.lastModified()));
		buf.append("\nDate: " + new Date());
		buf.append("\n\n");

		//Convert StringBuffer to a byte[]
		byte[] headers = buf.toString().getBytes();

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bytes.write(headers, 0, headers.length);

		int oneByte;
		//Go byte by byte, till end of file is reached
		while( (oneByte = fr.read()) != -1)
			bytes.write(oneByte);

		return bytes.toByteArray();
	}

	protected static byte[] generateHttpErrorPage( HttpResponse.HttpResponseStatus status) throws IOException
	{
		StringBuffer buf = new StringBuffer();

		buf.append("HTTP/1.1 " + status.getStatusCode() + " " + status.getReason());
		buf.append("\nServer: " + HttpServer.SERVER_STRING);
		buf.append("\nContent-Type: text/html");
		//buf.append("\nContent-Length: ");
		//buf.append("\nLast-Modified: " + response.getLastModified());
		buf.append("\nDate: " + new Date());
		buf.append("\n\n");

		//Convert StringBuffer to a byte[]
		byte[] headers = buf.toString().getBytes();

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bytes.write(headers, 0, headers.length);

		try{
			//Try a custom Error file first.
			FileInputStream fr = new FileInputStream( "./" + HttpServer.ERROR_PAGE_DIR + "/" + status.getStatusCode() + ".html");
			int oneByte;
			//Go byte by byte, till end of file is reached
			while( (oneByte = fr.read()) != -1)
				bytes.write(oneByte);
		}catch( FileNotFoundException f)
		{
			//If custom Error file does not exist
			//buf.append("<!DOCTYPE html><html><body><h1>" + status.getStatusCode() + " - " + status.getReason() + "</h1></body></html>");
			byte[] err = 
			("<!DOCTYPE html><html><body><h1>" + status.getStatusCode() + " - " + status.getReason() + "</h1></body></html>").getBytes();
			bytes.write( err, 0, err.length);
		}

		return bytes.toByteArray();
	}
}
