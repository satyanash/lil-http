package satyanash.jHTTP;

import java.util.Date;

public class HttpRequest
{
	/*public static final int GET = 1;
	public static final int POST = 2;
	public static final int PUT = 3;
	public static final int DELETE = 4;
	public static final int TRACE = 5;
	public static final int HEAD = 6;*/

	public static enum Method{ GET, POST, PUT, DELETE, TRACE, HEAD};

	private String resource;
	private String referer;
	private String host;
	private String connection;
	private String userAgent;
	private String accept;
	private String acceptEncoding;
	private String acceptLanguage;
	private float protocolVersion;
	private Method method;

	//getters

	public String getReferer() {
		return referer;
	}

	public String getHost() {
		return host;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public String getAccept() {
		return accept;
	}

	public String getConnection() {
		return connection;
	}

	public String getUserAgent() {
		return userAgent;
	}
	public String getResource() {
		return resource;
	}
	public float getProtocolVersion() {
		return protocolVersion;
	}
	public Method getMethod() {
		return method;
	}
	//setters

	public void setReferer(String referer) {
		this.referer= referer;
	}

	public void setHost(String host) {
		this.host= host;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding= acceptEncoding;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage= acceptLanguage;
	}

	public void setAccept(String accept) {
		this.accept= accept;
	}

	public void setConnection(String  connection) {
		this.connection= connection;
	}

	public void setUserAgent(String  userAgent) {
		this.userAgent= userAgent;
	}
	public void setResource( String resource) {
		this.resource = resource;
	}
	public void setProtocolVersion( float protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	public void setMethod( Method method) {
		this.method = method;
	}
}
