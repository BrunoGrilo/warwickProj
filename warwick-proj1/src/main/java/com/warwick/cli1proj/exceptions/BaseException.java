package com.warwick.cli1proj.exceptions;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
