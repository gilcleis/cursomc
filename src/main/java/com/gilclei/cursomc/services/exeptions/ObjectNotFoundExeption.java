package com.gilclei.cursomc.services.exeptions;

public class ObjectNotFoundExeption extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundExeption(String message) {
		super(message);
	}
	
	public ObjectNotFoundExeption(String message, Throwable cause) {
		super(message,cause);
	}

}
