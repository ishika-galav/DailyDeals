package com.capg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ResourceAlreadyExists extends Exception{
	
	public ResourceAlreadyExists(String msg) {
		super(msg);
	}

}
