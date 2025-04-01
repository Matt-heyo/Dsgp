package BookManagement;

public class PatronNode {
	Patron patron;
    PatronNode next;

    // Constructor for PatronNode
    public PatronNode(Patron patron) {
        this.patron = patron;
        this.next = null;
    }


}
