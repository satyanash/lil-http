package satyanash.jHTTP;

import java.util.Date;

public class HttpResponse
{
	private String contentType;
	private String contentLength;
	private Date date;
	private Date lastModified;
	private String serverString;
	private HttpResponseStatus status;

	/*public static final int HTTP_200_OK = 200;

	public static final int HTTP_301_MOVED_PERMANENTLY = 301;
	public static final int HTTP_302_MOVED_TEMPORARIRILY = 302;
	public static final int HTTP_303_SEE_OTHER = 303;

	public static final int HTTP_400_BAD_REQUEST = 400;
	public static final int HTTP_401_UNAUTHORIZED = 401;
	public static final int HTTP_402_PAYMENT_REQUIRED = 402;
	public static final int HTTP_403_FORBIDDEN = 403;
	public static final int HTTP_404_NOT_FOUND = 404;
	public static final int HTTP_405_METHOD_NOT_ALLOWED = 405;

	public static final int HTTP_500_INTERNAL_SERVER_ERROR = 500;
	public static final int HTTP_501_NOT_IMPLEMENTED = 501;
	public static final int HTTP_502_BAD_GATEWAY = 502;
	public static final int HTTP_503_SERVICE_UNAVAILABLE = 503;
	public static final int HTTP_504_GATEWAY_TIMEOUT = 504;
	public static final int HTTP_505_HTTP_VERSION_NOT_SUPPORTED = 506;*/

	public enum HttpResponseStatus {

		HTTP_200_OK(200, "OK"),
	
		HTTP_301_MOVED_PERMANENTLY(301, "Moved Permanently"),
		HTTP_302_MOVED_TEMPORARIRILY(302, "Moved Temporarily"),
		HTTP_303_SEE_OTHER(303, "See Other"),

		HTTP_400_BAD_REQUEST(400, "Bad Request"),
		HTTP_401_UNAUTHORIZED(401, "Unauthorized"),
		HTTP_402_PAYMENT_REQUIRED(402, "Payment Required"),
		HTTP_403_FORBIDDEN(403, "Forbidden"),
		HTTP_404_NOT_FOUND(404, "Not Found"),
		HTTP_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

		HTTP_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
		HTTP_501_NOT_IMPLEMENTED(501, "Not Implemented"),
		HTTP_502_BAD_GATEWAY(502, "Bad Gateway"),
		HTTP_503_SERVICE_UNAVAILABLE(503, "Service Unavailable"),
		HTTP_504_GATEWAY_TIMEOUT(504, "Gateway Timeout"),
		HTTP_505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

		private int statusCode;
		private String reason;

		HttpResponseStatus( int statusCode, String reason){
			this.statusCode = statusCode;
			this.reason = reason;
		}

		@Override
		public String toString()
		{
			return statusCode + " " + reason;
		}
	}

	//getters

	public HttpResponseStatus getStatus() {
		return status;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public String getServerString() {
		return serverString;
	}
	public Date getDate() {
		return date;
	}
	public String getContentLength() {
		return contentLength;
	}
	public String getContentType() {
		return contentType;
	}

	//setters

	public void setStatus(HttpResponseStatus  status) {
		this.status= status;
	}
	public void setLastModified(Date  lastModified) {
		this.lastModified= lastModified;
	}
	public void setServerString(String  serverString) {
		this.serverString= serverString;
	}
	public void setDate(Date  date) {
		this.date= date;
	}
	public void setContentLength(String  contentLength) {
		this.contentLength= contentLength;
	}
	public void setContentType(String  contentType) {
		this.contentType= contentType;
	}
}
