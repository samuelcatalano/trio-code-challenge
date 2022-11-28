package com.trio.challenge.service;

import com.trio.challenge.dto.ContactDTO;
import com.trio.challenge.dto.SyncedResultDTO;
import com.trio.challenge.exception.ServiceException;
import com.trio.challenge.model.Contact;

public interface ContactService {

  SyncedResultDTO syncContacts() throws ServiceException;

  ContactDTO create(Contact contact) throws ServiceException;

  ContactDTO findById(String id) throws ServiceException;

}
