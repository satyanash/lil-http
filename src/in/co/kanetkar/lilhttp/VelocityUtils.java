/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.kanetkar.lilhttp;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.DateTool;

/**
 *
 * @author satyanash
 */
public class VelocityUtils {
	public static VelocityContext getBaseContext(){
		VelocityContext context = new VelocityContext();
		context.put("dateTool", new DateTool());
		context.put("files", new DateTool());
		return context;
	}
	
}
