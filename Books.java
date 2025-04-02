package com.dsgp.library;


public class Books {
private String title;
private String author;
private int pubYear;
private int ISBN;
private boolean isAvailable;
private int amount;

public Books() 
{
	title="The Great Gatsby";
	author="F. Scott Fitzgerald";
	ISBN=074324;
	pubYear=1925;
	isAvailable=true;
	amount=0;// to check the amount of books added
}

public Books(String title, String author, int ISBN,int pubYear, int amount) {
    this.title = title;
    this.author = author;
    this.ISBN = ISBN;
    this.amount = amount;
    this.pubYear = pubYear; 
    this.isAvailable = true;  
    }
public Books(Books bk)
{
	this.author=bk.author;
	this.ISBN=bk.ISBN;
	this.title=bk.title;
	this.amount=bk.amount;
	this.isAvailable=bk.isAvailable;
	this.pubYear=bk.pubYear;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}


public int getISBN() {
	return ISBN;
}
public void setISBN(int iSBN) {
	ISBN = iSBN;
}
public int getPubYear() {
	return pubYear;
}
public void setPubYear(int pubYear) {
	this.pubYear = pubYear;
}
public boolean isAvailable() {
	return isAvailable;
}
public void setAvailable(boolean isAvailable) {
	this.isAvailable = isAvailable;
}

public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}


public void Display() {
	System.out.println("The title of the book is:   " + title);
    System.out.println("The author of the book is:  " + author);
    System.out.println("The ISBN is:                " + ISBN);
    System.out.println("the year of publication is: "+pubYear);
    System.out.println("Available??:                " + isAvailable);
    System.out.println("Number of books are:        " + amount);
    System.out.println("----------------------------\n");
    }
}
