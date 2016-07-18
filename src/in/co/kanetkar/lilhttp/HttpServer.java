package in.co.kanetkar.lilhttp;

import in.co.kanetkar.lilhttp.requesthandler.RequestHandler;
import in.co.kanetkar.lilhttp.error.HttpErrorHandler;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class HttpServer extends Thread
{
	private ServerSocket socket;
	private final ServerConfig serverConfig;
	private final List<RequestHandler> requestHandlers = new ArrayList<>();
	
	public HttpServer(ServerConfig serverConfig) throws IOException
	{
		this.serverConfig = serverConfig;

		Properties velocityConfig = new Properties();
		velocityConfig.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityConfig.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		Velocity.init(velocityConfig);
	}

	@Override
	public void run()
	{
		HttpErrorHandler errorHandler = new HttpErrorHandler(serverConfig);
		try{
			socket = new ServerSocket( serverConfig.getPort());

			while( true)
			{
				new HttpServiceThread( socket.accept(), requestHandlers, errorHandler).start();
			}
		}catch( IOException e)
		{
			System.err.println("Could not open Socket, closing...");
			System.exit(-1);
		}
	}

	public void registerRequestHandler(RequestHandler requestHandler){
		requestHandlers.add(requestHandler);
	}
}
