package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//This class to save list of location data
public class MyDoubleLinkedList {

	// Pointer to first double node.
	private DoubleNode first;
	// Pointer to last double node.
	private DoubleNode last;
	// size of list
	private int count;

	public DoubleNode getFirstDoubleNode() {
		return first;
	}

	// This method to add location in the first place in this list.
	public void addFirst(String location) {
		if(location == null|| location.isEmpty())
			return;
		location = location.trim();
		if (count == 0) {
			first = last = new DoubleNode(location);
		} else {
			DoubleNode tmp = new DoubleNode(location);
			tmp.setNext(first);
			first.setPre(tmp);
			first = tmp;
		}
		count++;
	}

	// This method to return if their is no location
	public boolean isEmpty() {
		return count == 0;
	}

	// This method to add location in the last place in this list.
	public void addLast(String location) {
		if(location == null|| location.isEmpty())
			return;
		location = location.trim();
		if (count == 0) {
			first = last = new DoubleNode(location);
		} else {
			DoubleNode tmp = new DoubleNode(location);
			last.setNext(tmp);
			tmp.setPre(last);
			last = tmp;
		}
		count++;
	}

	/*
	 * This method add the location in the place dependent on location name in this
	 * list and martyr in the place dependent on date of death in link list of the
	 * same location.
	 */
	public void add(Martyr martyr, String location) {
		if(location == null || location.isEmpty() || martyr == null)
			return;
		location = location.trim();

		if (isEmpty()) {
			addFirst(location);
			first.getStart().add(martyr);
			return;
		} else {
			int compare = location.compareTo(first.getLocation());
			if (compare < 0) {

				addFirst(location);
				first.getStart().add(martyr);
				return;
			} else if (compare == 0) {
				first.getStart().add(martyr);
				return;
			}

			compare = location.compareTo(last.getLocation());
			if (compare > 0) {
				addLast(location);
				last.getStart().add(martyr);
				return;
			} else if (compare == 0) {
				last.getStart().add(martyr);
				return;
			}

			DoubleNode current = first;
			while (current != null) {
				compare = location.compareTo(current.getLocation());
				if (compare > 0 && location.compareTo(current.getNext().getLocation()) < 0) {
					DoubleNode node = new DoubleNode(location);
					node.getStart().add(martyr);
					node.setNext(current.getNext());
					node.setPre(current);
					current.getNext().setPre(node);
					current.setNext(node);
					count++;
					return;
				} else if (compare == 0) {
					current.getStart().add(martyr);
					return;
				}
				current = current.getNext();
			}
		}
	}

	// This method to add location in the place dependent on location in this list.
	public boolean add(String location) {
		if(location == null|| location.isEmpty())
			return false;
		location = location.trim();
		
		if (count == 0) {
			addFirst(location);
			return true;
		} else {
			int compare = location.compareTo(first.getLocation());
			if (compare < 0) {

				addFirst(location);
				return true;
			} else if (compare == 0) {

				return false;
			}

			compare = location.compareTo(last.getLocation());
			if (compare > 0) {
				addLast(location);
				return true;
			} else if (compare == 0) {
				return false;
			}

			DoubleNode current = first;
			while (current != null) {
				compare = location.compareTo(current.getLocation());
				if (compare > 0 && location.compareTo(current.getNext().getLocation()) < 0) {
					DoubleNode node = new DoubleNode(location);

					node.setNext(current.getNext());
					node.setPre(current);
					current.getNext().setPre(node);
					current.setNext(node);
					count++;
					return true;
				} else if (compare == 0) {
					return false;
				}
				current = current.getNext();
			}
		}

		return false;
	}

	/*
	 * This method to read martyr data from input file with format
	 * (name,age,location,date(month/day/year),gender(M,F)) then add the location in
	 * the place dependent on location name in this list and martyr in the place
	 * dependent on date of death in link list of the same location.
	 */
	public boolean read(File f) throws Exception {
		try {
			Scanner scanner = new Scanner(f);
			SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
			while (scanner.hasNext()) {
				String[] line = scanner.nextLine().split(",");
				if (line.length < 4) {
					continue;
				}
				Martyr m;
				try {
					
					m = new Martyr(line[0], Byte.valueOf(line[1]), dateFor.parse(line[3]), line[4].equals("M"));
					add(m, line[2]);
					
				} catch (NumberFormatException e) {

					try {
						m = new Martyr(line[0], Byte.valueOf(line[1]), new Date(), line[4].equals("M"));
						add(m, line[2]);
					} catch (Exception exception) {
						m = new Martyr(line[0], (byte) -1, new Date(), line[4].equals("M"));
						add(m, line[2]);
					}
				} catch (ParseException e) {
					try {
						m = new Martyr(line[0], (byte) -1, dateFor.parse(line[3]), line[4].equals("M"));
						add(m, line[2]);
					} catch (Exception exception) {
						m = new Martyr(line[0], (byte) -1, new Date(), line[4].equals("M"));
						add(m, line[2]);
					}
				} catch (Exception e) {
				}

			}
			scanner.close();
		} catch (Exception e) {
			throw e;

		}
		return true;
	}

	// This method to add all location data in the grid pane
	public void addLocationToGP(GridPane gp, Stage stage) {

		DoubleNode current = first;
		gp.add(GeneralPanes.locationPane(null, stage, this), 1, 0);
		for (int x = 2; x < count + 2; x++) {
			gp.add(GeneralPanes.locationPane(current, stage, this), x % 5, x / 5);
			current = current.getNext();
		}

	}

	// This method to add search location data in the grid pane
	public void addLocationToGP(GridPane gp, String search, Stage stage) {
		DoubleNode current = first;
		for (int x = 2, pos = 2; x < count + 2; x++) {
			if (current.getLocation().startsWith(search)) {
				gp.add(GeneralPanes.locationPane(current, stage, this), pos % 5, pos / 5);
				pos++;
			}
			current = current.getNext();
		}
	}

	// This method to print all location and martyr data to the input file
	public boolean printListToFile(File f) throws Exception {
		try {
			PrintWriter pw = new PrintWriter(f);
			DoubleNode current = first;
			while (current != null) {
				current.getStart().printListToFile(pw);
				current = current.getNext();
			}
			pw.close();
			return true;
		} catch (FileNotFoundException e) {
			throw e;
		}

	}

	// This method to remove the input node
	public boolean removeDoubleNode(DoubleNode current) {
		if (current == null) {
			return false;
		}
		if (current.getPre() == null) {
			return removeFirst();
		} else if (current.getNext() == null) {
			return removeLast();
		} else {
			DoubleNode pre = current.getPre();
			DoubleNode next = current.getNext();
			pre.setNext(next);
			next.setPre(pre);
			count--;
			return true;
		}
	}

	// This method to remove the searched node by location and martyr
	public boolean removeNode(String location, Martyr martyr) {
		DoubleNode current = first;
		while (current != null) {
			if (current.getLocation().equals(location)) {
				return current.getStart().remove(martyr);
			}
			current = current.getNext();
		}
		return false;
	}

	// This method to get search node by location
	public DoubleNode getDoubleNode(String location) {
		DoubleNode current = first;
		while (current != null) {
			if (current.getLocation().equals(location))
				return current;
			current = current.getNext();
		}
		return current;
	}

	// This method to remove first location in this list.
	public boolean removeFirst() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				last = first = null;
			} else {
				first = first.getNext();
				first.setPre(null);
			}
			count--;
			return true;
		}

	}

	// This method to remove last location in this list.
	public boolean removeLast() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				last = first = null;
			} else {
				last = last.getPre();
				last.setNext(null);

			}
			count--;
			return true;
		}
	}

	// This method to update location name and place of node in this list.
	public boolean changeName(String text, DoubleNode node) {
		if (text == null || text.isEmpty() || node == null)
			return false;
		text = text.trim();
		int compare = text.compareTo(node.getLocation());

		if (compare > 0) {
			compare = text.compareTo(last.getLocation());
			if (compare > 0) {
				if (node.getNext() == null) {
					// no need to change the place of node
					node.setLocation(text);
					return true;
				} else if (node.getPre() == null) {
					DoubleNode current = node;

					// put the first node to the second node
					first = node.getNext();
					first.setPre(null);

					// put node in the last
					last.setNext(current);
					current.setNext(null);
					current.setPre(last);
					last = current;
					node.setLocation(text);
					return true;
				} else {
					DoubleNode current = node;

					// connect the pre node to the next node
					node.getPre().setNext(node.getNext());
					node.getNext().setPre(node.getPre());

					// put node in the last
					last.setNext(current);
					current.setNext(null);
					current.setPre(last);
					last = current;

					node.setLocation(text);
					return true;
				}

			} else if (compare == 0) {
				return false;
			}

			if (text.compareTo(node.getLocation()) > 0 && text.compareTo(node.getNext().getLocation()) < 0) {
				node.setLocation(text);
				return true;
			}
			DoubleNode current = node;
			while (current != null) {
				compare = text.compareTo(current.getLocation());
				if (compare > 0 && text.compareTo(current.getNext().getLocation()) < 0) {

					DoubleNode tmp = node;
					// connect the pre node to the next node
					if (node.getPre() != null) {
						node.getPre().setNext(node.getNext());
					}

					if (tmp.getNext() != null) {
						tmp.getNext().setPre(tmp.getPre());
					}

					tmp.setNext(current.getNext());
					tmp.setPre(current);
					current.getNext().setPre(tmp);
					current.setNext(tmp);

					tmp.setLocation(text);
					return true;
				} else if (compare == 0) {
					return false;
				}

				current = current.getNext();

			}

		} else if (compare < 0) {
			compare = text.compareTo(first.getLocation());
			if (compare < 0) {
				if (node.getPre() == null) {
					// no need to change the place of node
					node.setLocation(text);
					return true;
				} else if (node.getNext() == null) {
					DoubleNode current = node;

					// put the Last node to the pre last node
					last = last.getPre();
					last.setNext(null);

					// put node in the first
					first.setPre(current);
					current.setPre(null);
					current.setNext(first);
					first = current;
					node.setLocation(text);
					return true;
				} else {
					DoubleNode current = node;

					// connect the pre node to the next node
					node.getPre().setNext(node.getNext());
					node.getNext().setPre(node.getPre());

					// put node in the first
					first.setPre(current);
					current.setPre(null);
					current.setNext(first);
					first = current;
					node.setLocation(text);

					return true;
				}

			} else if (compare == 0) {
				return false;
			}

			if (text.compareTo(node.getLocation()) < 0 && text.compareTo(node.getPre().getLocation()) > 0) {
				node.setLocation(text);
				return true;
			}
			DoubleNode current = node;
			while (current != null) {
				compare = text.compareTo(current.getLocation());
				if (compare < 0 && text.compareTo(current.getPre().getLocation()) > 0) {

					DoubleNode tmp = node;
					// connect the pre node to the next node
					if (node.getPre() != null) {
						node.getPre().setNext(node.getNext());
					}

					if (node.getNext() != null) {
						node.getNext().setPre(node.getPre());
					}

					tmp.setPre(current.getPre());
					tmp.setNext(current);
					current.getPre().setNext(tmp);
					current.setPre(tmp);

					node.setLocation(text);
					return true;
				} else if (compare == 0) {
					return false;
				}

				current = current.getPre();

			}
		}

		return false;

	}

	// This method return the number of all martyr
	public int getDataCount() {
		int count = 0;
		DoubleNode current = first;
		current = first;
		while (current != null) {
			count += current.getStart().count;
			current = current.getNext();
		}
		return count;
	}

	// This method to add all martyr in all location in this list to the table view.
	public void addAllDataToTableView(TableView<AllData> tv) {
		DoubleNode current = first;
		current = first;
		while (current != null) {
			current.getStart().addAllDataToTableView(tv, current.getLocation());
			current = current.getNext();
		}

	}
	
	// This method to add martyr searched with name in all location in this list to the table view.
	public void addAllDataToTableView(TableView<AllData> tv, String search) {
		DoubleNode current = first;
		current = first;
		while (current != null) {
			current.getStart().addAllDataToTableView(tv, current.getLocation(), search);
			current = current.getNext();
		}

	}
}
