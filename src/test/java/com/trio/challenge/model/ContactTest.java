package com.trio.challenge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.trio.challenge.dto.ContactDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
class ContactTest {

  private final ModelMapper mapper = new ModelMapper();
  private ContactDTO contactDTO;

  @BeforeEach
  void setup() {
    contactDTO = ContactDTO.builder()
    .id("123")
    .email("samuel.catalano@gmail.com")
    .createdAt(LocalDateTime.now())
    .firstName("Samuel")
    .lastName("Catalano")
    .avatar("https://cdn.fakercloud.com/avatars/samcatalano_123.jpg")
    .build();
  }

  @Test
  void testContactMapping() throws Exception {
    final Contact contact = this.mapper.map(contactDTO, Contact.class);
    assertEquals(contact.getId(), contactDTO.getId());
    assertEquals(contact.getEmail(), contactDTO.getEmail());
    assertEquals(contact.getCreatedAt(), contactDTO.getCreatedAt());
    assertEquals(contact.getFirstName(), contactDTO.getFirstName());
    assertEquals(contact.getLastName(), contactDTO.getLastName());
    assertEquals(contact.getAvatar(), contactDTO.getAvatar());
  }
}
