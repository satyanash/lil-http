/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp;

import java.io.StringWriter;
import java.util.Date;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

/**
 *
 * @author satyanash
 */
public class RootRequestHandler implements RequestHandler {

	private final ServerConfig config;

	public RootRequestHandler(ServerConfig config) {
		this.config = config;
	}

	@Override
	public boolean match(HttpRequest request) {
		return "/".equals(request.getResource());
	}

	@Override
	public HttpResponse handle(HttpRequest request) {
		Template template = Velocity.getTemplate("/templates/index.vsl");
		StringWriter writer = new StringWriter();

		template.merge(VelocityUtils.getBaseContext(), writer);
		byte[] data = writer.toString().getBytes();

		HttpResponse response = new HttpResponse();
		response.setContent(data);
		response.setContentLength((long)data.length);
		response.setContentType("text/html");
		response.setServerString(config.getServerString());
		response.setDate(new Date());
		response.setStatus(HttpResponse.HttpResponseStatus.HTTP_302_MOVED_TEMPORARILY);
		response.setProtocolVersion(request.getProtocolVersion());
		return response;
	}

}
