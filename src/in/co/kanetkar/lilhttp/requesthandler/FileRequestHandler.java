/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp.requesthandler;

import in.co.kanetkar.lilhttp.error.HttpException;
import in.co.kanetkar.lilhttp.HttpRequest;
import in.co.kanetkar.lilhttp.HttpResponse;
import in.co.kanetkar.lilhttp.error.InvalidHttpRequestException;
import in.co.kanetkar.lilhttp.ServerConfig;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

/**
 *
 * @author satyanash
 */
public class FileRequestHandler implements RequestHandler {

	private final ServerConfig config;

	public FileRequestHandler(ServerConfig config) {
		this.config = config;
	}

	@Override
	public boolean match(HttpRequest request) {
		String resource = request.getResource();
		if (!resource.startsWith("/browse/")) {
			return false;
		}

		File reqFile = getFile(resource);
		System.out.println("found file: " + reqFile);

		return reqFile.isFile();
	}

	@Override
	public HttpResponse handle(HttpRequest request) {
		File file = getFile(request.getResource());

		HttpResponse response = new HttpResponse();

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();

			//Read 4KiB at a time
			byte[] block = new byte[4096];

			//read blocks till end of file is reached
			while (bis.read(block) != -1) {
				bytes.write(block);
			}

			response.setContent(bytes.toByteArray());
			response.setServerString(config.getServerString());
			response.setContentType(Files.probeContentType(file.toPath()));
			response.setContentLength(file.length());
			response.setLastModified(new Date(file.lastModified()));
			response.setDate(new Date());
			response.setStatus(HttpResponse.HttpResponseStatus.HTTP_200_OK);
			response.setProtocolVersion(request.getProtocolVersion());

		} catch (FileNotFoundException fnfe) {
			InvalidHttpRequestException ihre = new InvalidHttpRequestException(HttpResponse.HttpResponseStatus.HTTP_404_NOT_FOUND);
			ihre.addSuppressed(fnfe);
			throw ihre;
		} catch (IOException ioe) {
			HttpException he = new HttpException(HttpResponse.HttpResponseStatus.HTTP_500_INTERNAL_SERVER_ERROR);
			he.addSuppressed(ioe);
			throw he;
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
}
