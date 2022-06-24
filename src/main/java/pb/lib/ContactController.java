package pb.lib;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

public class ContactController {

    //file paths
    private final String jsonFilePath = "C:\\Users\\user\\IdeaProjects\\PhoneBookLib\\contacts.json";
    private final String binaryFilePath = "C:\\Users\\user\\IdeaProjects\\PhoneBookLib\\contacts.bin";

    private final ObjectMapper mapper = new ObjectMapper();
    private final String CONTACTS = "contacts";

    //create a new contact
    public void create(Contact contact) {
        Map<String, Object> contacts = new HashMap<>();
        List<Contact> contactsList = new ArrayList<>();
        contactsList.add(contact);
        contacts.put(CONTACTS, contactsList);

        //only on file initialization
        if (!Paths.get(jsonFilePath).toFile().exists()) {
            writeToJson(contacts);
            return;
        }

        //read file and append new values
        Map<String, Object> contactsJson = readFromJson();
        List<Contact> contactList = getContactList(contactsJson);
        contactList.add(contact);

        contactsJson.put(CONTACTS, contactList);
        writeToJson(contactsJson);
    }

    //update contact by id
    public void update(Contact contact, String contactId) {
        UUID id = getUuidFromString(contactId);

        if (!new File(jsonFilePath).isFile()) {
            return;
        }

        Map<String, Object> contactsJson = readFromJson();
        List<Contact> contactList = getContactList(contactsJson);

        if (contactList.isEmpty()) {
            return;
        }

        Optional<Contact> contactToUpdate = contactList.stream()
                .filter(contact1 -> contact1.getId().equals(id)).toList().stream().findFirst();

        if (contactToUpdate.isPresent()) {
            contactList.set(contactList.indexOf(contactToUpdate.get()), contact);
            contactsJson.put(CONTACTS, contactList);
            writeToJson(contactsJson);
        }
    }

    //delete contact by uuid
    public void delete(String contactId) {
        UUID id = getUuidFromString(contactId);

        if (!new File(jsonFilePath).isFile()) {
            return;
        }

        Map<String, Object> allContacts = readFromJson();
        List<Contact> contactList = getContactList(allContacts);

        if (contactList.isEmpty()) {
            return;
        }

        Optional<Contact> contactToDelete = contactList.stream()
                .filter(contact -> contact.getId().equals(id)).toList().stream().findFirst();

        if (contactToDelete.isPresent()) {
            contactList.remove(contactToDelete.get());
            allContacts.put(CONTACTS, contactList);
            writeToJson(allContacts);
        }
    }

    private Map<String, Object> readFromJson() {
        Map<String, Object> existingContacts = null;
        try {
            existingContacts = mapper.readValue(new File(jsonFilePath), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingContacts;
    }

    private void writeToJson(Map<String, Object> contact) {
        try {
            mapper.writeValue(new FileWriter(jsonFilePath), contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToBinary(Map<String, Object> contact) {
        File file = new File(binaryFilePath);
        byte[] data = contact.toString().getBytes(StandardCharsets.UTF_8);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readFromBinary() {
        Object contacts = null;
        try (FileInputStream fileInputStreams = new FileInputStream(binaryFilePath)) {
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStreams);

            contacts = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    private UUID getUuidFromString(String contactId) {
        return UUID.fromString(contactId);
    }

    private List<Contact> getContactList(Map<String, Object> allContacts) {
        return mapper.convertValue(
                allContacts.get(CONTACTS), new TypeReference<>() {
                });
    }
}
