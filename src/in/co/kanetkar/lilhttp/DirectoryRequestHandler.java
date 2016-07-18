/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp;

import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 *
 * @author satyanash
 */
public class DirectoryRequestHandler implements RequestHandler {

	private final ServerConfig config;

	public DirectoryRequestHandler(ServerConfig config) {
		this.config = config;
	}

	@Override
	public boolean match(HttpRequest request) {
		String resource = request.getResource();
		if (!resource.startsWith("/browse/")) {
			return false;
		}

		File reqFile = getFile(resource);

		return reqFile.isDirectory();
	}

	@Override
	public HttpResponse handle(HttpRequest request) {
		if (!request.getResource().endsWith("/")) {
			return redirectTrailingSlash(request);
		}

		File file = getFile(request.getResource());

		File[] files = file.listFiles();

		HttpResponse response = new HttpResponse();

		try {
			VelocityContext context = VelocityUtils.getBaseContext();
			context.put("dir", file);
			context.put("files", files);

			Template template = Velocity.getTemplate("/templates/directory_listing.vsl");
			StringWriter writer = new StringWriter();

			template.merge(context, writer);
			byte[] data = writer.toString().getBytes();

			response.setContent(data);
			response.setServerString(config.getServerString());
			response.setContentType("text/html");
			response.setContentLength((long) data.length);
			response.setLastModified(new Date(file.lastModified()));
			response.setDate(new Date());
			response.setStatus(HttpResponse.HttpResponseStatus.HTTP_200_OK);
			response.setProtocolVersion(request.getProtocolVersion());

		} catch (ResourceNotFoundException | ParseErrorException rnfe) {
			System.err.println("in.co.kanetkar.lilhttp.DirectoryRequestHandler.handle()");
			System.err.println("message:" + rnfe.getMessage());
		}

		return response;
	}

	public String extractFilePath(String resource) {
		return resource.substring(7);
	}

	public File getFile(String resource) {
		String relativeFilePath = extractFilePath(resource);
		return new File(config.getHttpRoot(), relativeFilePath);
	}

	public HttpResponse redirectTrailingSlash(HttpRequest request) {
		HttpResponse response = new HttpResponse();
		response.setServerString(config.getServerString());
		response.setDate(new Date());
		response.setLocation(request.getResource() + "/");
		response.setStatus(HttpResponse.HttpResponseStatus.HTTP_302_MOVED_TEMPORARILY);
		response.setProtocolVersion(request.getProtocolVersion());
		return response;
	}
}
