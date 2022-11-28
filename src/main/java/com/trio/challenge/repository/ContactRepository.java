package com.trio.challenge.repository;

import com.trio.challenge.model.Contact;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {

  @Query("SELECT count(*) FROM Contact")
  long countSyncedContacts();

}
