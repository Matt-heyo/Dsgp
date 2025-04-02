package com.dsgp.library;
public class BSTNode {
	Books book;
    BSTNode left, right;

    public BSTNode(Books book) {
        this.book = book;
        this.left = this.right = null;
    }
}
