package com.example.main.repository;

import com.example.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query(value = "select * from user where lower(name) like : username", nativeQuery = true)
  List<User> findByNameIsLike(String name);
}
