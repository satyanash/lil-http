package in.co.kanetkar.lilhttp;

import java.net.Socket;
import java.io.IOException;
import java.util.List;

public class HttpServiceThread extends Thread {

	private Socket client;
	private List<RequestHandler> requestHandlers;
	private HttpErrorHandler errorHandler;

	private HttpServiceThread() {
	}

	public HttpServiceThread(Socket client, List<RequestHandler> handlers, HttpErrorHandler errorHandler) {
		this.client = client;
		this.requestHandlers = handlers;
		this.errorHandler = errorHandler;
	}

	@Override
	public void run() {
		try {
			HttpResponse response = null;
			HttpRequest request = null;
			try {
				System.out.println("Client: " + client);

				request = RequestParser.parseRequest(client.getInputStream());
				System.out.println("Request Parsed: " + request);

				for (RequestHandler handler : requestHandlers) {
					if (handler.match(request)) {
						response = handler.handle(request);
						System.out.println("Request Served");
						break;
					}
				}
				if (response == null) {
					throw new InvalidHttpRequestException(HttpResponse.HttpResponseStatus.HTTP_404_NOT_FOUND);
				}
				HttpResponder.respond(response, client.getOutputStream());
			} catch (HttpException he) {
				response = errorHandler.handlerError(request, he.getStatus());
				HttpResponder.respond(response, client.getOutputStream());
			}
		} catch (IOException e) {
			System.err.println("Socket Error..");
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				System.err.println("Error while closing socket..");
			}
		}
	}

}
