package BookManagement;

public class PatronLinkedList {
	private PatronNode head;
    private PatronNode tail;

    public PatronLinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Add a new patron to the linked list
    public void addPatron(Patron patron) {
        PatronNode newNode = new PatronNode(patron);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Remove a patron from the linked list by their library card number
    public void removePatron(int libraryCardNumber) {
        PatronNode current = head;
        PatronNode previous = null;

        while (current != null) {
            if (current.patron.getLibraryCardNumber() == libraryCardNumber) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                if (current == tail) {
                    tail = previous;
                }
                System.out.println("Patron removed: " + current.patron.getName());
                return;
            }
            previous = current;
            current = current.next;
        }

        System.out.println("Patron not found.");
    }

    // View all patrons in the linked list
    public void viewAllPatrons() {
        PatronNode current = head;
        if (current == null) {
            System.out.println("No patrons available.");
            return;
        }
        while (current != null) {
            current.patron.displayPatronInfo();
            current = current.next;
        }
    }
 // Find a patron by their library card number
    public Patron findPatron(int libraryCardNumber) {
        PatronNode current = head;
        while (current != null) {
            if (current.patron.getLibraryCardNumber() == libraryCardNumber) {
                return current.patron;
            }
            current = current.next;
        }
        return null; // Patron not found
    }

}
