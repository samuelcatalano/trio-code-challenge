package com.trio.challenge.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseModel implements Serializable {

  @Id
  @Column(updatable = true, insertable = true)
  private String id;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

}
