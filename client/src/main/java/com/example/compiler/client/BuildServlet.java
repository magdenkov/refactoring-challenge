package com.example.compiler.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.compiler.server.CompilerProxy;
import com.example.compiler.server.Result;

@WebServlet("/BuildServlet")
public class BuildServlet extends HttpServlet {

	private static final long serialVersionUID = 3460936307359936690L;

	private static final Logger logger = Logger.getLogger(BuildServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String[] source = request.getParameterValues("source");
		String[] input = request.getParameterValues("input");
		
		if (source != null && source.length > 0
		  && input != null && input.length > 0
		) { // data validation (required by specification =)
			
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("[Source]\n%s\n[/Source]", source[0]));
				logger.debug(String.format("[Input]\n%s\n[/Input]", input[0]));
			}
			
			PrintWriter writer = response.getWriter();
			
			try {
				
				CompilerProxy compiler = new CompilerProxy();
				Result result = compiler.buildAndRun(source[0], input[0]);
				
				String trace = result.getTrace();
				String output = result.getOutput();
				
				if (trace != null) {
					writer.println("[Build]");
					writer.println(trace);
					writer.println("[/Build]");
					
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("[Build]\n%s\n[/Build]", trace));
					}
				}
				if (output != null) {
					writer.println("[Run]");
					writer.println(output);
					writer.println("[/Run]");
					
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("[Run]\n%s\n[/Run]", output));
					}
				}
				
			} catch (RemoteException e) {
				
				logger.error("Remote call failed", e);
				
				writer.println("Exception: " + e);
				e.printStackTrace(writer);
			}
		}
	}

}
