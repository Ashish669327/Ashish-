package utils;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.openqa.selenium.remote.RemoteWebDriver;

public class GenericMethods {

	public String getCurrentDate() {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MMM-d");
		Date date = new Date();
		return dateformat.format(date).toUpperCase();
	}

	public String getCurrentDateWithRequiredFormat(String format) {
		DateFormat dateformat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateformat.format(date).toUpperCase();
	}

	public String getCurrentDateWithFormat() {
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateformat.format(date);
	}

	// First parameter for month, Second parameter for date, Third parameter for minute
	public String getCurrentDateWithDashFormat(int... monthsdays) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (monthsdays.length == 3) {
			c.add(Calendar.MONTH, monthsdays[0]);
			c.add(Calendar.DATE, monthsdays[1]);
			c.add(Calendar.MINUTE, monthsdays[2]);
		}
		if (monthsdays.length == 2) {
			c.add(Calendar.MONTH, monthsdays[0]);
			c.add(Calendar.DATE, monthsdays[1]);
		}
		if (monthsdays.length == 1) {
			c.add(Calendar.MONTH, monthsdays[0]);
			c.add(Calendar.DATE, 0);
		}
		if (monthsdays.length == 0) {
			c.add(Calendar.MONTH, 0);
			c.add(Calendar.DATE, 0);
		}
		return sdf.format(c.getTime());
	}
	
	public String getCurrentDateWithSlashFormat(int... monthsdays) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (monthsdays.length == 3) {
			c.add(Calendar.MONTH, monthsdays[0]);
			c.add(Calendar.DATE, monthsdays[1]);
			c.add(Calendar.MINUTE, monthsdays[2]);
		}
		if (monthsdays.length == 2) {
			c.add(Calendar.MONTH, monthsdays[0]);
			c.add(Calendar.DATE, monthsdays[1]);
		}
		if (monthsdays.length == 1) {
			c.add(Calendar.MONTH, monthsdays[0]);
			c.add(Calendar.DATE, 0);
		}
		if (monthsdays.length == 0) {
			c.add(Calendar.MONTH, 0);
			c.add(Calendar.DATE, 0);
		}
		return sdf.format(c.getTime());
	}


	public String getEndDate(int difference) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-d");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, difference); // Adding 3 Days
		String output = sdf.format(c.getTime());
		return output.toUpperCase();
	}
	public String setDateFormate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-d");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		String output = "9/20/2024";
		return output.toUpperCase();
	}
	public String getEndDateWithFormat() {
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 3); // Adding 3 Days
		String output = dateformat.format(c.getTime());
		return output.toUpperCase();
	}

	public String getCurrentTime() {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		return timeFormat.format(date);
	}

	public String getCurrentDateWithTime() {
		SimpleDateFormat date = new SimpleDateFormat("ddMMyyyyHHmmss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return date.format(timestamp);
	}

	public String getDateWithMonthName(String str) throws ParseException {
		String s = str.substring(0, str.indexOf(" "));
		s=s.replace("/", "-");
	    Date date = new SimpleDateFormat("yyyy-MM-d").parse(s);
		DateFormat df = new SimpleDateFormat("MMMM d yyyy");
		Date convertDate = date;
		return df.format(convertDate);
	}
	
	public String getDateWithMonthNameNew(String str) throws ParseException {// by akg
		String s = str.substring(0, str.indexOf(" "));
		s=s.replace("/", "-");
		Date date = new SimpleDateFormat("MM-d-yyyy").parse(s);
		DateFormat df = new SimpleDateFormat("MMMM d yyyy");
		String Date1  = df.format(date);
		return Date1;
	}
	
	public String getDateWithRequiredMonthAndDate(int month, int date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-d");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, month);
		c.set(Calendar.DATE, date);
		String output = sdf.format(c.getTime());
		return output.toUpperCase();
	}

	public int getWeekNumber(String input, String format) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(input);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public String getRequiredDateAndTime(int... minutes) {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (minutes.length == 0) {
			c.add(Calendar.MINUTE, 0);
		} else {
			c.add(Calendar.MINUTE, minutes[0]);
		}
		return timeFormat.format(c.getTime());
	}

	public String getMonthNameFromDate(String str) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-d");
		Date date = df.parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return monthNames[cal.get(Calendar.MONTH)];
	}
	
	public String getMonthNameFromDateInFormat(String str,String format) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return monthNames[cal.get(Calendar.MONTH)];
	}

	public String getCurrentDateWithOwnFormat(String format) {
		DateFormat dateformat = new SimpleDateFormat(format);
		Date date = new Date();
		String currentDate = dateformat.format(date).toUpperCase();
		return currentDate;
	}

	public String getEndDate(int difference, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, difference); // Adding 3 days
		String output = sdf.format(c.getTime());
		return output.toUpperCase();
	}

	public String getCurrentDateWithUserFormate(String format) {
		DateFormat dateformat = new SimpleDateFormat(format);
		Date date = new Date();
		String currentDate = dateformat.format(date);
		return currentDate;
	}

	public String getETDateTimeWithDayName() {
		DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
		ZoneId etZoneId = ZoneId.of("US/Eastern");
		LocalDateTime currentDateTime = LocalDateTime.now();
		ZonedDateTime currentISTime = currentDateTime.atZone(istZoneId);
		ZonedDateTime currentETime = currentISTime.withZoneSameInstant(etZoneId);
		String str = currentETime.getDayOfWeek().toString().toLowerCase();
		StringBuffer dayName = new StringBuffer(str);
		dayName.setCharAt(0, Character.toUpperCase(str.charAt(0)));
		return (etFormat.format(currentETime) + " " + dayName);
	}

	public String getAddedHourTimeAsPerETTime(int nexthour, int nextDay) {                       // modifiyed by sel+9
		DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		ZoneId etZoneId = ZoneId.of("US/Eastern");
		ZonedDateTime now = ZonedDateTime.now(etZoneId);
		ZonedDateTime hourLater = now.plusHours(nexthour).plusDays(nextDay);
		String str = hourLater.getDayOfWeek().toString().toLowerCase();
		StringBuffer dayName = new StringBuffer(str);
		dayName.setCharAt(0, Character.toUpperCase(str.charAt(0)));
		return (etFormat.format(hourLater) + " " + dayName);
	}

	public String getTimeWithTimeZone(String timeZone, String format) {  // modified by sel+8
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		String utcTime = sdf.format(new Date());
		return utcTime;
	}
	
	public String getTimeWithTimeZoneIST(String timeZone, String format) throws Exception {  // modified by sel+6
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		String utcTime = sdf.format(new Date());
		return utcTime;    
	}

	/**
	 * This method is use to get the date of Current Week i.e "Sun Mar 21 14:23:15 IST 2021"
	 * Added by selenium+4
	 * @return
	 */
	public String getWeekStartDate() {
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		Date d= calendar.getTime();
		String s =String.valueOf(d);
		String array[] = s.split(" ");
		String firstDateOfWeek = array[2].replace("0", "");
		return firstDateOfWeek;
	}
	/**
	 * This method is use to get the day of Current Week i.e "Sunday"
	 * Added by selenium+4
	 * @return
	 */
	public String getCurrentWeekStartDay() {
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		Date d= calendar.getTime();
		Format   f = new SimpleDateFormat("EEEE");
		String str = f.format(d);
		System.out.println("Full Day Name = "+str);
		return str;
	}

	public Date getCurrentWeekStartDayWithMonth() {
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		Date d= calendar.getTime();
		return d;
	}

	public Date getCurrentWeekLastDayWithMonth() {
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			calendar.add(Calendar.DATE,  1);
		}
		Date d= calendar.getTime();
		return d;
	}

	public List<String> getNextStartAndLastDayDateWithMonth() {
		List<String> weekdateList= new ArrayList<String>();

		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DATE,  1);
		}

		Date d= calendar.getTime();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			calendar.add(Calendar.DATE,  1);
		}
		Date d2= calendar.getTime();
		weekdateList.add(String.valueOf(d));
		weekdateList.add(String.valueOf(d2));
		return weekdateList;

	}

	public String getRequiredDateAndTime(String time,String timeFromat,int... minutes) {
		DateFormat timeFormat = new SimpleDateFormat(timeFromat);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		switch(time.toLowerCase()) {
		case "minutes":{
			if (minutes.length == 0) 
				c.add(Calendar.MINUTE, 0);

			else 
				c.add(Calendar.MINUTE, minutes[0]);
			break;
		}
		case "hours":{
			if (minutes.length == 0) 
				c.add(Calendar.HOUR, 0);

			else 
				c.add(Calendar.HOUR, minutes[0]);
			break;
		}
		}

		System.out.println(timeFormat.format(c.getTime()));
		return timeFormat.format(c.getTime());
	}

	public String getConvertISTTimeToEST(String dateFormat) {

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(dateFormat);
		dateTimeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
		Date date = new Date();
		DateFormat timeFormat = new SimpleDateFormat(dateFormat);
		timeFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		String estTime = timeFormat.format(date);
		try {
			date = new SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(estTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(date);
	}

	public String getDateWithWeekSTartingDay(RemoteWebDriver driver, String weekStartingDayName, String weekType){

		String output =null;
		Calendar calendar = Calendar.getInstance();
		switch (weekStartingDayName.toLowerCase()) {
		case "monday":

			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				if(weekType.toLowerCase() == "current")
					calendar.add(Calendar.DATE, 1);
			}
			Date date= calendar.getTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "E MMM dd HH:mm:ss z uuuu" )
					.withLocale( Locale.US );		
			ZonedDateTime zDT = ZonedDateTime.parse(date.toString(),formatter);
			System.out.println(date);   
			LocalDate ld = zDT.toLocalDate();
			DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern( "dd/MM/uuuu" );
			output = ld.format( fLocalDate) ;
			break;

		default:
			break;
		}


		return output;
	}

	/**
	 * This methods is used to get last , current , next date with day wise
	 * @param startingWeekDaysName Its use as an input
	 * @param oldDateOrNewDate. Its use as an input. 
	 * Added by selenium+4
	 * @return
	 */
	public List<String> getDateWithWeekWise(String startingWeekDaysName, String lastDateOrNextDate) {
		List<String> pastPresentFutureDate = new ArrayList<String>(); 
		Calendar calendar = Calendar.getInstance();
		String currentDate[] = LocalDate.now().toString().split("-");
		boolean isOldDate = false;
		int mondayDate = 0;
		String typeCastDate = "";
		Date date = null;
		switch (startingWeekDaysName.toLowerCase()) {
		case "sunday":
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				if(lastDateOrNextDate.toLowerCase().equals("last")) {
					calendar.add(Calendar.DATE, -1);
					isOldDate = true;
				}
				else
					calendar.add(Calendar.DATE, 1);
			}
			date= calendar.getTime();
			typeCastDate =String.valueOf(date);
			break;
		case "monday":
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				if(lastDateOrNextDate.toLowerCase().equals("last")) {
					calendar.add(Calendar.DATE, -1);
					isOldDate = true;
				}
				else
					calendar.add(Calendar.DATE, 1);
			}
			date= calendar.getTime();
			typeCastDate =String.valueOf(date);

			break;

		case "tuesday":
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
				if(lastDateOrNextDate.toLowerCase().equals("last")) {
					calendar.add(Calendar.DATE, -1);
					isOldDate = true;
				}
				else
					calendar.add(Calendar.DATE, 1);
			}
			date= calendar.getTime();
			typeCastDate =String.valueOf(date);
			break;
		case "wednesday":
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
				if(lastDateOrNextDate.toLowerCase().equals("last")) {
					calendar.add(Calendar.DATE, -1);
					isOldDate = true;
				}
				else
					calendar.add(Calendar.DATE, 1);
			}
			date= calendar.getTime();
			typeCastDate =String.valueOf(date);
			break;
		case "thursday":
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
				if(lastDateOrNextDate.toLowerCase().equals("last")) {
					calendar.add(Calendar.DATE, -1);
					isOldDate = true;
				}
				else
					calendar.add(Calendar.DATE, 1);
			}
			date= calendar.getTime();
			typeCastDate =String.valueOf(date);
			break;
		case "friday":
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
				if(lastDateOrNextDate.toLowerCase().equals("last")) {
					calendar.add(Calendar.DATE, -1);
					isOldDate = true;
				}
				else
					calendar.add(Calendar.DATE, 1);
			}
			date= calendar.getTime();
			typeCastDate =String.valueOf(date);
			break;
		case "saturday":
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				if(lastDateOrNextDate.toLowerCase().equals("last")) {
					calendar.add(Calendar.DATE, -1);
					isOldDate = true;
				}
				else
					calendar.add(Calendar.DATE, 1);
			}
			date= calendar.getTime();
			typeCastDate =String.valueOf(date);
			break;
		default:
			break;
		}

		String array[] = typeCastDate.split(" ");
		if(isOldDate)
			mondayDate = -(Integer.parseInt(currentDate[2])-Integer.parseInt(array[2]));
		else {
			mondayDate = Integer.parseInt(currentDate[2])-Integer.parseInt(array[2]);
		}
		LocalDate currentWeekFirstDateStartFromMonday = LocalDate.now().plusDays(mondayDate);
		LocalDate futureDate = LocalDate.now().plusDays(mondayDate+7);
		LocalDate pastDate = LocalDate.now().plusDays(mondayDate-7);
		pastPresentFutureDate.add(currentWeekFirstDateStartFromMonday.toString());
		pastPresentFutureDate.add(futureDate.toString());
		pastPresentFutureDate.add(pastDate.toString());
		return pastPresentFutureDate;

	}

	/**
	 * This method is used for change date format from (yyyy-MM-dd) to (yyyy-MM-d)
	 * Added by selenium+4
	 * @param date
	 * @param oldDateFormat
	 * @param newDateFormat
	 * @return
	 */
	public String changeDateFormat(String date, String oldDateFormat,String newDateFormat) {
		String dateWithNewFormat = "";
		SimpleDateFormat sdf = new SimpleDateFormat(oldDateFormat);
		SimpleDateFormat sdfnew = new SimpleDateFormat(newDateFormat);
		try {
			dateWithNewFormat= sdfnew.format(sdf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateWithNewFormat;
	}
	public String getCurrentMonthDays  (RemoteWebDriver driver) { //sel+25
		List<String> stringList = null;
		String str1 = "";
		// Get current year and month
		List<Integer> mondayDays = getCurrentMonthMondayDays();
		// Print all Monday days
		for (int day : mondayDays) {
			System.out.println(day);
			String Value1 = String.valueOf(day);
			stringList = new ArrayList<>();
			stringList.add(Value1 + " ");
           for (String str2 : stringList) {
				System.out.println(str2);
				str1 = str1 + str2;
			}

		}
		return str1;
	}
	
	
	public String getCurrentMonthSundayDays(RemoteWebDriver driver) { //sel+25
		List<String> stringList = null;
		String str1 = "";
		// Get current year and month
		List<Integer> mondayDays = getCurrentMonthSundayDays();
		// Print all Monday days
		for (int day : mondayDays) {
			System.out.println(day);
			String Value1 = String.valueOf(day);
			stringList = new ArrayList<>();
			stringList.add(Value1 + " ");
           for (String str2 : stringList) {
				System.out.println(str2);
				str1 = str1 + str2;
			}
         }
		return str1;
	}
	
	
	
	public static List<Integer> getCurrentMonthMondayDays() {//sel+25
        List<Integer> mondayDays = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        // Iterate through each day of the month
        for (int day = 1; day <= currentDate.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(currentYear, currentMonth, day);
            // Check if the day is a Monday
            if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
                mondayDays.add(day); // Add the day of the month to the list
            }
        }
        return mondayDays;
    }
	public static List<Integer> getCurrentMonthSundayDays() {//sel+25
        List<Integer> sundayDays = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        // Iterate through each day of the month
        for (int day = 1; day <= currentDate.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(currentYear, currentMonth, day);
            // Check if the day is a Monday
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            	sundayDays.add(day); // Add the day of the month to the list
            }
        }
        return sundayDays;
    }


}