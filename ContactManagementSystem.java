import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ContactManagementSystem {

    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        ContactUI ui = new ContactUI(manager);
        ui.start();
    }
}

class Contact {
    private String id;
    private String name;
    private String phone;
    private String email;

    public Contact(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters with basic validation
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be empty");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Phone: %s | Email: %s", 
                id, name, phone, email);
    }
}

class ContactManager {
    private final List<Contact> contacts;
    private int nextId;

    public ContactManager() {
        this.contacts = new ArrayList<>();
        this.nextId = 1;
    }

    // CRUD Operations
    public Contact createContact(String name, String phone, String email) {
        String id = "D" + nextId++;
        Contact newContact = new Contact(id, name, phone, email);
        contacts.add(newContact);
        return newContact;
    }

    public List<Contact> readAllContacts() {
        return new ArrayList<>(contacts); // Return a copy to preserve encapsulation
    }

    public Contact readContact(String id) {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean updateContact(String id, String name, String phone, String email) {
        Contact contact = readContact(id);
        if (contact != null) {
            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);
            return true;
        }
        return false;
    }

    public boolean deleteContact(String id) {
        Contact contact = readContact(id);
        if (contact != null) {
            contacts.remove(contact);
            return true;
        }
        return false;
    }
}

class ContactUI {
    private final ContactManager manager;
    private final Scanner scanner;

    public ContactUI(ContactManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Contact Management System");

        while (true) {
            displayMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addContact();
                    case 2 -> viewAllContacts();
                    case 3 -> viewContact();
                    case 4 -> updateContact();
                    case 5 -> deleteContact();
                    case 6 -> {
                        System.out.println("Exiting system. Goodbye!");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nData:");
        System.out.println("1. Add Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. View Contact by ID");
        System.out.println("4. Update Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addContact() {
        System.out.println("\nAdd New Contact");
        System.out.println("---------------");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter email (optional): ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) email = null;
        
        Contact contact = manager.createContact(name, phone, email);
        System.out.println("Contact added successfully: " + contact);
    }

    private void viewAllContacts() {
        System.out.println("\nAll Contacts");
        System.out.println("------------");
        
        List<Contact> contacts = manager.readAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    private void viewContact() {
        System.out.println("\nView Contact");
        System.out.println("------------");
        
        System.out.print("Enter contact ID: ");
        String id = scanner.nextLine();
        
        Contact contact = manager.readContact(id);
        if (contact != null) {
            System.out.println(contact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    private void updateContact() {
        System.out.println("\nUpdate Contact");
        System.out.println("--------------");
        
        System.out.print("Enter contact ID to update: ");
        String id = scanner.nextLine();
        
        Contact existing = manager.readContact(id);
        if (existing == null) {
            System.out.println("Contact not found.");
            return;
        }
        
        System.out.println("Current details: " + existing);
        System.out.println("Enter new details (leave blank to keep current):");
        
        System.out.print("Name [" + existing.getName() + "]: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) name = existing.getName();
        
        System.out.print("Phone [" + existing.getPhone() + "]: ");
        String phone = scanner.nextLine();
        if (phone.trim().isEmpty()) phone = existing.getPhone();
        
        System.out.print("Email [" + (existing.getEmail() != null ? existing.getEmail() : "none") + "]: ");
        String email = scanner.nextLine();
        if (email.trim().isEmpty()) email = existing.getEmail();
        
        if (manager.updateContact(id, name, phone, email)) {
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Failed to update contact.");
        }
    }

    private void deleteContact() {
        System.out.println("\nDelete Contact");
        System.out.println("--------------");
        
        System.out.print("Enter contact ID to delete: ");
        String id = scanner.nextLine();
        
        if (manager.deleteContact(id)) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }
}