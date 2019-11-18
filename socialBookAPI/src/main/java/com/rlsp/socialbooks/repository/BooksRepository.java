package com.rlsp.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlsp.socialbooks.domain.Book;

/*
 * O Spring faz toda a implentação e não precisaremos implementar a interface
 * Basta adicionar o EXTENDS JpaRepository <Tipo do dados a manipular , tipo do identificador>
 */
public interface BooksRepository extends JpaRepository<Book, Long> {

	
}
