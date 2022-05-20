package com.ibm.academia.apirest.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;

@ControllerAdvice
public class RouletteRestException {

	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<Object> formatoInvalidoException(BadRequestException exception){
		Map<String, Object> answer = new HashMap<String, Object>();
		answer.put("Error", exception.getMessage());
		return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<?> noExisteException(NotFoundException exception){
		Map<String, Object> answer= new HashMap<String, Object>();
		answer.put("Error", exception.getMessage());
		return new ResponseEntity<>(answer, HttpStatus.NOT_FOUND); 
	}
	
}
