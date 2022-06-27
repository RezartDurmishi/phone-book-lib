package pb.lib.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static pb.lib.constants.Keyword.CONTACTS;
import static pb.lib.util.Utils.getContactList;
import static pb.lib.util.Utils.getUuidFromString;
import static pb.lib.validator.Valid.isValidContact;

/**
 * Contact controller
 */
public final class ContactController {

    /**
     * Logger instance
     */
    private static final Logger LOGGER = Logger.getLogger("InfoLogging");

    /**
     * file paths
     */
    private final String jsonFilePath = System.getProperty("user.dir") + "\\contacts.json";
    private final String binaryFilePath = System.getProperty("user.dir") + "\\contacts.bin";

    /**
     * ObjectMapper instance
     */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * create a new contact
     *
     * @param contact Contact
     */
    public void create(Contact contact) {

        if (!isValidContact(contact)) {
            return;
        }

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
        LOGGER.info("New contact created successfully.");
    }

    /**
     * update contact by id
     *
     * @param contact   Contact
     * @param contactId String
     */
    public void update(Contact contact, String contactId) {
        UUID id = getUuidFromString(contactId);

        if (!isValidContact(contact)) {
            return;
        }

        //set the existing id before updating
        contact.setId(id);

        if (!new File(jsonFilePath).isFile()) {
            return;
        }

        Map<String, Object> contactsJson = readFromJson();
        List<Contact> contactList = getContactList(contactsJson);

        if (contactList.isEmpty()) {
            return;
        }

        Optional<Contact> contactToUpdate = contactList.stream().filter(contact1 -> contact1.getId().equals(id)).toList().stream().findFirst();

        if (contactToUpdate.isPresent()) {
            contactList.set(contactList.indexOf(contactToUpdate.get()), contact);
            contactsJson.put(CONTACTS, contactList);
            writeToJson(contactsJson);
            LOGGER.info("Contact updated successfully.");
        }
    }

    /**
     * delete contact by uuid
     *
     * @param contactId string
     */
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

        Optional<Contact> contactToDelete = contactList.stream().filter(contact -> contact.getId().equals(id)).toList().stream().findFirst();

        if (contactToDelete.isPresent()) {
            contactList.remove(contactToDelete.get());
            allContacts.put(CONTACTS, contactList);
            writeToJson(allContacts);
        }
    }

    /**
     * Write object to json file
     *
     * @param contact Map<String, Object>
     */
    private void writeToJson(Map<String, Object> contact) {
        try {
            mapper.writeValue(new FileWriter(jsonFilePath), contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read from json file
     *
     * @return Map<String, Object>
     */
    private Map<String, Object> readFromJson() {
        Map<String, Object> existingContacts = null;
        try {
            existingContacts = mapper.readValue(new File(jsonFilePath), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingContacts;
    }

    /**
     * Write to binary file
     *
     * @param contact Map<String, Object>
     */
    private void writeToBinary(Map<String, Object> contact) {
        File file = new File(binaryFilePath);
        byte[] data = contact.toString().getBytes(StandardCharsets.UTF_8);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);

            LOGGER.info("Successfully written bytes to the file: " + Arrays.toString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read from binary file
     *
     * @return String
     */
    private String readFromBinary() {
        Path path = Paths.get(binaryFilePath);
        String contacts = "";
        try {

            // Verify file content
            contacts = Files.readString(path);
            LOGGER.info("Binary read value: " + contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
