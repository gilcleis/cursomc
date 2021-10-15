package com.gilclei.cursomc.resources.exeption;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gilclei.cursomc.services.exeptions.ObjectNotFoundExeption;

@ControllerAdvice
public class ResourceExeptionHandler {
	@ExceptionHandler(ObjectNotFoundExeption.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundExeption e, HttpServletRequest request){
		StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
