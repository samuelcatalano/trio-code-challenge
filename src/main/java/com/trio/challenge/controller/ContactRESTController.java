package com.trio.challenge.controller;

import lombok.extern.slf4j.Slf4j;

import com.trio.challenge.dto.SyncedResultDTO;
import com.trio.challenge.exception.ApiException;
import com.trio.challenge.exception.ServiceException;
import com.trio.challenge.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class ContactRESTController {

  private final ContactService contactService;

  @Autowired
  public ContactRESTController(final ContactService contactService) {
    this.contactService = contactService;
  }

  /**
   * Returns the list of contacts from service.
   * @return List of Contacts
   * @throws ApiException to be thrown if an error occurs during communication with the service
   */
  @GetMapping("/contacts/sync")
  public ResponseEntity<SyncedResultDTO> syncContacts() throws ApiException {
    try {
      var contacts = contactService.syncContacts();
      return ResponseEntity.ok(contacts);
    } catch (final ServiceException e) {
      log.error("Error retrieving contacts from service! Message {} ", e.getMessage(), e);
      throw new ApiException(e.getMessage(), e);
    }
  }
}
