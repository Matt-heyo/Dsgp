package com.dsgp.library;

import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StatLibrary statLibrary = new StatLibrary(); 
		LibraryBST libraryBST = new LibraryBST();
		Management managebooks = new Management(statLibrary, libraryBST);
		Library library = new Library();

        
		while(true) {
			System.out.println("\nWelcome to the Library Management System\n");
			System.out.println("*******Library Management System Menu******\n");
			
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Change Password");
			System.out.println("4. Exit\n");
			System.out.print("Choose an option: \n");
			
			try {
				int choice = scan.nextInt();
				scan.nextLine();

				switch (choice) {
					case 1:
						registerUser(scan);
						break;
					case 2:
						if (loginUser(scan)) {
							mainMenu(managebooks, scan, statLibrary, libraryBST);
						}
						break;
					case 3:
						changePassword(scan);
						break;
					case 4:
						System.out.println("Exiting system...");
						scan.close();
						return;
					default:
						System.out.println("Invalid choice. Try again.");
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please try again.");
				scan.nextLine(); // Clear buffer
			}
		}
	}

	
/*public static void generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        String password = random.ints(length, 33, 126)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        System.out.println("Generated Password: " + password);
        
    }*/
	
	private static void registerUser(Scanner scan) {
		try {
			System.out.print("Enter username: ");
			String username = scan.nextLine().trim();
			if (username.isEmpty()) {
				System.out.println("Username cannot be empty.");
				return;
			}
			
			generateRandomPassword(8); 
			System.out.print("Enter password: ");
			String password = scan.nextLine().trim();
			if (password.isEmpty()) {
				System.out.println("Password cannot be empty.");
				return;
			}

			User newUser = new User(username, FileManager.hashPassword(password), "user");
			List<User> users = FileManager.loadPasswordsFromFile();
			users.add(newUser);
			FileManager.savePasswordsToFile(users);
			System.out.println("User registered successfully.");
		} catch (Exception e) {
			System.out.println("Error during registration: " + e.getMessage());
		}
	}

	private static boolean loginUser(Scanner scan) {
		try {
			System.out.print("Enter username: ");
			String username = scan.nextLine().trim();
			System.out.print("Enter password: ");
			String password = scan.nextLine().trim();

			List<User> users = FileManager.loadPasswordsFromFile();
			for (User user : users) {
				if (user.getUsername().equals(username) && 
					user.getPassword().equals(FileManager.hashPassword(password))) {
					System.out.println("Login successful. Welcome, " + username);
					return true;
				}
			}
			System.out.println("Invalid username or password.");
		} catch (Exception e) {
			System.out.println("Error during login: " + e.getMessage());
		}
		return false;
	}

	private static void changePassword(Scanner scan) {
		try {
			System.out.print("Enter username: ");
			String username = scan.nextLine().trim();
			System.out.print("Enter old password: ");
			String oldPassword = scan.nextLine().trim();
			System.out.print("Enter new password: ");
			String newPassword = scan.nextLine().trim();

			List<User> users = FileManager.loadPasswordsFromFile();
			boolean found = false;
			for (User user : users) {
				if (user.getUsername().equals(username) && 
					user.getPassword().equals(FileManager.hashPassword(oldPassword))) {
					user.setPassword(FileManager.hashPassword(newPassword));
					found = true;
					break;
				}
			}
			
			if (found) {
				FileManager.savePasswordsToFile(users);
				System.out.println("Password changed successfully.");
			} else {
				System.out.println("Invalid username or password.");
			}
		} catch (Exception e) {
			System.out.println("Error changing password: " + e.getMessage());
		}
	}

	private static void mainMenu(Management managebooks, Scanner scan, StatLibrary statLibrary, LibraryBST libraryBST) {
		Library library = new Library();
		while (true) {
			try {
				System.out.println("\n*******Library Management System Menu******\n");
				System.out.println("1. Book Management");
				System.out.println("2. Patron Management");
				System.out.println("3. Books Check in & Out");
				System.out.println("4. Search for Book");
				System.out.println("5. Book Statistics"); 
				System.out.println("6. Exit");
				System.out.print("Choose an option: \n");
				
				int option = scan.nextInt();
				scan.nextLine();
				
				switch (option) {
					case 1:
						handleBookManagement(managebooks, scan);
						break;
					case 2:
						handlePatronManagement(managebooks, scan);
						break;
					case 3:
						handleCheckInOut(managebooks, scan, libraryBST, library);
						break;
					case 4:
						managebooks.searchForBook();
						break;
					case 5:
						handleStatistics(statLibrary, scan);
						break;
					case 6:
						return;
						
					default:
						System.out.println("Invalid choice. Please try again.");
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please try again.");
				scan.nextLine(); // Clear buffer
			}
		}
	}

	private static void handleBookManagement(Management managebooks, Scanner scan) {
		System.out.println("\n*******Book Management******");
		System.out.println("1. Add a new book");
		System.out.println("2. Remove a book");
		System.out.println("3. Search for a book");
		System.out.println("4. Display all books");
		System.out.println("5. Exit");
		System.out.print("Choose an option: ");
		
		int choice = scan.nextInt();
		scan.nextLine();
		
		managebooks.handleBooks(choice);
	}

	private static void handlePatronManagement(Management managebooks, Scanner scan) {
		System.out.println("\n******* Patron Management *******");
		System.out.println("1. Add a new patron");
		System.out.println("2. Remove a patron");
		System.out.println("3. View all patrons");
		System.out.println("4. View checked-out books for a patron");
		System.out.println("5. Back to Main Menu");
		System.out.print("Choose an option: ");

		int choice1 = scan.nextInt();
		scan.nextLine();
		managebooks.HandlePatron(choice1);
	}

	private static void handleCheckInOut(Management managebooks, Scanner scan, LibraryBST libraryBST, Library library) {
		System.out.println("\n******* Books Check in & Out *******");
		System.out.println("1. Check Out a Book");
		System.out.println("2. Check In a Book");
		System.out.println("3. Undo Last Check Out");
		System.out.println("4. Back to Main Menu");
		System.out.print("Choose an option: ");

		int checkInOutChoice = scan.nextInt();
		scan.nextLine();

		switch (checkInOutChoice) {
			case 1:
				System.out.print("Enter your library card number: ");
				String libraryCardNumber = scan.nextLine();
				if (libraryCardNumber.isEmpty()) {
					System.out.println("Library card number cannot be empty.");
					return;
				}
				System.out.print("Enter the title of the book to check out: ");
				String checkOutTitle = scan.nextLine().toLowerCase();
				managebooks.checkoutBook(libraryCardNumber, checkOutTitle);
				break;

			case 2:
				System.out.print("Enter your library card number: ");
				libraryCardNumber = scan.nextLine();
				if (libraryCardNumber.isEmpty()) {
					System.out.println("Library card number cannot be empty.");
					return;
				}
				System.out.print("Enter the title of the book to check in: ");
				String checkInTitle = scan.nextLine().toLowerCase();
				managebooks.checkInBook(libraryCardNumber, checkInTitle);
				break;
			
			case 3: 
				library.undoLastCheckOut(); 
				break;
			
			case 4:
				System.out.println("Returning to Main Menu...");
				break;
			
			default:
				System.out.println("Invalid choice. Please try again.");
		}
	}

	private static void handleStatistics(StatLibrary statLibrary, Scanner scan) {
		System.out.println("1. Display Library Statistics");
		System.out.println("2. Display All Books");
		System.out.println("3. Display All Patrons");
		System.out.println("4. Exit");
		System.out.print("Choose an option: ");
		
		int choice2 = scan.nextInt();
		scan.nextLine();
		switch (choice2) {
			case 1:
				statLibrary.displayStatistics(); // Display statistics
				break;
			case 2:
				statLibrary.displayAllBooks(); 
				break;
			case 3:
				statLibrary.displayAllPatrons();
				break;
			case 4:
				System.out.println("Returning to Main Menu...");
				break;
			default:
				System.out.println("Invalid option. Please try again.");
		}
	}
}
