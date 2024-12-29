package com.example.labmanagement.repository;

import com.example.labmanagement.dox.Announcement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement,String> {

}
