package com.trio.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDTO implements Serializable {

  private String id;
  private LocalDateTime createdAt;
  private String firstName;
  private String lastName;
  private String email;
  private String avatar;

}
