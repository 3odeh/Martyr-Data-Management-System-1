package application;

//This class for save data with their repeated count and the next node
public class CountNode<T> {

	// Attributes of count node
	private T data;
	private int count;
	private CountNode<T> next;

	// Constructor to make objects of node with (T) data
	public CountNode(T data) {
		super();
		this.data = data;
	}

	// This method to get data
	public T getData() {
		return data;
	}

	// This method to get repeated count
	public int getCount() {
		return count;
	}

	// This method to increment the count
	public void incremantCount() {
		count++;
	}

	// This method to get next count node
	public CountNode<T> getNext() {
		return next;
	}
	// This method to set new next count node
	public void setNext(CountNode<T> next) {
		this.next = next;
	}

}
