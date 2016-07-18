package in.co.kanetkar.lilhttp;

import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

public class HttpResponder {

	public static String getServerTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}

	public static void respond(HttpResponse response, OutputStream clientOutputStream) throws IOException {
		StringBuffer buf = new StringBuffer();
		Formatter formatter = new Formatter(buf);

		HttpResponse.HttpResponseStatus status = response.getStatus();
		formatter.format("HTTP/%f %s %s", response.getProtocolVersion(), status.getStatusCode(), status.getReason());
		formatter.format("\nServer: %s", response.getServerString());
		if( response.getContentType() != null)
			formatter.format("\nContent-Type: %s", response.getContentType());
		if( response.getContentLength() != null)
			formatter.format("\nContent-Length: %s", response.getContentLength());
		if( response.getLastModified()!= null)
			formatter.format("\nLast-Modified: %s", getServerTime(response.getLastModified()));
		if( response.getDate()!= null)
			formatter.format("\nDate: %s", getServerTime(response.getDate()));
		if( response.getLocation()!= null)
			formatter.format("\nLocation: %s", response.getLocation());
		formatter.format("\n\n");

		//Convert StringBuffer to a byte[]
		byte[] headers = buf.toString().getBytes();

		clientOutputStream.write(headers);
		if( response.getContent() != null)
			clientOutputStream.write(response.getContent());
		clientOutputStream.flush();
	}
}
