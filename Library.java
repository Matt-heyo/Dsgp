package com.dsgp.library;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Library {
    private Stack undoStack;
    private LinkedList checkOutQueue;
    private List<Books> bookCollection;
    private LinkedList<Patron> patrons; 
    
    public Library() {
        undoStack = new Stack();
        checkOutQueue = new LinkedList();
        bookCollection = new ArrayList<>();
        patrons = new LinkedList<>();
        // Load existing data
        loadData();
    }

    private void loadData() {
        // Load books
        bookCollection = FileManager.loadBooksFromFile();
        // Load patrons
        patrons = new LinkedList<>(FileManager.loadPatronsFromFile());
    }

    private void saveData() {
        // Save books
        FileManager.saveBooksToFile(bookCollection);
        // Save patrons
        FileManager.savePatronsToFile(patrons);
    }
    
    public void addBook(Books book) {
        if (!bookCollection.contains(book)) {
            bookCollection.add(book);
            System.out.println("Book added: " + book.getTitle());
            saveData(); // Save after adding
        } else {
            System.out.println("Book already exists in the collection.");
        }
    }

    public void removeBook(Books book) {
        if (bookCollection.remove(book)) {
            System.out.println("Book removed: " + book.getTitle());
            saveData(); // Save after removing
        } else {
            System.out.println("Book not found in the collection.");
        }
    }

    public Books findBookByTitle(String title) {
        for (Books book : bookCollection) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;  
    }

    public Patron findPatron(int libraryCardNumber) {
        if (patrons == null || patrons.isEmpty()) {
            System.out.println("No patrons registered in the library.");
            return null;
        }
        
        for (Patron patron : patrons) {
            if (patron != null && patron.getLibraryCardNumber() == libraryCardNumber) {
                return patron;
            }
        }
        System.out.println("Patron not found with library card number: " + libraryCardNumber);
        return null;
    }

    public void checkOutBook(String title, String libraryCardNumber) {
        try {
            int cardNumber = Integer.parseInt(libraryCardNumber);
            Books book = findBookByTitle(title);
            Patron patron = findPatron(cardNumber);

            if (patron == null) {
                return;  // findPatron already printed the error message
            }
            
            if (book == null) {
                System.out.println("Book not found with title: " + title);
                return;
            }

            if (book.getAmount() > 0) {
                book.setAmount(book.getAmount() - 1);
                patron.addCheckedOutBook(book);
                undoStack.push(book);
                checkOutQueue.add(book);
                System.out.println("Checked out: " + title);
                patron.displayPatronInfo();
                saveData();
                FileManager.saveCheckoutHistory(libraryCardNumber, String.valueOf(book.getISBN()), "CHECKOUT", new java.util.Date());
            } else {
                System.out.println("No copies available for checkout.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid library card number format. Please enter a valid number.");
        }
    }

    public void checkInBook(String title, String libraryCardNumber) {
        try {
            int cardNumber = Integer.parseInt(libraryCardNumber);
            Books book = findBookByTitle(title);
            Patron patron = findPatron(cardNumber);

            if (patron == null) {
                return;  // findPatron already printed the error message
            }
            
            if (book == null) {
                System.out.println("Book not found with title: " + title);
                return;
            }

            // Check if the book is in the patron's checked out books
            if (patron.getCheckedOutBooks().contains(book)) {
                book.setAmount(book.getAmount() + 1);
                patron.removeCheckedOutBook(book.getTitle());
                System.out.println("Checked in: " + title);
                patron.displayPatronInfo();
                saveData();
                FileManager.saveCheckoutHistory(libraryCardNumber, String.valueOf(book.getISBN()), "CHECKIN", new java.util.Date());
            } else {
                System.out.println("This book was not checked out by this patron.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid library card number format. Please enter a valid number.");
        }
    }
    
    public void undoLastCheckOut() {
        if (!undoStack.isEmpty()) {
            Books book = (Books) undoStack.pop();
            book.setAmount(book.getAmount() + 1);
            checkOutQueue.removeFirst();
            System.out.println("Undo last check-out: " + book.getTitle() + " is now available.\n");
            saveData(); // Save after undo
        } else {
            System.out.println("No check-outs to undo.\n");
        }
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
        saveData(); // Save after adding patron
    }
}
	
