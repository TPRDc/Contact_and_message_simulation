package Contact_and_message_simulation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Scanner;
public class Main {
    private static ArrayList<Contact> contacts;
    private static Scanner input;
    private static int id=0;

    public static void main(String[] args) {
        contacts = new ArrayList<>();
        System.out.println("Welcome too TPRDc Phone Simulation");
        showInitialOption();
    }

    private static void showInitialOption(){
        System.out.println("""
                Please select one:
                    1. Manage Contacts
                    2. Massages
                    3. Quit""");
        input =new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1 -> manageContacts();
            case 2 -> manageMassages();
            default -> {
            }
        }
    }

    private static void manageContacts(){
        System.out.println("""
                Please select one:
                    1. show all contact
                    2. add a new contact
                    3. search for a contact
                    4. delete a contact
                    5. go back
                """);
        int choice = input.nextInt();
        switch (choice){
            case 1 -> showAllContacts();
            case 2 -> addNewContact();
            case 3 -> searchForContact();
            case 4 -> deleteContact();
            default -> showInitialOption();
        }
    }

        private static void showAllContacts(){
            if (contacts.isEmpty()) {
                System.out.println("Your contact list is empty");
                System.out.println();
            } else {
                for (Contact c: contacts) {
                    c.getDetails();
                    System.out.println();
                }
            }

            showInitialOption();
        }
        private static void addNewContact(){
            System.out.println("""
                    Adding a new contact...........
                    
                    Enter contact name:""");
            String name = input.next();

            System.out.println("Enter contact number: ");
            String number = input.next();

            System.out.println("Enter contact email: ");
            String email = input.next();

            if (name.equals("") || number.equals("") || email.equals("")){
                System.out.println("Please enter all required information");
                addNewContact();
            }else {
                boolean doseExit = false;
                for (Contact c: contacts){
                    if(c.getName().equals(name)){
                        doseExit = true;
                    }
                }

                if(doseExit){
                    System.out.println("We have a contact with this name");
                    System.out.println();
                    addNewContact();
                } else {
                    Contact newContact = new Contact(name, number, email);
                    contacts.add(newContact);
                    System.out.println(name + " added successfully");
                    System.out.println();
                }
            }

            showInitialOption();
        }
        private static void searchForContact(){
            System.out.println("Enter contact name");
            String name = input.next();

            if(name.equals("")){
                System.out.println("Please enter required information");
                searchForContact();
            }else {
                boolean doseExit = false;

                for (Contact c: contacts){
                    if(c.getName().equals(name)){
                        doseExit = true;
                        c.getDetails();
                    }
                }

                if (!doseExit){
                    System.out.println("The is no such contact in your phone");
                }
            }
            showInitialOption();
        }
        private static void deleteContact(){
                System.out.println("Enter contact name: ");
                String name = input.next();

                if (name.equals("")){
                    System.out.println("Please enter required information");
                    deleteContact();
                } else {
                    boolean doseExit = false;

                    try {
                        for (Contact c : contacts) {
                            if (c.getName().equals(name)) {
                                doseExit = true;

                                contacts.remove(c);
                                System.out.println("Contact " + name + " has been deleted successfully");
                                System.out.println();
                            }
                        }

                        if (!doseExit) {
                            System.out.println("The is no such contact in your phone");
                            System.out.println();
                        }
                    } catch (RuntimeException e) {
                        showInitialOption();
                    }
                }
            }

    private static void manageMassages(){
        System.out.println("""
                Please select one:
                    1. Show all messages
                    2. Send a new massage
                    3. Go back
                """);

        int choice = input.nextInt();
        switch (choice){
            case 1 -> showAllMassages();
            case 2 -> sendNewMassage();
            default -> showInitialOption();
        }
    }

        private static void showAllMassages(){
            ArrayList<Massage> allMassages =new ArrayList<>();
            for (Contact c: contacts){
                allMassages.addAll(c.getMassages());
            }

            if (allMassages.size()>0){
                for (Massage m: allMassages){
                    m.getDetails();
                    System.out.println();
                }
            } else {
                System.out.println("You don't have any massages ");
            }
            showInitialOption();
        }
        private static void sendNewMassage(){
            System.out.println("Enter recipient name: ");
            String name = input.next();

            if (name.equals("")){
                System.out.println("Please enter name");
                sendNewMassage();
            } else {
                boolean doseExit = false;

                for (Contact c: contacts){
                    if (c.getName().equals(name)){
                        doseExit = true;
                    }
                }

                if (doseExit){
                    System.out.println("Enter massage: ");
                    String text = input.next();

                    if (text.equals("")){
                        System.out.println("Please enter a massage");
                        sendNewMassage();
                    } else {
                        id++;
                        Massage newMassage = new Massage(text,name,id);
                        for (Contact c: contacts){

                            if ((c.getName().equals(name))){
                                ArrayList<Massage> newMassages = c.getMassages();
                                newMassages.add(newMassage);
                                System.out.println("\nmassage sent to "+name+"\n");

                                // remove sent message from contact class
                                c.setMassages(newMassages);
                                contacts.remove(c);
                                contacts.add(c);
                            }
                        }
                    }
                } else {
                    System.out.println("The is no such contact");
                }
            }
            showInitialOption();
        }
}