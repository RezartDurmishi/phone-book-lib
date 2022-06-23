package pb.lib;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ContactController { //todo: make it abstract

    //file path
    private final String path = "C:\\Users\\user\\IdeaProjects\\PhoneBookLib\\contacts.json";
    private final ObjectMapper mapper = new ObjectMapper();

    //create a new contact
    public void create(Contact contact) {
        Map<String, Object> contacts = new HashMap<>();
        List<Contact> contactsList = new ArrayList<>();
        contacts.put("contacts", contactsList.add(contact));

        //only on file initialization
        if (!new File(path).isFile()) {
            writeToFile(contacts);
            return;
        }

        //read file and append new values
        Map<String, Object> allContacts = readFromFile();
        List<Contact> contactList = mapper.convertValue(
                allContacts.get("contacts"),
                new TypeReference<>() {
                });

        allContacts.put("contacts", contactList.add(contact));
        writeToFile(allContacts);
    }

    //delete contact by uuid
    public void delete(String contactId) {
        UUID id = UUID.fromString(contactId);
        if (!new File(path).isFile()) {
            return;
        }

        Map<String, Object> allContacts = readFromFile();
        List<Contact> contactList = mapper.convertValue(
                allContacts.get("contacts"), new TypeReference<>() {
                });

        Optional<Contact> contactToDelete = contactList.stream()
                .filter(contact -> contact.getId().equals(id)).toList().stream().findFirst();

        if (contactToDelete.isPresent()) {
            contactList.remove(contactToDelete.get());
            allContacts.put("contacts", contactList);
            writeToFile(allContacts);
        }
    }

    private Map<String, Object> readFromFile() {
        Map<String, Object> existingContacts = null;
        try {
            existingContacts = mapper.readValue(new File(path), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingContacts;
    }

    private void writeToFile(Map<String, Object> contact) {
        try {
            mapper.writeValue(new FileWriter(path), contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
