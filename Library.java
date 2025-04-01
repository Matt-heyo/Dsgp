package BookManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
	    private Stack undoStack;
	    private Queue checkOutQueue;
	    private List<Books> bookCollection;
	    private Map<String, Patron> patrons; 
	    
	    public Library() {
	        undoStack = new Stack();
	        checkOutQueue = new Queue();
	        bookCollection = new ArrayList<>();
	        patrons = new HashMap<>();

	    }
	    public void addBook(Books book) {
	        if (!bookCollection.contains(book)) {
	            bookCollection.add(book);
	            System.out.println("Book added: " + book.getTitle());
	        } else {
	            System.out.println("Book already exists in the collection.");
	        }
	    }
	    public void removeBook(Books book) {
	        if (bookCollection.remove(book)) {
	            System.out.println("Book removed: " + book.getTitle());
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

	    public void checkOutBook(String title, String libraryCardNumber) {
	        Books book = findBookByTitle(title);
	        Patron patron = patrons.get(libraryCardNumber);

	        if (book != null && book.isAvailable() && patron != null) {
	            book.setAvailable(false);
	            patron.addCheckedOutBook(book);
	            undoStack.push(book);
	            checkOutQueue.enqueue(book);
	            System.out.println("Checked out: " + title);
	            patron.displayPatronInfo(); // Display updated list
	        } else {
	            System.out.println("Book is not available for checkout or patron not found.");
	        }
	    }

	    public void checkInBook(String title, String libraryCardNumber) {
	        Books book = findBookByTitle(title);
	        Patron patron = patrons.get(libraryCardNumber);

	        if (book != null && !book.isAvailable() && patron != null) {
	            book.setAvailable(true);
	            patron.removeCheckedOutBook(book.getTitle());
	            System.out.println("Checked in: " + title);
	            patron.displayPatronInfo(); // Display updated list
	        } else {
	            System.out.println("Book was not checked out or patron not found.");
	        }
	    }
	    
	    public void undoLastCheckOut() {
	        if (!undoStack.isEmpty()) {
	            Books book = undoStack.pop();
	            book.setAvailable(true);
	            checkOutQueue.dequeue();
	            System.out.println("Undo last check-out: " + book.getTitle() + " is now available.\n");
	        } else {
	            System.out.println("No check-outs to undo.\n");
	        }
	    }	}
	
