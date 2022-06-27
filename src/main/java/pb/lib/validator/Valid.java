package pb.lib.validator;

import pb.lib.controller.Contact;
import pb.lib.type.ContactType;

import java.util.Arrays;

public class Valid {

    /**
     * Checks if number is valid
     *
     * @param number e.g 0691234644
     * @return boolean
     */
    public static boolean isValidNumber(String number) {
        return number.matches("^[0-9]{10}$");
    }

    /**
     * Checks if type is valid
     *
     * @param contactType ContactType
     * @return boolean
     */
    public static boolean isValidContactType(String contactType) {
        return Arrays.stream(ContactType.values()).map(Enum::name).anyMatch(code -> code.equals(contactType));
    }

    /**
     * Checks if contact is valid
     *
     * @param contact Contact
     * @return boolean
     */
    public static boolean isValidContact(Contact contact) {
        if (!isValidNumber(contact.getNumber())) {
            System.out.println("Please enter a valid number. e.g 0691234644");
            return false;
        }

        if (!isValidContactType(contact.getType().toString())) {
            System.out.println("Please enter a valid type. e.g Work, Cellphone or Home");
            return false;
        }
        return true;
    }

}
