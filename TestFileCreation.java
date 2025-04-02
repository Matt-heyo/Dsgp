package com.dsgp.library;

import java.util.ArrayList;
import java.util.List;

public class TestFileCreation {
    public static void main(String[] args) {
        // Create a test book
        Books book = new Books("Test Book", "Test Author", 123456, 2024, 1);
        
        // Create a test patron
        Patron patron = new Patron("Test Patron", 1);
        
        // Create a test user
        User user = new User("testuser", "testpass", "user");
        
        // Save books
        List<Books> books = new ArrayList<>();
        books.add(book);
        FileManager.saveBooksToFile(books);
        
        // Save patrons
        List<Patron> patrons = new ArrayList<>();
        patrons.add(patron);
        FileManager.savePatronsToFile(patrons);
        
        // Save users
        List<User> users = new ArrayList<>();
        users.add(user);
        FileManager.savePasswordsToFile(users);
        
        System.out.println("Test files created successfully!");
    }
} 