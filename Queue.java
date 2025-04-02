package com.dsgp.library;
public class Queue {
	private checkingNode front, rear;

    public Queue() {
        front = null;
        rear = null;
    }

    public void enqueue(Books book) {
        checkingNode temp = new checkingNode(book);
        if (rear == null) {
            front = temp;
            rear = temp;
        } else {
            rear.setNextNode(temp);
            rear = temp;
        }
    }

    public Books dequeue() {
        if (front == null) {
            System.out.println("Queue is empty, no book to check out.");
            return null;
        }
        Books book = front.getData();
        front = front.getNextNode();
        if (front == null) { rear = null; }
        return book;
    }

    public boolean isEmpty() {
        return front == null;
    }
}
