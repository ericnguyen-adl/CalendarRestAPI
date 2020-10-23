package work.ericnguyen.calendar.dao;


import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import work.ericnguyen.calendar.entity.Calendar;
import work.ericnguyen.calendar.entity.NonWorkingDay;


@Repository
public class CalendarDAOHibernateImpl implements CalendarDAO {
	
	private EntityManager entityManager; 
	
	@Autowired
	public CalendarDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager; 		
	}

	@Override
	@Transactional
	public List<Calendar> getCalendars() {	
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create the query to select all calendar		
		Query<Calendar> theQuery = 
				currentSession.createQuery("from Calendar", Calendar.class); 	
		
		// execute query 
		List<Calendar> calendars = theQuery.getResultList(); 
		
		return calendars; 
	}

	@Override
	@Transactional
	public List<NonWorkingDay> getNonWorkingDays() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create the query to select all NonWorkingDay
		Query<NonWorkingDay> theQuery = currentSession.createQuery("from NonWorkingDay", NonWorkingDay.class);

		// execute query
		List<NonWorkingDay> nonWorkingDays = theQuery.getResultList();

		return nonWorkingDays;
	}

	@Override
	@Transactional
	public List<NonWorkingDay> getNonWorkingDaysForCalendar(String calendarCode) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class); 
		
		// get the specific calendar based on calendar Code
		Calendar theCalendar = currentSession.get(Calendar.class, calendarCode); 
		
		// get the list of NonWorkDays for theCalendar
		return theCalendar.getNonWorkingDays(); 				
				
	}


	@Override
	@Transactional
	public Calendar getCalendar(String calendarCode) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class); 
				
		// get the specific calendar based on calendar Code
		Calendar theCalendar = currentSession.get(Calendar.class, calendarCode); 
				
		// get the list of NonWorkDays for theCalendar
		return theCalendar; 
	}


	@Override
	@Transactional
	public List<NonWorkingDay> addNonWorkingDayToCalendar(String calendarCode, NonWorkingDay theNonWorkingDay) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class); 
						
		// get the specific calendar based on calendar Code
		Calendar theCalendar = currentSession.get(Calendar.class, calendarCode); 
		
		// Add the NonWorkingDay to the calendar
		theCalendar.addNonWorkingDay(theNonWorkingDay);
		
		// Save the calendar
		currentSession.save(theCalendar); 
		
		return getCalendar(calendarCode).getNonWorkingDays(); 
		
	}


	@Override
	@Transactional
	public List<NonWorkingDay> deleteNonWorkingDayFromCalendar(String calendarCode, String nonWorkingDayCode) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class); 
		
		// get the specific calendar based on calendar Code
		Calendar theCalendar = currentSession.get(Calendar.class, calendarCode); 
		
		// get the NonWorkingDay base on nonWorkingDayCode
		NonWorkingDay theNonWorkingDay = currentSession.get(NonWorkingDay.class, nonWorkingDayCode); 
		
		theCalendar.getNonWorkingDays().remove(theNonWorkingDay); 
		
		currentSession.save(theCalendar); 
		
		return getCalendar(calendarCode).getNonWorkingDays(); 		
		
	}

}
