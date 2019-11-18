package com.rlsp.socialbooks.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rlsp.socialbooks.domain.Comment;
import com.rlsp.socialbooks.domain.Book;
import com.rlsp.socialbooks.repository.CommentsRepository;
import com.rlsp.socialbooks.repository.BooksRepository;
import com.rlsp.socialbooks.service.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {

	@Autowired //procura a implementação de LivrosRepository, que será feita pelo SpringData
	private BooksRepository livrosRepository;
	
	@Autowired //procura a implementação de ComentariosRepository, que será feita pelo SpringData
	private CommentsRepository comentariosRepository;
	
	public List<Book> listar() {
		return livrosRepository.findAll();
	}
	
	public Book buscar(Long id) {
		Book livro = livrosRepository.findById(id).orElse(null);
		
		if (livro == null) {
			throw new LivroNaoEncontradoException("O Livro não foi encontrado..SORRY !!!");
		}
		
		return livro;
		
	}
	
	public Book salvar(Book livro) {
		/*
		 * O "SAVE" da camada de repostitorio do SPRING faz um merge, se tiver alguma instancia com id escolhido usa o UPDATE, caso contrario usa o SAVE
		 */
		livro.setId(null); //Serve para garantir a criacao de uma instancia nova e nao atualização uma instancia existente
		return livrosRepository.save(livro);		
		
	}
	
	public void deletar(Long id) {
		
		try {
			livrosRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new LivroNaoEncontradoException("O livro não foi encontrado no BD...Sorry Folks !!!");
		}

	}
	
	public void atualizar(Book livro) {
		
		verificarExistencia(livro);
		livrosRepository.save(livro);
		
	}
	
	private void verificarExistencia(Book livro) {
		buscar(livro.getId());
	}
	
	public Comment salvarComentario(Long livroId, Comment comentario) {
	
		Book livro = buscar(livroId);
		
		comentario.setLivro(livro);
		comentario.setData(new Date());
		
		return comentariosRepository.save(comentario);
		
	}

	public List<Comment> listarComentarios(Long livroId) {
		Book livro = buscar(livroId);
		
		
		return livro.getComments();
	}
}
