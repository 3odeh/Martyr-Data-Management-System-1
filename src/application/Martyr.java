package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// This class for save the data of Martyr
public class Martyr {

	// Attributes of Martyr
	private String name;
	private byte age;
	private Date dateOfDeath;
	private boolean isMale;

	// Constructor to make objects of Martyr
	public Martyr(String name, byte age, Date dateOfDeath, boolean isMale) {
		super();
		this.name = name;
		this.age = age;
		this.dateOfDeath = dateOfDeath;
		this.isMale = isMale;
	}

	// This method to get martyr name
	public String getName() {
		return name;
	}

	// This method to get martyr age
	public byte getAge() {
		return age;
	}

	// This method to get martyr gender
	public char getGender() {
		return (isMale) ? 'M' : 'F';
	}

	// This method to update martyr gender
	public void setGender(char isMale) {
		this.isMale = isMale == 'M';
	}

	// This method to get martyr date of death
	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	// This method to get if martyr is male or not
	public boolean isMale() {
		return isMale;
	}

	// This method to update martyr name
	public void setName(String name) {
		this.name = name;
	}

	// This method to update martyr age
	public void setAge(byte age) {
		this.age = age;
	}

	// This method to update martyr date of death
	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	// This method to set if martyr is male or not
	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	// This method to get martyr simple date of death (month/day/year)
	public String getSimpleDateOfDeath() {
		return new SimpleDateFormat("MM/dd/yyyy").format(dateOfDeath);
	}

	// This method to update martyr date of death
	public void setSimpleDateOfDeath(String date) throws ParseException {
		try {
			dateOfDeath = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (ParseException e) {
			throw e;
		}
	}

	// This method to get all formation of martyr
	public String getInfo(String location) {
		return name + "," + age + "," + location + "," + getSimpleDateOfDeath() + "," + getGender();
	}

	// This method to check equality of martyr
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Martyr) {
			Martyr martyr = (Martyr) obj;
			return martyr.name.equals(name) && martyr.getAge() == age && martyr.getDateOfDeath().equals(dateOfDeath)
					&& (isMale == martyr.isMale);
		}
		return false;
	}

}
