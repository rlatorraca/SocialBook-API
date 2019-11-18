package com.rlsp.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rlsp.socialbooks.domain.Book;
import com.rlsp.socialbooks.domain.Comment;
import com.rlsp.socialbooks.service.LivrosService;

@RestController // Responsável para indicar que a classe é um RESOURCE (Controller do MVC)
@RequestMapping("/livros")
public class LivrosResources {

	@Autowired //procura a implementação de LivrosService, que será feita pelo SpringData
	private LivrosService livrosService;
	
	
	/*
	 * Lista algum livros
	 * @RequestMapping //mapeia uma URI para determinado método 
	 * 		value="/livros" --> (no caso /livros)
	 * 		method = RequestMethod.GET --> retorna um valor
	 * @CrossOrigin ==> permit acesso de qualquer origem [Não sera barrado pelo Chrome / CORS], sem verificacao do CORS {Importante para JScript} 
	 * 		- Usado em API, pela necessidade de acesso via varias/diferentes origens
	 */
	//@RequestMapping(value="/livros", method=RequestMethod.GET) //mapeia uma URI para determinado método (no caso /livros)
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Book>> listar() {
		
		return ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
	}
	
	/*
	 * Criar Livros (anota) no BD
	 * method = RequestMethod.POST --> cria um valor
	 * @RequestBody ==> pega o valor presente no CORPO da requisição (body) coloque (Injeta) dentro do OBJETO
	 * ResponseEntity<T> ==> usado para manipular a resposta HTTP
	 */
	//@RequestMapping(value="/livros/", method=RequestMethod.POST)
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Book livro) {	
		
		livrosService.salvar(livro);
		
		URI uri = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(livro.getId())
						.toUri(); //serve para incluir a URI que o cliente poderá usar para acessar a informação do novo recurro.
		
		return ResponseEntity.created(uri).build(); //retorna a URI criado, como CREATED (201), como resposta correta
	}
	
	/*
	 * PathVariable ==> pegar o valor presente entre "{x}" (o que está na URI) e passa para a variável
	 * ResponseEntity<T> ==> tem a função de encapsular o objeto de retorno (no caso é o LIVRO) e entraga/manupila o HTTP // o "?" ==> qualquer tipo de objeto
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Book livro = livrosService.buscar(id);
		
		CacheControl cacheControl = CacheControl.maxAge(25, TimeUnit.SECONDS);
		/*
		try {
			livro = livrosService.buscar(id);	
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build(); // build ==> constrói o corpo da mensagem com 404 
		}
		*/		
		
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro); //status ==> apresente o status REAL(HTTP resposta) da requisição | body ==> retorno o objeto LIVRO
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		
		livrosService.deletar(id);
		/*
		try {
			livrosService.deletar(id);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build(); // envia um erro 404 (Não achado)
		} 
		*/
		
		return ResponseEntity.noContent().build(); // envia um "No content"
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@PathVariable("id") Long id, @RequestBody Book livro) {
		
		livro.setId(id); //garante que o recurso passado na URI seja atualizado
		livrosService.atualizar(livro); // Save iria usar um implementação chamada MERGE (para atualizar)
		
		/*
		try {
			livrosService.atualizar(livro); // Save iria usar um implementação chamada MERGE (para atualizar)
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build(); // envia um erro 404 (Não achado)
		}
		*/
		
		return ResponseEntity.noContent().build(); // envia um "No content"
	}
	
	@RequestMapping(value="/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> adcionarComentario(@PathVariable("id") Long livroId, @RequestBody Comment comentario) {
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Pega o usuario que esta autenticado
		
		comentario.setUsuario(auth.getName());
		
		livrosService.salvarComentario(livroId, comentario);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUri(); // Recupera todos os comentarios existentes para cada livro
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}/comentarios", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> listarComentarios(@PathVariable("id") Long livroId) {
		
		List<Comment> listaDeComentarios = livrosService.listarComentarios(livroId);
		
		return ResponseEntity.status(HttpStatus.OK).body(listaDeComentarios);
	}
}

