package BookManagement;

import java.util.LinkedList;

public class Patron {
	private String name;
    private int libraryCardNumber;
    private LinkedList<Books> checkedOutBooks; // List of checked-out books

    public Patron() {
        this.name = "Simone Smith";
        this.libraryCardNumber = 0001;
        this.checkedOutBooks = new LinkedList<>();
    }
    
    // Constructor for patron with name and library card number
    public Patron(String name, int libraryCardNumber) {
        this.name = name;
        this.libraryCardNumber = libraryCardNumber;
        this.checkedOutBooks = new LinkedList<>(); 
    }
    
    //Copy Constructor
    public Patron(Patron cp)
    {
    	this.name = cp.name;
        this.libraryCardNumber = cp.libraryCardNumber;
        this.checkedOutBooks = new LinkedList<>(cp.checkedOutBooks);
    }
    public void addCheckedOutBook(Books book) {
        checkedOutBooks.add(book);
    }
    public void removeCheckedOutBook(Books book) {
    		if (checkedOutBooks.contains(book)) {
    			checkedOutBooks.remove(book);
    		} else {
    			System.out.println("Book not found in checked-out books.");
    		 }
    }
   
    // Getters and Setters
    public String getName() 
    {
        return name;
    }
    
    public void setName(String name)
    {
    	this.name= name;
    }

    public int getLibraryCardNumber() {
    	
        return libraryCardNumber;
    }

    
 public void setLibraryCardNumber(int libraryCardNumber) 
 	{
        this.libraryCardNumber = libraryCardNumber;
    }
 
 
 public void checkOutBook(Books book) {
     if (checkedOutBooks == null) {
         checkedOutBooks = new LinkedList<>();  
     }
     checkedOutBooks.add(book);  // Add book to the checked-out list
 }
 public LinkedList<Books> getCheckedOutBooks() {
     return checkedOutBooks;
 }
 public void removeCheckedOutBook(String title) {
	    if (checkedOutBooks.contains(title)) {
	        checkedOutBooks.remove(title);
	    } else {
	        System.out.println("Book title not found in checked-out books.");
	    }
	}

    // Display patron info
    	public void displayPatronInfo() {
    		if (name == null || name.isEmpty()) {  
    	        System.out.println("Error: No patron exists.");
    	        return;
    	    }
            System.out.println("Patron Name: " + name);
            System.out.println("Library Card Number: " + libraryCardNumber);
            System.out.print("Checked-out Books: ");
            if (checkedOutBooks.isEmpty()) {
                System.out.println("None");
            } else {
                for (Books book : checkedOutBooks) {
                    System.out.print(book.getTitle() + ", ");
                }
                System.out.println();
            }
        }

	
}
