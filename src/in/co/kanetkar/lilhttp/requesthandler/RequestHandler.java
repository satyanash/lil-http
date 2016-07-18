/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp.requesthandler;

import in.co.kanetkar.lilhttp.HttpRequest;
import in.co.kanetkar.lilhttp.HttpResponse;

/**
 *
 * @author satyanash
 */
public interface RequestHandler {
	public boolean match(HttpRequest request);

	public HttpResponse handle(HttpRequest request);
}
