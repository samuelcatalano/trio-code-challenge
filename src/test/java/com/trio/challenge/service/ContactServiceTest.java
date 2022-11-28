package com.trio.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trio.challenge.dto.ContactDTO;
import com.trio.challenge.model.Contact;
import com.trio.challenge.repository.ContactRepository;
import com.trio.challenge.service.impl.ContactServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

  private static final String EMAIL = "samuel.catalano@gmail.com";
  private static final LocalDateTime CREATED_AT = LocalDateTime.now();
  private static final String FIRST_NAME = "Samuel";
  private static final String LAST_NAME = "Catalano";
  private static final String AVATAR = "https://cdn.fakercloud.com/avatars/samcatalano_123.jpg";

  @Mock
  private ModelMapper mapper;

  @InjectMocks
  private ContactServiceImpl contactService;

  @Mock
  private ContactRepository repository;

  @Test
  void testCreate() throws Exception {
    var contact = Contact.builder()
        .email(EMAIL)
        .createdAt(CREATED_AT)
        .firstName(FIRST_NAME)
        .lastName(LAST_NAME)
        .avatar(AVATAR)
        .build();

    Mockito.when(repository.save(Mockito.any(Contact.class))).thenReturn(contact);
    contactService.create(Contact.builder().email(EMAIL).createdAt(CREATED_AT).firstName(FIRST_NAME).lastName(LAST_NAME).avatar(AVATAR).build());

    ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
    Mockito.verify(repository).save(contactCaptor.capture());
    Mockito.verify(mapper).map(contact, ContactDTO.class);

    assertEquals(EMAIL, contactCaptor.getValue().getEmail());
    assertEquals(CREATED_AT, contactCaptor.getValue().getCreatedAt());
    assertEquals(FIRST_NAME, contactCaptor.getValue().getFirstName());
    assertEquals(LAST_NAME, contactCaptor.getValue().getLastName());
    assertEquals(AVATAR, contactCaptor.getValue().getAvatar());
  }

  @Test
  void testFindById() throws Exception {
    var id = "1";
    var contact = Contact.builder().build();
    Mockito.when(repository.findById(id)).thenReturn(Optional.of(contact));

    contactService.findById(id);
    Mockito.verify(repository).findById(id);
  }

  @Test
  void testGetContacts() throws Exception {
    var contact1 = ContactDTO.builder()
        .id("115")
        .createdAt(LocalDateTime.of(2022, Month.FEBRUARY, 18, 16, 32, 23, 57))
        .email("Kirk.Fritsch931@hotmail.com")
        .firstName("Michelle")
        .lastName("Gaylord")
        .avatar("https://cdn.fakercloud.com/avatars/dshster_128.jpg")
        .build();

    var contact2 = ContactDTO.builder()
        .id("116")
        .createdAt(LocalDateTime.of(2022, Month.FEBRUARY, 18, 18, 9, 28, 68))
        .email("Corbin.Abshire401@yahoo.com")
        .firstName("Deborah")
        .lastName("Schinner")
        .avatar("https://cdn.fakercloud.com/avatars/spacewood__128.jpg")
        .build();

    var contacts = this.getContacts();
    assertEquals(contacts != null ? contacts.get(0) : null, contact1);
    assertEquals(contacts != null ? contacts.get(1) : null, contact2);
  }

  /**
   * Simulates a list of contacts from https://challenge.trio.dev/api/v1/contacts
   * @return List of contacts from json file containing all contacts
   */
  private List<ContactDTO> getContacts() {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());

      final BufferedReader br = new BufferedReader(new FileReader("json/contacts.json"));
      final ParameterizedTypeReference<List<ContactDTO>> typeRef = new ParameterizedTypeReference<>() {
      };
      final TypeReference<Object> typeReference = new TypeReference<>() {
        public Type getType() {
          return typeRef.getType();
        }
      };
      return (List<ContactDTO>) mapper.readValue(br, typeReference);
    } catch (final IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Error reading contacts from json file");
    }
  }

}
