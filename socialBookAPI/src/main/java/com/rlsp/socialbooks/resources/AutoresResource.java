package com.rlsp.socialbooks.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rlsp.socialbooks.domain.Author;
import com.rlsp.socialbooks.service.AuthorsService;

@RestController
@RequestMapping("/autores")
public class AutoresResource {

	@Autowired
	private AuthorsService authorService;
	
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<Author>> listar() {
		
		List<Author> autores = authorService.listAllAuthors();
	
		return ResponseEntity.status(HttpStatus.OK).body(autores);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody Author autor){
		autor = authorService.salvar(autor);
		
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(autor.getId())
					.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value= "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Author> buscarAuthor(@PathVariable("id") Long id){
		Author autor = authorService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(autor);
	}
	
	
	
}
