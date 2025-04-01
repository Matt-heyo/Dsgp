package BookManagement;

public class Stack {
	private checkingNode top;

    public Stack() {
        this.top = null;
    }

    public void push(Books book) {
        checkingNode temp = new checkingNode(book);
        if (top == null) {
            top = temp;
        } else {
            temp.setNextNode(top);
            top = temp;
        }
    }

    public Books pop() {
        if (top == null) {
            System.out.println("Stack is empty, no check-out to undo.");
            return null;
        }
        Books book = top.getData();
        top = top.getNextNode();
        return book;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
