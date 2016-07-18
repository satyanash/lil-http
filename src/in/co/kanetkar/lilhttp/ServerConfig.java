package in.co.kanetkar.lilhttp;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;


public class ServerConfig
{
	public static final int DEFAULT_PORT=8080;
	public static final String DEFAULT_SERVER_STRING="lilHTTP Server";
	public static final String DEFAULT_HTTP_ROOT="./";
	public static final String DEFAULT_ERROR_PAGE_DIR= DEFAULT_HTTP_ROOT + "/errdir/";

	private int port;
	private String serverString;
	private String httpRoot;
	private String errorPageDir;


	public static ServerConfig read(File configFile) throws FileNotFoundException, IOException{
	 	Properties confFile = new Properties();
		confFile.load( new FileInputStream(configFile));

		ServerConfig config = new ServerConfig();

		config.port = Integer.parseInt( confFile.getProperty( "PORT", Integer.toString( DEFAULT_PORT)));
		config.serverString = confFile.getProperty( "SERVER_STRING", DEFAULT_SERVER_STRING);
		config.httpRoot = confFile.getProperty( "HTTP_ROOT", DEFAULT_HTTP_ROOT);
		config.errorPageDir = confFile.getProperty( "ERROR_PAGE_DIR", DEFAULT_ERROR_PAGE_DIR);
		return config;
	}

	public static ServerConfig defaultConfig(){
		ServerConfig config = new ServerConfig();

		config.port = DEFAULT_PORT;
		config.serverString = DEFAULT_SERVER_STRING;
		config.httpRoot = DEFAULT_HTTP_ROOT;
		config.errorPageDir = DEFAULT_ERROR_PAGE_DIR;
		return config;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServerString() {
		return serverString;
	}

	public void setServerString(String serverString) {
		this.serverString = serverString;
	}

	public String getHttpRoot() {
		return httpRoot;
	}

	public void setHttpRoot(String httpRoot) {
		this.httpRoot = httpRoot;
	}

	public String getErrorPageDir() {
		return errorPageDir;
	}

	public void setErrorPageDir(String errorPageDir) {
		this.errorPageDir = errorPageDir;
	}
}
