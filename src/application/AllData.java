
package application;

// This class to contain all data to suer it in table view
public class AllData {

	// Attributes of AllData 
	private Martyr martyr;
	private String location;
	
	// Constructor to make objects of AllData
	public AllData(Martyr martyr, String location) {
		super();
		this.martyr = martyr;
		this.location = location;
	}

	// This method to get martyr
	public Martyr getMartyr() {
		return martyr;
	}
	
	// This method to get location of martyr
	public String getLocation() {
		return location;
	}
	
	// This method to set new martyr
	public void setMartyr(Martyr martyr) {
		this.martyr = martyr;
	}
	
	// This method to set new location.
	public void setLocation(String location) {
		this.location = location;
	}
	
}
