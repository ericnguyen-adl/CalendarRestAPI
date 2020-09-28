package work.ericnguyen.calendar.entity;

import java.time.LocalDate;
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
@Table(name="nonworkingday")
public class NonWorkingDay {	
	@Id
	@Column(name="nwday_code")
	private String nwdayCode; 	
	
	@Column(name="nwday_name")
	private String nwdayName; 	
	
	@Column(name="date")
	private LocalDate date;
	
	@ManyToMany(fetch = FetchType.LAZY,	cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name="calendarnonworkingday", joinColumns=@JoinColumn(name="nwday_code"), inverseJoinColumns = @JoinColumn(name="calendar_code"))
	@JsonIgnore
	private List<Calendar> calendars; 	

	public List<Calendar> getCalendars() {
		return calendars;
	}

	public void setCalendars(List<Calendar> calendars) {
		this.calendars = calendars;
	}

	public NonWorkingDay() {
	}

	public NonWorkingDay(String nwdayCode, String nwdayName, LocalDate date) {
		this.nwdayCode = nwdayCode;
		this.nwdayName = nwdayName;
		this.date = date;
	}

	public String getNwdayCode() {
		return nwdayCode;
	}

	public void setNwdayCode(String nwdayCode) {
		this.nwdayCode = nwdayCode;
	}

	public String getNwdayName() {
		return nwdayName;
	}

	public void setNwdayName(String nwdayName) {
		this.nwdayName = nwdayName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "NonWorkingDay [nwdayCode=" + nwdayCode + ", nwdayName=" + nwdayName + ", date=" + date + "]";
	} 	
	
}
