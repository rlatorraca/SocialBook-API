package com.rlsp.socialbooks.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity // --> Entidade  JPA
public class Book {

	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // politica de geração do ID no BD
	private Long id;
	
	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	@NotEmpty(message="Nome is mandatory/not empty to Book Entity")
	private String nome;
	
	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="Publicação is mandatory to Book Entity")
	private Date publicacao;
	
	
	@JoinColumn(name="Author_Id")	
	@ManyToOne
	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	@Transient
	private Author author;
	
	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	private String editora;
	
	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	@NotEmpty(message="Resumo is mandatory/not empty to Book Entity")
	@Size(max = 1500, message="Resumo has been more than 1500 characters")
	private String resumo;
	
	//@Transient //evita que Hibernate tente fazer a conexão entre LIVRO x Comentario
	@JsonInclude(Include.NON_EMPTY) // Apenas mostra a  informação no JSON se não for NULA
	@OneToMany(mappedBy="livro")
	private List<Comment> comments;
	
	
	public Book() {};
	
	public Book (String nome) {
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getPublicacao() {
		return publicacao;
	}
	public void setPublicacao(Date publicacao) {
		this.publicacao = publicacao;
	}
	public Author getAutor() {
		return author;
	}
	public void setAutor(Author autor) {
		this.author = autor;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	
}
