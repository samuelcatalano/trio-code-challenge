package com.trio.challenge.service.impl;

import lombok.extern.slf4j.Slf4j;

import com.trio.challenge.configuration.MailChimp;
import com.trio.challenge.dto.ContactDTO;
import com.trio.challenge.dto.SyncedContactDTO;
import com.trio.challenge.dto.SyncedResultDTO;
import com.trio.challenge.exception.ServiceException;
import com.trio.challenge.model.Contact;
import com.trio.challenge.repository.ContactRepository;
import com.trio.challenge.service.ContactService;
import com.trio.challenge.service.base.BaseService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactServiceImpl extends BaseService implements ContactService {

  private static final String PATH = "contacts";

  private final ContactRepository repository;
  private final MailChimp mailChimp;
  private final RestTemplate restTemplate;
  private final ModelMapper modelMapper;

  @Autowired
  public ContactServiceImpl(final ContactRepository repository, final MailChimp mailChimp, final RestTemplate restTemplate, final ModelMapper modelMapper) {
    this.repository = repository;
    this.mailChimp = mailChimp;
    this.restTemplate = restTemplate;
    this.modelMapper = modelMapper;
  }

  /**
   * Sync contacts list.
   * @return List of Contacts synced
   * @throws ServiceException to be thrown if an error occurs during synchronization
   */
  @Override
  public SyncedResultDTO syncContacts() throws ServiceException {
    try {
      var contactDTOs = Optional.of(this.getContacts()).orElseThrow(() -> new ServiceException("No contacts were found!"));
      var contacts = contactDTOs.stream().map(user -> modelMapper.map(user, Contact.class)).collect(Collectors.toList());
      contacts = mailChimp.subscribe(contacts);
      repository.saveAll(contacts);

      long count = repository.countSyncedContacts();
      var  syncedContactDTOs = contacts.stream().map(contact -> modelMapper.map(contact, SyncedContactDTO.class)).collect(Collectors.toList());

      return SyncedResultDTO.builder().syncedContacts(count).contacts(syncedContactDTOs).build();
    } catch (final Exception e) {
      log.error("Error syncing contacts! Message {} ", e.getMessage(), e);
      throw new ServiceException(e.getMessage(), e);
    }
  }

  /**
   * Returns the list of contacts from Contacts Endpoint: https://challenge.trio.dev/api/v1/contacts.
   * @return List of Contacts
   * @throws ServiceException to be thrown if an error occurs during communication with the endpoint
   */
  private List<ContactDTO> getContacts() throws ServiceException {
    try {
      return this.restTemplate.exchange(baseUrl + PATH, HttpMethod.GET, this.getCustomHeaders(), new ParameterizedTypeReference<List<ContactDTO>>(){}).getBody();
    } catch (final Exception e) {
      log.error("Error retrieving contacts from Trio URL! Message {} ", e.getMessage(), e);
      throw new ServiceException(e.getMessage(), e);
    }
  }

  /**
   * Saves a new Contact into the database.
   * @param contact the contactDTO
   * @return a contact saved in the database
   * @throws ServiceException to be thrown if an error occurs during communication with the database
   */
  @Override
  public ContactDTO create(final Contact contact) throws ServiceException {
    try {
      var entity = repository.save(contact);
      return this.modelMapper.map(entity, ContactDTO.class);
    } catch (final Exception e) {
      log.error("Error saving a new contact! Message {} ", e.getMessage(), e);
      throw new ServiceException(e.getMessage(), e);
    }
  }

  /**
   * Returns the contact associated with this identifier.
   * @param id the contact identifier
   * @return the contact associated with this identifier
   * @throws ServiceException to be thrown if an error occurs during communication with the database
   */
  @Override
  public ContactDTO findById(final String id) throws ServiceException {
    try {
      var entity = repository.findById(id);
      return this.modelMapper.map(entity, ContactDTO.class);
    } catch (final Exception e) {
      log.error("Error retrieving the contact by id! Message {} ", e.getMessage(), e);
      throw new ServiceException(e.getMessage(), e);
    }
  }

  /**
   * Returns the custom headers.
   * @return the custom headers
   */
  private HttpEntity<List<ContactDTO>> getCustomHeaders() {
    final HttpHeaders httpHeaders = this.getHttpHeaders();
    return new HttpEntity<>(httpHeaders);
  }
}