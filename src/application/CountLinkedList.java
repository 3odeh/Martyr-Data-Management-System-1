package application;

import javafx.scene.control.TableView;

// This class to save the (T) data with repeated count and calculate the maximum value that repeated (I use it to save the age and date)
public class CountLinkedList<T> {

	
	// Pointer to first count node.
	private CountNode<T> first;
	
	// Pointer to max count node.
	private CountNode<T> max;
	
	// size of list
	private int count;

	// This method to add data to the linked list and increment the repeated count
	public void add(T t) {
		if (count == 0) {
			max = first = new CountNode<>(t);
			first.incremantCount();
			count++;
		} else {
			CountNode<T> current = first;
			while (current != null) {
				if (current.getData().equals(t)) {
					current.incremantCount();
					if (max.getCount() < current.getCount())
						max = current;
					return;
				}
				current = current.getNext();
			}
			addFirst(t);
		}
	}

	// This method add data to the first place in linked list and increment the
	// repeated count
	private void addFirst(T t) {
		if (count == 0) {
			max = first = new CountNode<>(t);
		} else {
			CountNode<T> tmp = new CountNode<>(t);
			tmp.setNext(first);
			first = tmp;
		}
		first.incremantCount();
		count++;

	}

	// This method to print the all data with repeated count
	public void printList() {
		CountNode<T> current = first;
		int total = 0;
		while (current != null) {
			total += current.getCount();
			System.out.println(current.getData() + ":" + current.getCount());
			current = current.getNext();
		}
		System.out.println("Total:" + total);
	}

	// This method to add the all data with repeated count to the table view
	public void setAgeData(TableView<CountNode<Byte>> tv) {
		CountNode<T> current = first;
		while (current != null) {
			tv.getItems().add((CountNode<Byte>) current);
			current = current.getNext();
		}
	}

	// This method to get max value that repeated
	public CountNode<T> getMax() {
		return max;
	}

	// This method to set new max value that repeated
	public void setMax(CountNode<T> max) {
		this.max = max;
	}
}
