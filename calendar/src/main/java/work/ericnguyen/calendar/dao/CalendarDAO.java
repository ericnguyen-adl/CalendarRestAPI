package work.ericnguyen.calendar.dao;

import java.util.List;

import work.ericnguyen.calendar.entity.Calendar;
import work.ericnguyen.calendar.entity.NonWorkingDay;

public interface CalendarDAO {
	
//	public List<NonWorkingDay> getNonWorkingDays(String calendarCode); 
	
	public List<Calendar> getCalendars(); 
	public List<NonWorkingDay> getNonWorkingDays(); 
	public List<NonWorkingDay> getNonWorkingDaysForCalendar(String calendarCode);
	public Calendar getCalendar(String calendarCode);
	public List<NonWorkingDay> addNonWorkingDayToCalendar(String calendarCode, NonWorkingDay theNonWorkingDay);
	public List<NonWorkingDay> deleteNonWorkingDayFromCalendar(String calendarCode, String nonWorkingDayCode); 

}