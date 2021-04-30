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

@Entity
@Table(name="user")
public class User {

	@Id
	@Column(name="username")
	private String username; 
	
	@Column(name = "password")
	private String password; 
	
	@Column(name = "firstname")
	private String firstname; 
	
	@Column(name = "lastname")
	private String lastname; 
	
	@ManyToMany(fetch = FetchType.LAZY,	cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name="usercalendar", joinColumns=@JoinColumn(name="username"), inverseJoinColumns = @JoinColumn(name="calendar_code"))
	@JsonIgnore
	private List<Calendar> calendars; 	
	
	public List<Calendar> getCalendars() {
		return calendars;
	}
	
	public void setCalendars(List<Calendar> calendars) {
		this.calendars = calendars;
	}
	
	public User() {		
		
	}
	
	public User(String username, String password, String firstname, String lastname) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public void addCalendar(Calendar theCalendar) {
		if(theCalendar == null) {
			calendars = new ArrayList<>(); 
		}
		calendars.add(theCalendar); 		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstname=" + firstname + ", lastname="
				+ lastname + "]";
	}
	
	
	
}
