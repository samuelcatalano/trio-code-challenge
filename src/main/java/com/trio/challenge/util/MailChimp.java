package com.trio.challenge.util;

import lombok.extern.slf4j.Slf4j;

import com.github.alexanderwe.bananaj.connection.MailChimpConnection;
import com.github.alexanderwe.bananaj.exceptions.TransportException;
import com.github.alexanderwe.bananaj.model.list.MailChimpList;
import com.github.alexanderwe.bananaj.model.list.member.EmailType;
import com.github.alexanderwe.bananaj.model.list.member.Member;
import com.github.alexanderwe.bananaj.model.list.member.MemberStatus;
import com.trio.challenge.exception.ConfigurationException;
import com.trio.challenge.model.Contact;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MailChimp {

  private static final String DEFAULT_IP_ADDRESS = "127.0.0.1";

  @Value("${mailchimp.api.key}")
  private String apiKey;

  @Value("${mailchimp.list.default.id}")
  private String defaultListId;

  /**
   * Subscribes to mailchimp on the specified list.
   * @param contacts the contacts of list to subscribe to mailchimp on the specified list
   * @throws ConfigurationException to be thrown if an error occurs during communication with mailchimp server or client
   */
  public List<Contact> subscribe(final List<Contact> contacts) throws ConfigurationException {
    try {
      final List<Contact> updatedContacts = new ArrayList<>();
      final MailChimpConnection connection = new MailChimpConnection(apiKey);
      final MailChimpList trioBackendProject = connection.getList(defaultListId);

      for (final Contact contact : contacts) {
        this.addMember(contact, trioBackendProject);
        contact.setSynced(true);
        updatedContacts.add(contact);
      }

      log.info("Contacts synced and stored: " + updatedContacts.size());
      return updatedContacts;
    } catch (final MalformedURLException | TransportException | URISyntaxException e) {
      log.error("Error subscribing to mailchimp list! Message {}", e.getMessage(), e);
      throw new ConfigurationException(e.getMessage(), e);
    }
  }

  /**
   * Adds a new contact to the list of mailchimp contacts.
   * @param contact the contact to add to the list of mailchimp contacts
   * @param list the list of mailchimp contacts
   * @throws ConfigurationException to be thrown if an error occurs during communication with the list of mailchimp contacts
   */
  private void addMember(final Contact contact, final MailChimpList list) throws ConfigurationException {
    try {
      final Map<String, Object> mergeFields = new HashMap<>();
      mergeFields.put("FNAME", contact.getFirstName());
      mergeFields.put("LNAME", contact.getLastName());

      final String ipAddress = DEFAULT_IP_ADDRESS;
      final LocalDateTime timeStamp = contact.getCreatedAt();

      final Member member = new Member.Builder()
                  .list(list)
                  .emailAddress(contact.getEmail())
                  .emailType(EmailType.HTML)
                  .status(MemberStatus.SUBSCRIBED)
                  .mergeFields(mergeFields)
                  .statusIfNew(MemberStatus.SUBSCRIBED)
                  .ipSignup(ipAddress)
                  .timestampSignup(timeStamp)
                  .ipOpt(ipAddress)
                  .timestampOpt(timeStamp)
                  .build();

      list.addOrUpdateMember(member);
    } catch (final MalformedURLException | TransportException | URISyntaxException e) {
      log.error("Error adding member to mailchimp list! Message {}", e.getMessage(), e);
      throw new ConfigurationException(e.getMessage(), e);
    }
  }
}
