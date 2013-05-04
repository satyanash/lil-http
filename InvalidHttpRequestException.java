package satyanash.jHTTP;

public class InvalidHttpRequestException extends RuntimeException
{
	private HttpResponse.HttpResponseStatus status;

	InvalidHttpRequestException( HttpResponse.HttpResponseStatus status)
	{
		this.status = status;
	}

	//getters

	public HttpResponse.HttpResponseStatus getStatus() {
		return status;
	}

	//setters

	public void setStatus(HttpResponse.HttpResponseStatus  status) {
		this.status= status;
	}

	@Override
	public String toString()
	{
		return "InvalidHttpRequestException: " + status;
	}
}
