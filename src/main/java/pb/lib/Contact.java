package pb.lib;

import java.util.UUID;

//POJO
public class Contact {

    //id
    private UUID id;

    // first and last name
    private String fullName;

    // Work, Cellphone or Home
    // todo: enum
    private String type;

    // Contact no
    // todo: number must be unique
    private String number;

    public Contact() {
        setId(UUID.randomUUID());
    }


    public UUID getId() {
        return id;
    }

    //ID cannot be set by user
    private void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", fullname=" + fullName + ", type=" + type + ", number=" + number + "]";
    }
}
