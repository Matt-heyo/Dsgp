package com.dsgp.library;
public class LibraryBST {
	private BSTNode root;

    public LibraryBST() {
        root = null;
    }

    // Insert a book into BST
    public void insert(Books book) {
        root = insertRec(root, book);
    }

    private BSTNode insertRec(BSTNode root, Books book) {
        if (root == null) {
            return new BSTNode(new Books(book)); // Copy constructor used
        }
        if (book.getTitle().compareTo(root.book.getTitle()) < 0)
            root.left = insertRec(root.left, book);
        else
            root.right = insertRec(root.right, book);
        return root;
    }

    // Search by title
    public Books searchByTitle(String title) {
        Books foundBook = searchByTitleRec(root, title.toLowerCase());
        if (foundBook != null) {
            System.out.println("Book Found: " + foundBook.getTitle());
            foundBook.Display(); 
        } else {
            System.out.println("Book not found with title: " + title);
        }
        return foundBook;
    }

    private Books searchByTitleRec(BSTNode root, String title) {
        if (root == null) {
            return null; 
        }
        if (root.book.getTitle().trim().equalsIgnoreCase(title.trim())) { // Ignore case and spaces
            return root.book;
        }
        if (title.compareToIgnoreCase(root.book.getTitle()) < 0) {
            return searchByTitleRec(root.left, title);
        } else {
            return searchByTitleRec(root.right, title);
        }
    }

    // Search by author
    public Books searchByAuthor(String author) {
        Books foundBook = searchByAuthorRec(root, author.toLowerCase());
        if (foundBook != null) {
            System.out.println("Book Found: " + foundBook.getTitle()); 
            foundBook.Display(); 
        } else {
            System.out.println("Book not found with author: " + author);
        }
        return foundBook;
    }

    private Books searchByAuthorRec(BSTNode root, String author) {
        if (root == null) return null;
        if (root.book.getAuthor().equalsIgnoreCase(author)) return root.book;

        Books leftSearch = searchByAuthorRec(root.left, author);
        if (leftSearch != null) return leftSearch;

        return searchByAuthorRec(root.right, author);
    }

    // Search by ISBN
    public Books searchByISBN(int ISBN) {
        Books foundBook = searchByISBNRec(root, ISBN);
        if (foundBook != null) {
            System.out.println("Book Found: " + foundBook.getTitle()); 
            foundBook.Display(); 
        } else {
            System.out.println("Book not found with ISBN: " + ISBN);
        }
        return foundBook;
    }
    
    private Books searchByISBNRec(BSTNode root, int ISBN) {
        if (root == null) return null;
        if (root.book.getISBN() == ISBN) return root.book;

        Books leftSearch = searchByISBNRec(root.left, ISBN);
        if (leftSearch != null) return leftSearch;

        return searchByISBNRec(root.right, ISBN);
    }
}
