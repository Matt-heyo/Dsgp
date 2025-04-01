package BookManagement;

public class Node {
	Books book;
	Node nextNode;
	
	public Node(Books book)
	{
		this.book=book;
		this.nextNode=null;
	}
	
}
