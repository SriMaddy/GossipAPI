package app.priority.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "priority")
public class Priority {

	@Id
	@GeneratedValue
	private long id;
	
	private long high;
	
	private long medium;
	
	private long low;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getHigh() {
		return high;
	}

	public void setHigh(long high) {
		this.high = high;
	}

	public long getMedium() {
		return medium;
	}

	public void setMedium(long medium) {
		this.medium = medium;
	}

	public long getLow() {
		return low;
	}

	public void setLow(long low) {
		this.low = low;
	}
	
}
