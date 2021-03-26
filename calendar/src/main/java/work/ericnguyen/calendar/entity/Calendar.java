package work.ericnguyen.calendar.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Calendar")
@Table(name = "calendar")
public class Calendar {
	// define fields, constructors, getters and setters
	// annotate the fields


	@Id
	@Column(name = "calendar_code")
	private String calendarCode;

	@Column(name = "calendar_name")
	private String calendarName;

	@Column(name = "place")
	private String place;

	@Column(name = "description")
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "calendarnonworkingday", joinColumns = @JoinColumn(name = "calendar_code"), inverseJoinColumns = @JoinColumn(name = "nwday_code"))
	@JsonIgnore
	private List<NonWorkingDay> nonWorkingDays;

	public List<NonWorkingDay> getNonWorkingDays() {
		return nonWorkingDays;
	}

	public void setNonWorkingDays(List<NonWorkingDay> nonWorkingDays) {
		this.nonWorkingDays = nonWorkingDays;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinTable(name = "usercalendar", joinColumns = @JoinColumn(name = "calendar_code"), inverseJoinColumns = @JoinColumn(name = "username"))
	@JsonIgnore
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Calendar() {
	}

	public Calendar(String calendarCode, String calendarName, String place, String description) {
		this.calendarCode = calendarCode;
		this.calendarName = calendarName;
		this.place = place;
		this.description = description;
	}
	
	public void addNonWorkingDay(NonWorkingDay theNonWorkingDay) {
		if(nonWorkingDays == null) {
			nonWorkingDays = new ArrayList<>(); 
		}
		nonWorkingDays.add(theNonWorkingDay); 		
	}

	public String getCalendarCode() {
		return calendarCode;
	}

	public void setCalendarCode(String calendarCode) {
		this.calendarCode = calendarCode;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
