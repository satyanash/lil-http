/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp;

/**
 *
 * @author satyanash
 */
public class HttpException extends RuntimeException{
	private HttpResponse.HttpResponseStatus status;

	public HttpException( HttpResponse.HttpResponseStatus status)
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
		return "HttpException: " + status;
	}
	
}
