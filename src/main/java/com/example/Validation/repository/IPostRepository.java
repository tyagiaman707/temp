package com.example.Validation.repository;

import com.example.Validation.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post,Integer> {
}
