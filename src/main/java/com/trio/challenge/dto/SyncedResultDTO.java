package com.trio.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SyncedResultDTO {

  private long syncedContacts;
  private List<SyncedContactDTO> contacts;

}
