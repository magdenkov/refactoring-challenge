package com.example.compiler.server;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class Compiler {

	private static final Logger logger = Logger.getLogger(Compiler.class);
	
	public Result buildAndRun(String code, String data) throws IOException, InterruptedException {
		
		try {
		
			String javaPath = System.getenv("APP_JAVA_PATH");
			String javacPath = System.getenv("APP_JAVAC_PATH");
	
			Path temp = null;
			Path source = null;
			Path dest = null;
			Path trace = null;
			Path input = null;
			Path output = null;
			
		    try {
		    	
		    	temp = Files.createTempDirectory("compiler");
		    	
		    	source = temp.resolve("Main.java");
				dest = temp.resolve("Main.class");
				trace = temp.resolve("trace.txt");
				input = temp.resolve("input.txt");
				output = temp.resolve("output.txt");
				
				Files.write(source, code.getBytes());
				Files.write(input, data.getBytes());
				
				Redirect redirectBuild = Redirect.to(trace.toFile());
				
				Process build = new ProcessBuilder(
						String.format("\"%s\"", javacPath.toString().replace("\\", "/")),
						String.format("\"%s\"", source.toString().replace("\\", "/"))
				)
			    	.directory(temp.toFile())
			    	.redirectErrorStream(true)
			    	.redirectError(redirectBuild)
			    	.redirectOutput(redirectBuild)
			    	.start();
				
				build.waitFor(5, TimeUnit.SECONDS);
				
				Redirect redirectOutput = Redirect.to(output.toFile());
				
				Process run = new ProcessBuilder(
					String.format("\"%s\"", javaPath.toString().replace("\\", "/")),
					"Main"
				)
			    	.directory(temp.toFile())
			    	.redirectErrorStream(true)
			    	.redirectError(redirectOutput)
			    	.redirectOutput(redirectOutput)
			    	.redirectInput(input.toFile())
			    	.start();
				
				run.waitFor(5, TimeUnit.SECONDS);
				
				Result result = new Result();
				
				result.output = new String(Files.readAllBytes(output));
				result.trace = new String(Files.readAllBytes(trace));
				
				return result;
				
		    } finally {
		    	
		    	IOException ex = new IOException("Build & Run Service Exception");
		    	
		    	try { if (source != null) Files.deleteIfExists(source); } catch (Exception e) { ex.addSuppressed(e); }
		    	try { if (dest != null) Files.deleteIfExists(dest); } catch (Exception e) { ex.addSuppressed(e); }
		    	try { if (trace != null) Files.deleteIfExists(trace); } catch (Exception e) { ex.addSuppressed(e); }
		    	try { if (input != null) Files.deleteIfExists(input); } catch (Exception e) { ex.addSuppressed(e); }
		    	try { if (output != null) Files.deleteIfExists(output); } catch (Exception e) { ex.addSuppressed(e); }
		    	try { if (temp != null) Files.deleteIfExists(temp); } catch (Exception e) { ex.addSuppressed(e); }
		    	
		    	if (ex.getSuppressed().length > 0) {
		    		throw ex;
		    	}
		    }
		} catch (IOException | InterruptedException e) {
			logger.error("Build & Run task failed", e);
			throw e;
		}
	}
}
