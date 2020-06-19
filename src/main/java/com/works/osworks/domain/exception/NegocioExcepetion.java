package com.works.osworks.domain.exception;

public class NegocioExcepetion extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NegocioExcepetion(String message) {
		super(message);
	}
	
}
