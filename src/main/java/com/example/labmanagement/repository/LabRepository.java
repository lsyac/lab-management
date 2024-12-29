package com.example.labmanagement.repository;

import com.example.labmanagement.dox.Lab;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LabRepository extends ListCrudRepository<Lab,String> {
    List<Lab> findByState(short state);
    Lab deleteLabByName(String name);
}
