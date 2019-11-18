package com.rlsp.socialbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlsp.socialbooks.domain.Author;
import com.rlsp.socialbooks.repository.AuthorRepository;
import com.rlsp.socialbooks.service.exceptions.AuthorExistenteException;
import com.rlsp.socialbooks.service.exceptions.AuthorNaoEncontradoException;

@Service
public class AuthorsService {
	
	@Autowired
	private AuthorRepository autoresRepository;
	
	public List<Author> listAllAuthors(){		 
		
		return autoresRepository.findAll();		
	}
	
	public Author salvar(Author autor) {
		if (autor.getId() != null) {
			Author a = autoresRepository.findById(autor.getId()).orElse(null);
			
			if (a != null) {
				throw new AuthorExistenteException("Sorry...This author already is included into DB");
			}
		}
		
		return autoresRepository.save(autor);
	}
	
	public Author buscar(Long id) {
		Author autor = autoresRepository.findById(id).orElse(null);
		
		if (autor == null) {
			throw new AuthorNaoEncontradoException("Author not FOUND....!!!");
		}
		
		return autor;
	}

}
