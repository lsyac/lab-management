package com.example.labmanagement.repository;

import com.example.labmanagement.dox.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);
    void deleteById(String id);
}
