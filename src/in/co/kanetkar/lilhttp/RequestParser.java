package in.co.kanetkar.lilhttp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class RequestParser {

	public static HttpRequest parseRequest(InputStream clientInputStream) throws InvalidHttpRequestException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(clientInputStream));

		ArrayList<String> requestArray = new ArrayList<>();
		String testLine;
		//Wait for an empty line containing only a CRLF \r\n
		while (!(testLine = br.readLine()).isEmpty()) {
			//Don't echo unless required
			//System.out.println(testLine);
			requestArray.add(testLine);
		}

		HttpRequest req = new HttpRequest();

		String[] lines = requestArray.toArray(new String[]{});

		try {
			//Split the request line
			String[] req_line = lines[0].split(" +");

			//Find out the Method
			if (req_line[0].contains("GET")) {
				req.setMethod(HttpRequest.Method.GET);
			} else if (req_line[0].contains("POST")) {
				req.setMethod(HttpRequest.Method.POST);
			} else if (req_line[0].contains("PUT")) {
				req.setMethod(HttpRequest.Method.PUT);
			} else if (req_line[0].contains("DELETE")) {
				req.setMethod(HttpRequest.Method.DELETE);
			} else if (req_line[0].contains("TRACE")) {
				req.setMethod(HttpRequest.Method.TRACE);
			} else if (req_line[0].contains("HEAD")) {
				req.setMethod(HttpRequest.Method.HEAD);
			} else {
				throw new InvalidHttpRequestException(HttpResponse.HttpResponseStatus.HTTP_400_BAD_REQUEST);
			}

			//URL decode and resource string
			req.setResource(URLDecoder.decode(req_line[1], "UTF-8"));

			//Find out the HTTP Version
			if (req_line[2].contains("HTTP/1.1")) {
				req.setProtocolVersion(1.1f);
			} else if (req_line[2].contains("HTTP/1.0")) {
				req.setProtocolVersion(1.0f);
			} else {
				throw new InvalidHttpRequestException(HttpResponse.HttpResponseStatus.HTTP_505_HTTP_VERSION_NOT_SUPPORTED);
			}

			//Parse the Header field
			for (String line : lines) {
				String[] nv = line.split(":");

				if (nv[0].contains("Host")) {
					req.setHost(nv[1]);
				}
				if (nv[0].contains("Referer")) {
					req.setReferer(nv[1]);
				}
				if (nv[0].contains("User-Agent")) {
					req.setUserAgent(nv[1]);
				}
				if (nv[0].contains("Accept-Language")) {
					req.setAcceptLanguage(nv[1]);
				}
				if (nv[0].contains("Accept-Encoding")) {
					req.setAcceptEncoding(nv[1]);
				}
				if (nv[0].contains("Accept")) {
					req.setAccept(nv[1]);
				}
				if (nv[0].contains("Connection")) {
					req.setConnection(nv[1]);
				}
			}
		} catch (ArrayIndexOutOfBoundsException a) {
			throw new InvalidHttpRequestException(HttpResponse.HttpResponseStatus.HTTP_400_BAD_REQUEST);
		}

		return req;
	}
}
