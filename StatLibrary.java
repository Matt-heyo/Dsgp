package com.dsgp.library;

import java.util.ArrayList;
import java.util.List;

public class StatLibrary {
		private int totalBooks;
		private int totalPatrons;
		private int totalCheckouts;

	    private List<Books> books;
	    private List<Patron> patrons;

	    // Default constructor
	    public StatLibrary() {
	        books = new ArrayList<>();
	        patrons = new ArrayList<>();
	        totalBooks = 0;
	        totalCheckouts=0;
	    }

	    // Copy constructor
	    public StatLibrary(Library other) {
	        this.books = new ArrayList<>();
	        this.patrons = new ArrayList<>();
	    }
	    public List<Books> getBooks() {
	    	return books;
	    	}
	    public List<Patron> getPatrons() {
	    	return patrons;
	    }

	    // Add a book to the library
	    public void addBook(Books book) {
	    	for (Books b : books) {
	    		if (b.getISBN() == book.getISBN()) {
	    		// If it exists, update the amount instead of adding
	    		b.setAmount(b.getAmount() + book.getAmount());
	    			return; // Exit the method
	    		 }
	    		  }
	        books.add(book);  // Add book to the list
	        totalBooks += book.getAmount();  // Update total book count with the amount of copies
	    }

	    // Add a patron to the library
	    public void addPatron(Patron patron) {
	        patrons.add(patron);
	    }

	    // Get total number of books
	    public int getTotalBooks() {
	    	 return totalBooks;
	    }
	    
	    public void removeBook(Books book, int amount) {
	    		if (books.contains(book)) {
	    			books.remove(book); // Remove the book from the list
	    			totalBooks -= amount; // Decrease the total number of books by the amount
	          }
	     }

	    // Get total number of patrons
	    public int getTotalPatrons() {
	        return patrons.size();
	    }

	    // Get total number of current checkouts
	    public int getTotalCheckouts() {
	        int totalCheckouts = 0;
	        for (Patron patron : patrons) {
	            totalCheckouts += patron.getCheckedOutBooks().size();
	        }
	        return totalCheckouts;
	    }
	    public boolean removePatron(int libraryCardNumber) {
	    	for (Patron patron : patrons) {
	    		if (patron.getLibraryCardNumber() == libraryCardNumber) {
	    			patrons.remove(patron);
	    			totalPatrons--;
	    			return true;
	    		   }
	    		 }
	    			return false; // Patron not found by library card number
	    	  }
	    public void checkoutBook() {
	    	totalCheckouts++; // Increase the count of current checkouts
	    	 }

	    // Display statistics
	    public void displayStatistics() {
	        int totalBooks = 0; 
	        int totalPatrons = patrons.size();  
	        int totalCheckouts = 0;  

	        System.out.println("Book Quantities in the Library:");
	        for (Books book : books) {
	            int quantity = book.getAmount();  // Get the quantity of each book
	            totalBooks += quantity;  // Add the quantity to the total
	            System.out.println(book.getTitle() + ": " + quantity + " copies");  // Print the quantity for each book
	        }

	        // Count the total current checkouts
	        for (Patron patron : patrons) {
	            totalCheckouts += patron.getCheckedOutBooks().size();
	        }

	        // Display the statistics
	        System.out.println("\nLibrary Statistics:\n");
	        System.out.println("Total Books: " + totalBooks);
	        System.out.println("Total Patrons: " + totalPatrons);
	        System.out.println("Total Current Checkouts: \n" + totalCheckouts);
	    }


	    // Display all books
	    public void displayAllBooks() {
	        for (Books book : books) {
	            book.Display();
	            System.out.println("----------------------------");
	        }
	    }

	    // Display all patrons
	    public void displayAllPatrons() {
	        for (Patron patron : patrons) {
	            patron.displayPatronInfo();
	            System.out.println("----------------------------");
	        }
	    }
	}



