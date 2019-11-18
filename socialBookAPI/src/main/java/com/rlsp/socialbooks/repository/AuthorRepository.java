package com.rlsp.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlsp.socialbooks.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
