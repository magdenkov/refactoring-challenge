package com.example.compiler.server;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 8809462777596113685L;
	
	public String trace; // compiler output
	public String output; // execution output
}