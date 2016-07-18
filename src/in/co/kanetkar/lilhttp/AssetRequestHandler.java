/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;

/**
 *
 * @author satyanash
 */
public class AssetRequestHandler implements RequestHandler{
	private final ServerConfig config;

	public AssetRequestHandler(ServerConfig config) {
		this.config = config;
	}

	@Override
	public boolean match(HttpRequest request) {
		String resource = request.getResource();
		if (!resource.startsWith("/assets/")) {
			return false;
		}

		InputStream assetStream = getAssetResourceStream(resource);

		return assetStream != null;
	}

	@Override
	public HttpResponse handle(HttpRequest request) {
		InputStream assetStream = getAssetResourceStream(request.getResource());

		HttpResponse response = new HttpResponse();

		try (BufferedInputStream bis = new BufferedInputStream(assetStream)) {
			String contentType = URLConnection.guessContentTypeFromStream(bis);
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();

			//Read 4KiB at a time
			byte[] block = new byte[4096];

			long length = 0;
			int count;
			//read blocks till end of file is reached
			while ((count = bis.read(block)) != -1) {
				bytes.write(block);
				length += count;
			}

			response.setContent(bytes.toByteArray());
			response.setServerString(config.getServerString());
			response.setContentType(contentType);
			response.setContentLength(length);
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

	public InputStream getAssetResourceStream(String resource) {
		return this.getClass().getResourceAsStream(resource);
	}
	
}
