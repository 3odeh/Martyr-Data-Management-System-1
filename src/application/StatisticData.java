package application;

import java.util.Date;

import javafx.scene.control.TableView;

public class StatisticData {

	private static int martyrCount = 0;
	public static boolean isUpdatedData = false;

	private DoubleNode current;
	private int numberOfMale;
	private int numberOfFemale;
	private float averageOfAge;
	private float thePercentage;
	private CountLinkedList<Byte> ageCount;
	private CountLinkedList<Date> dateCount;

	public StatisticData(DoubleNode current) {
		super();
		this.current = current;
		ageCount = new CountLinkedList<>();
		dateCount = new CountLinkedList<>();
		Node c = current.getStart().getFirstNode();

		while (c != null) {

			Martyr martyr = c.getMartyr();
			if (martyr.isMale()) {
				numberOfMale++;
			} else
				numberOfFemale++;

			if (martyr.getAge() > 0)
				averageOfAge += martyr.getAge();
			ageCount.add(martyr.getAge());
			dateCount.add(martyr.getDateOfDeath());

			c = c.getNext();
		}

		averageOfAge = averageOfAge / (numberOfFemale + numberOfMale);
		thePercentage = 100 * (((float) numberOfFemale + numberOfMale) / getMartrCount());

	}

	public StatisticData(MyDoubleLinkedList list) {
		this.current = list.getFirstDoubleNode();
		ageCount = new CountLinkedList<>();
		dateCount = new CountLinkedList<>();
		getMartrCount();
		if (!list.isEmpty()) {
			DoubleNode doubleNode = current;
			while (doubleNode != null) {
				Node node = doubleNode.getStart().getFirstNode();

				while (node != null) {

					Martyr martyr = node.getMartyr();
					if (martyr.isMale()) {
						numberOfMale++;
					} else
						numberOfFemale++;
					if (martyr.getAge() > 0)
						averageOfAge += martyr.getAge();
					ageCount.add(martyr.getAge());
					dateCount.add(martyr.getDateOfDeath());

					node = node.getNext();
				}
				doubleNode = doubleNode.getNext();
			}
			averageOfAge = averageOfAge / (numberOfFemale + numberOfMale);
			thePercentage = 100;
		}

	}

	public int getNumberOfMale() {
		return numberOfMale;
	}

	public int getNumberOfFemale() {
		return numberOfFemale;
	}

	public float getAverageOfAge() {
		return averageOfAge;
	}

	public float getThePercentage() {
		return thePercentage;
	}

	public byte getMaxAge() {
		return ageCount.getMax().getData();
	}

	public Date getMaxDate() {
		return dateCount.getMax().getData();
	}

	public void setAgeData(TableView<CountNode<Byte>> tv) {
		ageCount.setAgeData(tv);
	}

	public int getMartrCount() {
		if (isUpdatedData) {
			martyrCount = 0;
			DoubleNode doubleNode = current;
			while (doubleNode != null) {
				martyrCount += doubleNode.getStart().count;
				doubleNode = doubleNode.getNext();
			}
			doubleNode = current.getPre();
			while (doubleNode != null) {
				martyrCount += doubleNode.getStart().count;
				doubleNode = doubleNode.getPre();
			}
			isUpdatedData = false;
		}
		return martyrCount;
	}

	public static int getAllMartrCount() {

		return martyrCount;
	}

}
