package application;

// This class for save data of martyr and the next node
public class Node {

	// Attributes of node
	private Martyr martyr;
	private Node next;

	// Constructor to make objects of node with martyr data
	public Node(Martyr martyr) {
		this.martyr = martyr;
	}

	// This method to get martyr
	public Martyr getMartyr() {
		return martyr;
	}

	// This method to get next node
	public Node getNext() {
		return next;
	}

	// This method to set new martyr
	public void setMartyr(Martyr martyr) {
		this.martyr = martyr;
	}

	// This method to set new next node
	public void setNext(Node next) {
		this.next = next;
	}

}
