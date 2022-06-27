package pb.lib.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pb.lib.controller.Contact;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static pb.lib.constants.Keyword.CONTACTS;

/**
 * Utils class
 */
public final class Utils {

    /**
     * ObjectMapper instance
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Get Uuid
     *
     * @param contactId
     * @return UUID
     */
    public static UUID getUuidFromString(String contactId) {
        return UUID.fromString(contactId);
    }

    /**
     * Gets contacts list from json
     *
     * @param allContacts contacts object
     * @return List<Contact>
     */
    public static List<Contact> getContactList(Map<String, Object> allContacts) {
        return mapper.convertValue(allContacts.get(CONTACTS), new TypeReference<>() {
        });
    }
}
