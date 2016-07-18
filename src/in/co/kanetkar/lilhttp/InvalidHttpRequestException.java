package in.co.kanetkar.lilhttp;

public class InvalidHttpRequestException extends HttpException
{
	public InvalidHttpRequestException(HttpResponse.HttpResponseStatus status) {
		super(status);
	}

	@Override
	public String toString()
	{
		return "InvalidHttpRequestException: " + getStatus();
	}
}
