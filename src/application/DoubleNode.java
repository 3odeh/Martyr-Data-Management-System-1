package application;

//This class for save data of location with linked list of martyr and the next node
public class DoubleNode {

	// Attributes of double node
	private String location;
	private MyLinkedList martyr;
	private DoubleNode pre, next;

	// Constructor to make objects of double node with location data and initialize
	// the linked list
	public DoubleNode(String location) {
		super();
		this.location = location;
		martyr = new MyLinkedList();
	}

	// This method to get location
	public String getLocation() {
		return location;
	}

	// This method to get linked list
	public MyLinkedList getStart() {
		return martyr;
	}

	// This method to get previous double node
	public DoubleNode getPre() {
		return pre;
	}

	// This method to get next double node
	public DoubleNode getNext() {
		return next;
	}

	// This method to set update location
	public void setLocation(String location) {
		this.location = location;
	}

	// This method to set set new linked list
	public void setStart(MyLinkedList martyr) {
		this.martyr = martyr;
	}

	// This method to set new previous double node
	public void setPre(DoubleNode pre) {
		this.pre = pre;
	}

	// This method to set new next double node
	public void setNext(DoubleNode next) {
		this.next = next;
	}

}
