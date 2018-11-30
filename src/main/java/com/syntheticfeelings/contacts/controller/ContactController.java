package com.syntheticfeelings.contacts.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syntheticfeelings.contacts.model.Contact;
import com.syntheticfeelings.contacts.repos.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RestController
@RequestMapping("/hello/contacts")
public class ContactController {

    private Gson gson = new GsonBuilder().create();

    @Autowired
    private ContactRepo contactRepo;

    @RequestMapping(params = "nameFilter", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getMatching(@RequestParam("nameFilter") String regex) {
        Iterable<Contact> contactList = contactRepo.findAll();
        ArrayList<Contact> matches = new ArrayList<>();
        try {
            Pattern p = Pattern.compile(regex);
            for (Contact s : contactList) {
                if (!p.matcher(s.getName()).matches()) {
                    matches.add(s);
                }
            }
        } catch (PatternSyntaxException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(gson.toJson(matches), HttpStatus.OK);
    }

    @RequestMapping(params = "newContact", method = RequestMethod.POST)
    public ResponseEntity<String> addContact(@RequestParam("newContact") String name) {
        Contact contact = new Contact(name);
        contactRepo.save(contact);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
