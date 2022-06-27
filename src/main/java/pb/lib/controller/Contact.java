package pb.lib.controller;

import pb.lib.type.ContactType;

import java.util.UUID;

/**
 * Contact model
 */
public final class Contact {

    //id
    private UUID id;

    // first and last name
    private String fullName;

    // Work, Cellphone or Home
    private ContactType type;

    // Contact no
    // todo: number must be unique
    private String number;

    public Contact() {
        setId(UUID.randomUUID());
    }


    public UUID getId() {
        return id;
    }

    //ID cannot be set by user (protected)
    void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = ContactType.valueOf(type);
    }

    //used for binary file
    @Override
    public String toString() {
        return "Contact [id=" + id + ", fullname= " + fullName + ", type= " + type + ", number= " + number + "]";
    }
}
