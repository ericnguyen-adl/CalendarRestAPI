package work.ericnguyen.calendar.rest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import work.ericnguyen.calendar.dao.CalendarDAO;
import work.ericnguyen.calendar.entity.Calendar;
import work.ericnguyen.calendar.entity.NonWorkingDay;

@RestController
@RequestMapping("/api")
public class CalendarRestController {
	
	private CalendarDAO calendarDAO; 	
	
	@Autowired
	public CalendarRestController(CalendarDAO theCalendarDAO) {
		calendarDAO = theCalendarDAO; 
	}	
	
	// API to get the list of calendars 
	@GetMapping("/calendars")
	public List<Calendar> getCalendars() {
		return calendarDAO.getCalendars(); 
	}
	
	// API to get the list of nonworkingdays
	@GetMapping("/nonworkingdays")
	public List<NonWorkingDay> getNonWorkingDays() {
		return calendarDAO.getNonWorkingDays(); 
	}
	
	// API to get the list of nonworkingdays for a specific calendar
	@GetMapping("/calendars/{calendarCode}")
	public List<NonWorkingDay> getNonWorkingDaysForCalendar(@PathVariable String calendarCode) {
		List<NonWorkingDay> theNonWorkingDays = calendarDAO.getNonWorkingDaysForCalendar(calendarCode); 		
		return theNonWorkingDays; 
	}
	
	// API to calculate the new DATE base on current DATE and Processing Time in Days
	@PostMapping("/calculateNewDate")
	public LocalDate calculateNewDate(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
			@RequestParam int processingDays) {				
		LocalDate newDate = startDate.plusDays(processingDays); 
		return newDate; 		
	}
	
	// API to calculate the new DATE base on current DATE and Processing Time in Days but exclude weekend
	@PostMapping("/calculateNewDateExcludeWk")
	public LocalDate calculateNewDateExcludeWk(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
			@RequestParam int processingDays) {				

        // If processingDays < 1, assign the startDate to newDate. 
        LocalDate newDate = startDate; 
        int addedWorkingDays = 0; 
        
        // If the processingDays >= 1, the algorithm will start checking all date from startDate 
        while(addedWorkingDays < processingDays) {            
        	// If newDate is not weekend, increase the workingDay. 
            if(newDate.getDayOfWeek()!= DayOfWeek.SATURDAY && newDate.getDayOfWeek()!= DayOfWeek.SUNDAY) 
            {
                addedWorkingDays++;             
            }            
            newDate = newDate.plusDays(1);               
        }
        // if the newDate is Saturday, need to move to next Monday. 
        if(newDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
        	newDate = newDate.plusDays(2); 
        }
        
        return newDate; 		
	}
	
	// API to calculate new date exclude both weekend and holiday
	@PostMapping("/calculateNewDateExcludeAll")
	public LocalDate calculateNewDateExcludeAll(@RequestParam String calendarCode, @RequestParam int processingDays,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
		
		// get the list of NonWorkingDay for the calendarCode. 
		List<NonWorkingDay> theNonWorkingDays = calendarDAO.getNonWorkingDaysForCalendar(calendarCode);
		List<LocalDate> theNonWorkingDaysDateList = 
					theNonWorkingDays.stream().map(u -> u.getDate()).collect(Collectors.toList()); 
		
		// if processingDays < 1, assign the startDate to newDate 
		LocalDate newDate = startDate; 
		int addedWorkingDays = 0; 
		
		// if processingDays >= 1: 
		// if the newDate is not weekend and holiday, add the addWorkingDays
		while(addedWorkingDays < processingDays) {            
        	// If newDate is not weekend and holiday, increase the workingDay. 
            if(newDate.getDayOfWeek()!= DayOfWeek.SATURDAY && newDate.getDayOfWeek()!= DayOfWeek.SUNDAY
            		&& !theNonWorkingDaysDateList.contains(newDate)) 
            {
                addedWorkingDays++;             
            }            
            newDate = newDate.plusDays(1);               
        }
		
		// if the calculated newDate is weekend or holiday, use the nextDate. 
		while(newDate.getDayOfWeek()== DayOfWeek.SATURDAY || newDate.getDayOfWeek() == DayOfWeek.SUNDAY
        		&& theNonWorkingDaysDateList.contains(newDate)) {
			newDate = newDate.plusDays(1); 
		}		
		
		return newDate; 
		
	}
	
	@PostMapping("/addNonWorkingDayToCalendar")
	public List<NonWorkingDay> addNonWorkingDayToCalendar(@RequestParam String calendarCode, @RequestParam String nonWorkingDayCode,
			@RequestParam String nonWorkingDayName, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		
		// Create the NonWorkingDay based on the provided info. 
		NonWorkingDay theNonWorkingDay = new NonWorkingDay(nonWorkingDayCode, nonWorkingDayName, date); 
		
		// Call the DAO to add the NonWorkingDay to calendar
		List<NonWorkingDay> nonWorkingDays = calendarDAO.addNonWorkingDayToCalendar(calendarCode, theNonWorkingDay); 
			
		// return the list of NonWorkingDay for the calendar				
		return nonWorkingDays; 		
		
	}
	
	@PostMapping("/deleteNonWorkingDayFromCalendar")
	public List<NonWorkingDay> deleteNonWorkingDayFromCalendar(@RequestParam String calendarCode, @RequestParam String nonWorkingDayCode) {
		
		
		// Call the DAO to add the NonWorkingDay to calendar
		List<NonWorkingDay> nonWorkingDays = calendarDAO.deleteNonWorkingDayFromCalendar(calendarCode, nonWorkingDayCode); 
			
		// return the list of NonWorkingDay for the calendar				
		return nonWorkingDays; 		
		
	}

	
}
