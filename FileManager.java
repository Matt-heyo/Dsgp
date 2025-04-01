package BookManagement;
import java.io.*;
import java.util.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.nio.file.*;

public class FileManager {
	private static final String DATA_DIR = "library_data";
    private static final String BOOKS_FILE = DATA_DIR + "/books.csv";
    private static final String PATRONS_FILE = DATA_DIR + "/patrons.csv";
    private static final String PASSWORDS_FILE = DATA_DIR + "/passwords.csv";
    private static final String CHECKOUT_HISTORY_FILE = DATA_DIR + "/checkout_history.csv";
    
    static {
        try {
            // Create data directory if it doesn't exist
            Files.createDirectories(Paths.get(DATA_DIR));
            // Create backup directory
            Files.createDirectories(Paths.get(DATA_DIR + "/backups"));
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    public static void saveBooksToFile(List<Books> books) {
        createBackup(BOOKS_FILE);
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Books book : books) {
                // Save all book attributes
                pw.println(String.join(",", 
                    book.getTitle(),
                    book.getAuthor(),
                    String.valueOf(book.getISBN()),
                    String.valueOf(book.getPubYear()),
                    String.valueOf(book.getAmount()),
                    String.valueOf(book.isAvailable())
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    public static List<Books> loadBooksFromFile() {
        List<Books> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    if (data.length >= 6) {
                        Books book = new Books(
                            data[0], // title
                            data[1], // author
                            Integer.parseInt(data[2]), // ISBN
                            Integer.parseInt(data[3]), // pubYear
                            Integer.parseInt(data[4])  // amount
                        );
                        book.setAvailable(Boolean.parseBoolean(data[5]));
                        books.add(book);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing book data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("No books file found, starting fresh.");
        }
        return books;
    }

    public static void savePatronsToFile(List<Patron> patrons) {
        createBackup(PATRONS_FILE);
        try (PrintWriter pw = new PrintWriter(new FileWriter(PATRONS_FILE))) {
            for (Patron p : patrons) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getName()).append(",")
                  .append(p.getLibraryCardNumber()).append(",");
                
                // Convert checked out books to string
                LinkedList<Books> checkedOutBooks = p.getCheckedOutBooks();
                if (checkedOutBooks != null && !checkedOutBooks.isEmpty()) {
                    StringJoiner joiner = new StringJoiner(";");
                    for (Books book : checkedOutBooks) {
                        joiner.add(book.getISBN() + ":" + book.getTitle());
                    }
                    sb.append(joiner.toString());
                }
                pw.println(sb.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving patrons: " + e.getMessage());
        }
    }

    public static List<Patron> loadPatronsFromFile() {
        List<Patron> patrons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATRONS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",", 3);
                    if (data.length >= 2) {
                        Patron p = new Patron(data[0], Integer.parseInt(data[1]));
                        if (data.length == 3 && !data[2].isEmpty()) {
                            String[] booksData = data[2].split(";");
                            for (String bookData : booksData) {
                                String[] bookInfo = bookData.split(":");
                                if (bookInfo.length == 2) {
                                    Books book = new Books();
                                    book.setISBN(Integer.parseInt(bookInfo[0]));
                                    book.setTitle(bookInfo[1]);
                                    p.addCheckedOutBook(book);
                                }
                            }
                        }
                        patrons.add(p);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing patron data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("No patrons file found, starting fresh.");
        }
        return patrons;
    }

    public static void savePasswordsToFile(List<User> users) {
        createBackup(PASSWORDS_FILE);
        try (PrintWriter pw = new PrintWriter(new FileWriter(PASSWORDS_FILE))) {
            for (User user : users) {
                pw.println(String.join(",", 
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving passwords: " + e.getMessage());
        }
    }

    public static List<User> loadPasswordsFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PASSWORDS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    users.add(new User(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No password file found, creating default admin.");
            users.add(new User("admin", hashPassword("admin"), "admin"));
            savePasswordsToFile(users);
        }
        return users;
    }

    private static void createBackup(String filePath) {
        try {
            Path source = Paths.get(filePath);
            if (Files.exists(source)) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String backupFile = DATA_DIR + "/backups/" + 
                                  Paths.get(filePath).getFileName() + "." + 
                                  timestamp + ".bak";
                Files.copy(source, Paths.get(backupFile), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            System.err.println("Error hashing password: " + e.getMessage());
            return password; // fallback
        }
    }

    // Method to save checkout history
    public static void saveCheckoutHistory(String patronId, String isbn, String action, Date date) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CHECKOUT_HISTORY_FILE, true))) {
            pw.println(String.join(",",
                patronId,
                isbn,
                action,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
            ));
        } catch (IOException e) {
            System.err.println("Error saving checkout history: " + e.getMessage());
        }
    }
}


