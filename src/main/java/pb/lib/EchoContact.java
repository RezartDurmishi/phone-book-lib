package pb.lib;

public class EchoContact {

    static public void main(String[] args) {
        ContactController contactController = new ContactController();
//        Contact contact = new Contact();
//        contact.setFullName("john doe");
//        contact.setType("Work");
//        contact.setNumber("0691234567");
//
//        contactController.create(contact);

        contactController.delete("30ac75f8-1a5c-48c3-a324-7bf4c9cfd4ba");
    }
}
