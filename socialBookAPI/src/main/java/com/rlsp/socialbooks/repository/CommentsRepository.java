package com.rlsp.socialbooks.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rlsp.socialbooks.domain.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long>{

}
