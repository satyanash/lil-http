package in.co.kanetkar.lilhttp;

import java.io.IOException;
import java.io.File;

public class Main {

	public static void main(String[] args) throws IOException {
		ServerConfig config;
		try {
			File configFile = new File(args[0]);
			config = ServerConfig.read(configFile);
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			config = ServerConfig.defaultConfig();
		}
		System.out.println("Starting Server..");
		HttpServer server = new HttpServer(config);
		server.registerRequestHandler(new FileRequestHandler(config));
		server.registerRequestHandler(new DirectoryRequestHandler(config));
		server.registerRequestHandler(new AssetRequestHandler(config));
		server.registerRequestHandler(new RootRequestHandler(config));
		server.start();
		System.out.println("Server Started..");
	}
}
