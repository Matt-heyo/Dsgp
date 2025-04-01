package BookManagement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Management {

		private Node head;
		private Scanner scan = new Scanner(System.in);
		private PatronNode tailPatron;
		private PatronNode headPatron;
		private LibraryBST libraryBST;
		private StatLibrary statLibrary;
		
		public Management() 
		{
			this.head=null;
			this.headPatron=null;
			this.tailPatron=null;
			this.libraryBST = new LibraryBST();
			 this.statLibrary = new StatLibrary();
			
		}
		
		public Management(StatLibrary statLibrary,LibraryBST libraryBST) {
	        this.statLibrary = statLibrary;
	        this.libraryBST = new LibraryBST();
	    }

		public Node getHead()
		{
			return head;
		}

		public void setHead(Node head)
		{
			this.head = head;
		}
		//menu for the handling of books
		public void handleBooks (int choice) {
			while (true) {
			switch(choice)
	        {
		
	        case 1:
	        	addBookOption();
	        	break;
	        case 2:
	        	deletebook ();
	        	break;
	        case 3:
	        	searchForBook();
	        	break;
	        case 4:
	        	displayBooks();
	        	
	        case 5:
	        	System.out.println("Returning to the main menu.");
	            return;
	        default:
	            System.out.println("Invalid option. Please try again.");
	        }
			System.out.println("You may choose again from the Book Management menu: ");
			System.out.println("\n*******Book Management******");
			System.out.println("1. Add a new book");
			System.out.println("2. Remove a book");
			System.out.println("3. Search for a book");
			System.out.println("4. Display all books");
			System.out.println("5. Exit");
			System.out.print("Choose an option: ");
	        choice = scan.nextInt(); 
	        scan.nextLine();
		}
		}
		
		//Menu for the patron class
		public void HandlePatron(int choice) 
		{
			while(true) {
			
			switch(choice) 
			{
			 case 1:
		        	addPatronOption();
		        	break;
		        case 2:
		        	removePatronOption ();
		        	break;
		        case 3:
		        	viewAllPatronOption();
		        	break;
		        case 4:
		        	viewPatronCheckoutBooks();
		        	System.out.println("Returning to the main menu.");
		            return;
		        case 5:
		        	System.out.println("Returning to the main menu.");
		        	return;
		        default:
		            System.out.println("Invalid option. Please try again.");
		        }
			System.out.println("You may choose again from the Patron Management menu: ");
			System.out.println("\n******* Patron Management *******");
			System.out.println("1. Add a new patron");
			System.out.println("2. Remove a patron");
			System.out.println("3. View all patrons");
			System.out.println("4. View checked-out books for a patron");
			System.out.println("5. Back to Main Menu");
			System.out.print("Choose an option: ");
	        choice = scan.nextInt();
	        scan.nextLine();
			}
}

		//Options to add book to the linked list 
		public void addBookOption() {
			try {
	            System.out.println("Enter the title of the book:");
	            String title = scan.nextLine().trim();
	            if (title.isEmpty()) {
	                throw new IllegalArgumentException("Book title cannot be empty.");
	            }

	            System.out.println("Enter the author:");
	            String author = scan.nextLine().trim();
	            if (author.isEmpty()) {
	                throw new IllegalArgumentException("Author name cannot be empty.");
	            }

	            System.out.println("Enter the ISBN:");
	            int ISBN = scan.nextInt();
	            if (ISBN <= 0) {
	                throw new IllegalArgumentException("ISBN must be a positive integer.");
	            }

	            System.out.println("Enter the publication year:");
	            int pubYear = scan.nextInt();
	            if (pubYear < 0) {
	                throw new IllegalArgumentException("Publication year cannot be negative.");
	            }

	            System.out.println("Enter the number of copies:");
	            int amount = scan.nextInt();
	            if (amount <= 0) {
	                throw new IllegalArgumentException("Amount must be a positive integer.");
	            }
	            scan.nextLine(); 
	            addBook(title, author, ISBN, pubYear, amount);
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	            scan.nextLine(); 
	        }
	    }

		
		public void addBook(String title, String author, int ISBN, int pubYear, int amount) {
		    if (title == null || title.isEmpty()) {
		        System.err.println("Error: Book title cannot be empty.");
		        return;
		    }
		    if (author == null || author.isEmpty()) {
		        System.err.println("Error: Author name cannot be empty.");
		        return;
		    }
		    if (ISBN <= 0) {
		        System.err.println("Error: ISBN must be a positive integer.");
		        return;
		    }
		    if (pubYear < 0) {
		        System.err.println("Error: Publication year cannot be negative.");
		        return;
		    }
		    if (amount <= 0) {
		        System.err.println("Error: Amount must be a positive integer.");
		        return;
		    }

		    // Search for the book in the libraryBST
		    Books existingBook = libraryBST.searchByISBN(ISBN);

		    if (existingBook != null) {
		        // If the book exists, update the amount in libraryBST
		        existingBook.setAmount(existingBook.getAmount() + amount);
		        System.out.println(amount + " more copies of " + title + " are added.");
		    } else {
		        // Otherwise, create a new book
		        Books newBook = new Books(title, author, ISBN, pubYear, amount);
		        libraryBST.insert(newBook);
		        System.out.println("Added Book: " + title + " with amount: " + amount + " copies.");

		        // Check if the book exists in statLibrary
		        updateStatLibrary(newBook);
		    }
		}

		private void updateStatLibrary(Books newBook) {
		    // Check if the book already exists in statLibrary
		    boolean bookExistsInStatLibrary = false;
		    for (Books book : statLibrary.getBooks()) {
		        if (book.getISBN() == newBook.getISBN()) {
		            book.setAmount(book.getAmount() + newBook.getAmount());
		            bookExistsInStatLibrary = true;
		            break;
		        }
		    }

		    // If the book does not exist, add it to statLibrary and linked list
		    if (!bookExistsInStatLibrary) {
		        statLibrary.addBook(newBook);
		        addBookToLinkedList(newBook);
		    }
		}

		private void addBookToLinkedList(Books newBook) {
		    Node newNode = new Node(newBook);
		    if (head == null) {
		        head = newNode;
		    } else {
		        Node temp = head;
		        while (temp.nextNode != null) {
		            temp = temp.nextNode;
		        }
		        temp.nextNode = newNode;
		    }
		}

		
		public int getAmount(String title)
		{
			int count=0;
			Node temp=head;
			while (temp !=null) 
			{
				if(temp.book.getTitle().equals(title))
				{
					count+=temp.book.getAmount();
				}
				temp=temp.nextNode;
			}
			return count;
		}
		//This displays all the book that are in the linked list
		public List<Books> displayBooks()
		{
			List<Books> bookList = new ArrayList<>();
			if(head ==null) {
				System.out.println("There is no book on the system");
				return bookList;
			}
			Node temp=head;
			while(temp!=null) 
			{
				temp.book.Display();
				bookList.add(temp.book);
				temp=temp.nextNode;
			}
			return bookList; 
		}
		//option to search for a book
		public void searchForBook() {
			System.out.println("Search for a book by:");
	    	System.out.println("1. Title");
	    	System.out.println("2. Author");
	    	System.out.println("3. ISBN");
	        System.out.println("4. Exit");
	        System.out.print("Choose an option: ");
	        int searchbyChoice = scan.nextInt();
	        scan.nextLine();
	       
			switch (searchbyChoice) {
			case 1:
				System.out.print("Enter the title of the book to search: ");
				String searchTitle = scan.nextLine();
				Books foundBook = libraryBST.searchByTitle(searchTitle);
				System.out.println(foundBook != null ? "Book Found: " + foundBook : "Book Not Found");
				break;
			case 2:
				System.out.print("Enter the author of the book to search: ");
				String searchAuthor = scan.nextLine();
				foundBook = libraryBST.searchByAuthor(searchAuthor);
				System.out.println(foundBook != null ? "Book Found: " + foundBook : "Book Not Found");
				break;
			case 3:
				System.out.print("Enter the ISBN of the book to search: ");
				int searchISBN = scan.nextInt();
				foundBook = libraryBST.searchByISBN(searchISBN);
				System.out.println(foundBook != null ? "Book Found: " + foundBook : "Book Not Found");
				break;
			case 4:
				System.out.println("Returning to Main Menu...");
				break;
	            
			default:
				System.out.println("Invalid option. Please try again.");
		}}
		
		//option to delete the amount of books by the title
		public void deletebook() {
			try {
	            System.out.println("Enter the name of the book you'd like to remove:");
	            String title = scan.nextLine().trim();
	            if (title.isEmpty()) {
	                throw new IllegalArgumentException("Book title cannot be empty.");
	            }

	            System.out.println("Enter the number of copies to remove:");
	            int deleteAmount = scan.nextInt();
	            if (deleteAmount <= 0) {
	                throw new IllegalArgumentException("Delete amount must be a positive integer.");
	            }
	            scan.nextLine(); // Consume newline

	            boolean removed = deleteAmount(title, deleteAmount);
	            if (removed) {
	                System.out.println("Successfully removed " + deleteAmount + " copies.");
	            } else {
	                System.err.println("Error: Not enough copies to remove or book not found.");
	            }
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	            scan.nextLine(); // Clear invalid input
	        }
	    }
		
		
		public boolean deleteAmount(String title, int deleteAmount) {
		    Books book = libraryBST.searchByTitle(title);
		    if (book != null && book.getAmount() >= deleteAmount) {
		        book.setAmount(book.getAmount() - deleteAmount);
		        statLibrary.removeBook(book, deleteAmount);  // Update statistics
		        return true;
		    }
		    return false;
		}

		//PAtron Management
		public void addPatronOption() {
		    System.out.print("Enter patron library card number: ");
		    int libraryCardNumber = scan.nextInt();
		    scan.nextLine(); // Consume the newline character
		    System.out.print("Enter patron name: ");
		    String patronName = scan.nextLine();
		    Patron newPatron = new Patron(patronName, libraryCardNumber);
		    addPatron(newPatron);
		    System.out.println("Patron added successfully!");
		}

			public void addPatron(Patron patron) {
			    PatronNode newNode = new PatronNode(patron);
			    if (tailPatron == null) {
			        headPatron = tailPatron = newNode;
			    } else {
			        tailPatron.next = newNode;
			        tailPatron = newNode;
			    }
			    statLibrary.addPatron(patron); // Add patron to StatLibrary
			    System.out.println("Added Patron: " + patron.getName()); 
			}
			
		public void checkoutBook() {
			 try {
		            System.out.print("Enter patron library card number: ");
		            int libraryCardNumber = scan.nextInt();
		            scan.nextLine(); // Consume newline

		            System.out.print("Enter book title to check out: ");
		            String inputTitle = scan.nextLine().trim();
		            if (inputTitle.isEmpty()) {
		                throw new IllegalArgumentException("Book title cannot be empty.");
		            }

		            checkoutBook(libraryCardNumber, inputTitle);
		        } catch (Exception e) {
		            System.err.println("Error: " + e.getMessage());
		            scan.nextLine(); // Clear invalid input
		        }
		    }
		
		public void checkoutBook(int libraryCardNumber, String title) {
		    Patron patron = findPatron(libraryCardNumber);
		    Books book = libraryBST.searchByTitle(title);  

		    if (book == null || patron == null) {
		        System.out.println("Book or Patron not found.");
		        return;
		    }

		    if (book.getAmount() > 0) {
		        book.setAmount(book.getAmount() - 1);
		        patron.checkOutBook(book);  // Add book to patron's checked-out list
		        statLibrary.checkoutBook(); // Update statistics
		        System.out.println(patron.getName() + " has checked out " + book.getTitle());
		    } else {
		        System.out.println("No copies available for checkout.");
		    }
		}


		public void removePatronOption()
		{
			 System.out.print("Enter the patron library card number to remove: ");
	         int patronToRemove = scan.nextInt();
	         removePatron(patronToRemove);
	         
		}
		public void removePatron(int libraryCardNumber)
		{
			PatronNode current = headPatron;
		    PatronNode previous = null;

		    while (current != null) {
		        if (current.patron.getLibraryCardNumber()==(libraryCardNumber)) {
		            if (previous == null) {
		                headPatron = current.next;
		            } else {
		                previous.next = current.next;
		            }
		            if (current == tailPatron) {
		                tailPatron = previous;
		            }
		            statLibrary.removePatron(libraryCardNumber);
		            System.out.println("Patron with library card number " + libraryCardNumber + " has been removed.");
		            return;
		        }
		        previous = current;
		        current = current.next;
		}
		    System.out.println("Patron not found with library card number: " + libraryCardNumber);
		}
		public void viewAllPatronOption()
		{
			PatronNode current = headPatron;
	        if (current == null) {
	            System.out.println("No patrons available.");
	            return;
	        }
	        while (current != null) {
	            current.patron.displayPatronInfo();
	            current = current.next;
	        }

		}
		public Patron findPatron(int libraryCardNumber) {
		    PatronNode current = headPatron;
		    while (current != null) {
		        if (current.patron.getLibraryCardNumber() == libraryCardNumber) {
		            return current.patron;
		        }
		        current = current.next;
		    }
		    System.out.println("Patron not found with library card number: " + libraryCardNumber); // Debugging output
		    return null; // Patron not found
		}		
		
		public void viewPatronCheckoutBooks() {
		    try {
		        System.out.print("Enter patron library card number to view checked-out books: ");
		        int findLibraryCardNumber = scan.nextInt();
		        scan.nextLine(); // Consume newline
		        
		        Patron patron = findPatron(findLibraryCardNumber);
		        
		        if (patron != null) {
		            System.out.println("Checked-out books for " + patron.getName() + ": ");
		            
		            // Get the checked-out books list
		            LinkedList<Books> checkedOutBooks = patron.getCheckedOutBooks();
		            
		            if (checkedOutBooks.isEmpty()) {
		                System.out.println("No books checked out.");
		            } else {
		                for (Books book : checkedOutBooks) {
		                    System.out.println(book.getTitle());
		                }
		            }
		        } else {
		            System.err.println("Error: Patron not found.");
		        }
		    } catch (Exception e) {
		        System.err.println("Error: Invalid input. Please enter a valid library card number.");
		        scan.nextLine();  // Clear invalid input
		    }
		}

	}

