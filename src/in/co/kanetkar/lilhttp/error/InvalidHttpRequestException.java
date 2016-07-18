package in.co.kanetkar.lilhttp.error;

import in.co.kanetkar.lilhttp.HttpResponse;

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
