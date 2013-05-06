package satyanash.jHTTP;

import java.util.Date;

public class HttpResponse
{
	private String contentType;
	private String contentLength;
	private String resource;
	private Date date;
	private Date lastModified;
	private HttpResponseStatus status;

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

		//getters


		public String getReason() {
			return reason;
		}
		public int getStatusCode() {
			return statusCode;
		}

		@Override
		public String toString()
		{
			return statusCode + " " + reason;
		}
	}

	//getters

	public String getResource() {
		return resource;
	}

	public HttpResponseStatus getStatus() {
		return status;
	}
	public Date getLastModified() {
		return lastModified;
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

	public void setResource(String  resource) {
		this.resource= resource;
	}

	public void setStatus(HttpResponseStatus status) {
		this.status= status;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified= lastModified;
	}
	public void setDate(Date date) {
		this.date= date;
	}
	public void setContentLength(String contentLength) {
		this.contentLength= contentLength;
	}
	public void setContentType(String contentType) {
		this.contentType= contentType;
	}
}
