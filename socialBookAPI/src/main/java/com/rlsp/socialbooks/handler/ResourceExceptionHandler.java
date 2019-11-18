package com.rlsp.socialbooks.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rlsp.socialbooks.domain.DetailsError;
import com.rlsp.socialbooks.service.exceptions.AuthorExistenteException;
import com.rlsp.socialbooks.service.exceptions.AuthorNaoEncontradoException;
import com.rlsp.socialbooks.service.exceptions.LivroNaoEncontradoException;

@ControllerAdvice  // Permite que interceptar todos as exceções que existirem no código
public class ResourceExceptionHandler {
	
	/**
	 * Capturada a excecao responde uma rsposta adequado ao cliente/usuario
	 */
	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<DetailsError> handleLivroNaoEncontradoException(LivroNaoEncontradoException e, HttpServletRequest request){
	
		DetailsError error = new DetailsError();
		error.setStatus(404l);
		error.setTitulo("Book not Found...(by RLSP)");
		error.setMensagemDesenvolvedor("http://error.rlsp.socialbooks.com/404");
		error.setTimestamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(AuthorExistenteException.class)
	public ResponseEntity<DetailsError> handleAuthorExistenteException(AuthorExistenteException e, HttpServletRequest request){
	
		DetailsError error = new DetailsError();
		error.setStatus(409l);
		error.setTitulo("Author already existed ..CONFLICT...(by RLSP)");
		error.setMensagemDesenvolvedor("http://error.rlsp.socialbooks.com/409");
		error.setTimestamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
	
	@ExceptionHandler(AuthorNaoEncontradoException.class)
	public ResponseEntity<DetailsError> handleAuthorExistenteException(AuthorNaoEncontradoException e, HttpServletRequest request){
	
		DetailsError error = new DetailsError();
		error.setStatus(404l);
		error.setTitulo("Author not FOUND ..BAD REQUEST...(by RLSP)");
		error.setMensagemDesenvolvedor("http://error.rlsp.socialbooks.com/404");
		error.setTimestamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetailsError> handleConstraintViolationException(DataIntegrityViolationException e, HttpServletRequest request){
	
		DetailsError error = new DetailsError();
		error.setStatus(400l);
		error.setTitulo("Invalid Request to SERVER ..BAD REQUEST...(by RLSP)");
		error.setMensagemDesenvolvedor("http://error.rlsp.socialbooks.com/400");
		error.setTimestamp(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	

}
