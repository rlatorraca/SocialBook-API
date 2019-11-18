package com.rlsp.socialbooks.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Name is Mandatory to Author Entity")
	private String name;
	
	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="DOB is mandatory to Author Entity")
	private Date dob;
	
	@JsonInclude(Include.NON_NULL) // Apenas mostra a  informação no JSON se não for NULA
	@NotNull(message="Nationality is mandatory to Author Entity")
	private String nationality;
	
	@OneToMany(mappedBy = "author")
	@JsonIgnore
	private List<Book> books;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDOB() {
		return dob;
	}

	public void setDOB(Date dOB) {
		dob = dOB;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	
	
	
	
}
