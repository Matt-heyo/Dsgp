package BookManagement;

public class checkingNode {

    private Books data;
    private checkingNode nextNode;

    public checkingNode() {
        this.data = new Books();
        this.nextNode = null;
    }

    public checkingNode(Books book) {
        this.data = book;
        this.nextNode = null;
    }

    public Books getData() { return data; }
    public checkingNode getNextNode() { return nextNode; }
    public void setNextNode(checkingNode nextNode) { this.nextNode = nextNode; }
}


