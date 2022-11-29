package com.trio.challenge.repository;

import com.trio.challenge.model.Contact;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {

  @Query("SELECT SUM(case isSynced when true then 1 else 0 end) FROM Contact")
  long countSyncedContacts();

}
