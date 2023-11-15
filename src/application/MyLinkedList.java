package application;

import java.io.PrintWriter;
import java.util.LinkedList;

import javafx.scene.control.TableView;

// This class to save list of martyr data
public class MyLinkedList {

	// Pointer to first node.
	private Node first;
	// Pointer to last node.
	private Node last;
	// size of list
	int count;

	// This method to return if their is no martyr
	public boolean isEmpty() {
		return (count == 0);
	}

	// This method to returns the first martyr in this list
	public Martyr getFirst() {
		if (count != 0)
			return first.getMartyr();

		else
			return null;
	}

	// This method to returns the last martyr in this list.
	public Martyr getLast() {
		if (count != 0)
			return last.getMartyr();

		else
			return null;
	}

	// This method to add martyr in the first place in this list.
	public void addFirst(Martyr o) {

		if (count == 0) {
			first = last = new Node(o);

		} else {
			Node tmp = new Node(o);
			tmp.setNext(first);
			first = tmp;
		}
		count++;
	}

	// This method to add martyr in the last place in this list.
	public void addLast(Martyr o) {
		if (count == 0) {
			first = last = new Node(o);

		} else {
			Node newN = new Node(o);
			last.setNext(newN);
			last = last.getNext();

		}
		count++;
	}

	// This method to add martyr in the index place in this list.
	public void add(Martyr o, int index) {

		if (index == 0) {
			addFirst(o);

		} else if (index >= count) {
			addLast(o);
		} else {

			Node current = first;
			Node node = new Node(o);
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			node.setNext(current.getNext());
			current.setNext(node);
			count++;

		}

	}

	// This method to add martyr in the place dependent on death date of martyr in
	// this list.
	public int add(Martyr o) {
		if (count == 0) {
			addFirst(o);
			return 0;
		} else {
			if (o.getDateOfDeath() == null || o.getDateOfDeath().compareTo(first.getMartyr().getDateOfDeath()) <= 0) {
				addFirst(o);
				return 0;
			}

			if (o.getDateOfDeath().compareTo(last.getMartyr().getDateOfDeath()) >= 0) {

				addLast(o);
				return count - 1;
			}
			Node node = new Node(o);
			Node current = first;
			int place = 1;
			while (current != null) {

				if (o.getDateOfDeath().compareTo(current.getMartyr().getDateOfDeath()) >= 0 && current.getNext() != null
						&& o.getDateOfDeath().compareTo(current.getNext().getMartyr().getDateOfDeath()) <= 0) {
					node.setNext(current.getNext());
					current.setNext(node);

					count++;
					return place;
				}
				place++;
				current = current.getNext();
			}
		}
		return -1;
	}

	// This method to remove first martyr in this list.
	public boolean removeFirst() {

		if (count == 0) {
			return false;

		} else {
			if (count == 1) {
				last = first = null;
			}

			else {
				first = first.getNext();
			}
			count--;
			return true;
		}

	}

	// This method to remove last martyr in this list.
	public boolean removeLast() {

		if (count == 0) {
			return false;

		} else {
			if (count == 1) {
				last = first = null;
			}

			else {
				Node current = first;
				for (int i = 0; i < count - 2; i++)
					current = current.getNext();

				last = current;
				last.setNext(null);

			}
			count--;

			return true;
		}

	}

	// This method to remove index place martyr in this list.
	public boolean remove(int index) {
		if (index < 0 || index > count) {
			return false;

		} else if (index == 0) {
			return removeFirst();

		} else if (index == count) {
			return removeLast();

		} else {
			Node current = first;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();

			current.setNext(current.getNext().getNext());
			count--;
			return true;
		}

	}

	// This method to remove input martyr in this list.
	public boolean remove(Martyr o) {
		if (count == 0) {
			return false;

		} else {
			if (first.getMartyr().equals(o)) {
				return removeFirst();
			}

			else {
				Node previous = first;
				Node current = first.getNext();
				while (current != null && !current.getMartyr().equals(o)) {
					previous = current;
					current = current.getNext();
				}
				if (current != null) {
					previous.setNext(current.getNext());
					count--;
					return true;
				} else
					return false;

			}
		}

	}

	// This method to search martyr in this list.
	public boolean search(Object o) {

		Node current = first;

		for (int i = 0; i < count; i++) {
			if (current.getMartyr().equals(o)) {
				return true;
			}
			current = first.getNext();
		}
		return false;

	}

	// This method to print all martyr in this list.
	public void printList() {

		try {
			Node current = first;
			while (current != null) {
				System.out.println(current.getMartyr());
				current = current.getNext();
			}
		} catch (Exception e) {
		}
	}

	// This method to print all martyr in this list on input file.
	public void printListToFile(PrintWriter pw) throws Exception {
		try {
			Node current = first;
			while (current != null) {
				pw.println(current.getMartyr());
				current = current.getNext();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// This method to add all martyr in this list to the table view.
	public void addDataToTableView(TableView<Martyr> tv) {
		Node current = first;
		while (current != null) {
			tv.getItems().add(current.getMartyr());
			current = current.getNext();
		}
	}

	// This method to add martyr searched with name in this list to the table view.
	public void addDataToTableView(TableView<Martyr> tv, String text) {

		Node current = first;
		while (current != null) {
			if (current.getMartyr().getName().startsWith(text))
				tv.getItems().add(current.getMartyr());
			current = current.getNext();
		}
	}

	// This method to add martyr with location in this list to the table view.
	public void addAllDataToTableView(TableView<AllData> tv, String location) {

		Node current = first;
		while (current != null) {
			tv.getItems().add(new AllData(current.getMartyr(), location));
			current = current.getNext();
		}
	}

	// This method to add location and martyr searched with name in this list to the
	// table view.
	public void addAllDataToTableView(TableView<AllData> tv, String location, String search) {

		Node current = first;
		while (current != null) {
			if (current.getMartyr().getName().startsWith(search))
				tv.getItems().add(new AllData(current.getMartyr(), location));
			current = current.getNext();
		}
	}

	// This method to update the place of node
	public void notifyChangeDate(Martyr martyr) {
		if (first.getMartyr().equals(martyr)) {
			removeFirst();
			add(martyr);
		} else if (last.getMartyr().equals(martyr)) {
			removeLast();
			add(martyr);
		} else {
			remove(martyr);
			add(martyr);
		}
	}

	// This method return the searched node by martyr
	public Node getNode(Martyr martyr) {
		Node current = first;
		while (current != null) {
			if (current.getMartyr().equals(martyr))
				return current;
			current = current.getNext();
		}
		return current;
	}

	// This method return the first node
	public Node getFirstNode() {
		return first;
	}

}