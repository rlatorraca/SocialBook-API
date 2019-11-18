package com.rlsp.socialbooks.service.exceptions;

/*
 * Excecao NAO CHECADA ==> nao pode-se tomar alguma ação em nivel de codigo
 * Lanca-se para camada superior para ser tratada.
 */
public class AuthorNaoEncontradoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthorNaoEncontradoException(String mensagem) {
		super(mensagem);		
	}
	
	public AuthorNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem,causa);		
	}
	
}
