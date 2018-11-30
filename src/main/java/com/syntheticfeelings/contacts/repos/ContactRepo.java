package com.syntheticfeelings.contacts.repos;

import com.syntheticfeelings.contacts.model.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepo extends CrudRepository<Contact, Long> {

}
