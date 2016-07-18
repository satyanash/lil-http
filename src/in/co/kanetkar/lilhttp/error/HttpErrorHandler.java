/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp.error;

import in.co.kanetkar.lilhttp.HttpRequest;
import in.co.kanetkar.lilhttp.HttpResponse;
import in.co.kanetkar.lilhttp.ServerConfig;
import in.co.kanetkar.lilhttp.VelocityUtils;
import java.io.StringWriter;
import java.util.Date;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 *
 * @author satyanash
 */
public class HttpErrorHandler {
	private final ServerConfig config;

	public HttpErrorHandler(ServerConfig config) {
		this.config = config;
	}

	public HttpResponse handlerError(HttpRequest request, HttpResponse.HttpResponseStatus status) {
		VelocityContext context = VelocityUtils.getBaseContext();
		context.put("status", status);
		context.put("message", "There was an error processing the resource: " + request.getResource());

		Template template = Velocity.getTemplate("/templates/error/error.vsl");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		byte[] data = writer.toString().getBytes();

		HttpResponse response = new HttpResponse();
		response.setContent(data);
		response.setServerString(config.getServerString());
		response.setContentType("text/html");
		response.setContentLength((long)data.length);
		response.setDate(new Date());
		response.setStatus(status);
		return response;

	}

}
