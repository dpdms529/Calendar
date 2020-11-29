package project;

import java.io.Serializable;
import java.util.Calendar;

public class calendar implements Serializable{
	static Calendar c = Calendar.getInstance();
	static int thisYear = c.get(Calendar.YEAR), thisMonth = c.get(Calendar.MONTH)+1, today = c.get(Calendar.DATE);
	int year = thisYear, month = thisMonth, date = today;
	int fd[] = {31,28,31,30,31,30,31,31,30,31,30,31};	//finalDate 마지막 날 일수
	String day[] = {"일","월","화","수","목","금","토"};	//요일
	int cal[][] = new int[6][7];
	private static String solar[] = {"11","31","55","66","815","103","109","1225"};	//양력 공휴일
	private static String anniversary[]	= {"45","58","515","717"};	//기념일
//	private static String lunar[] = {"11","12","48","814","815","816"};	//음력 공휴일, 명절
	String holiday;
	String avs;	//anniversary
	
	public boolean isSolarHoliday(String month,String date) {	//양력 공휴일인지 확인하는 메서드
		for(int i = 0;i<solar.length;i++) {
			if(solar[i].equals(month+date)) {
				holiday = solar[i];
				return true;
			}
		}
		return false;
	}
	
	public boolean isAnniversary(String month,String date) {	//기념일인지 확인하는 메서드
		for(int i = 0;i<anniversary.length;i++) {
			if(anniversary[i].equals(month+date)) {
				avs = anniversary[i];
				return true;
			}
		}
		return false;
	}
	
	private boolean leapYear(int year) {	//윤년인지 확인하는 메서드
		if(year%4==0 && year%100!=0 || year%400==0) {
			return true;
		}else {
			return false;
		}
	}
	
	private int startDay(int year, int month) {	//1일의 요일을 구하는 메서드
		int sDay = (c.get(Calendar.DAY_OF_WEEK)+7-(c.get(Calendar.DAY_OF_MONTH)%7))%7;	//startDay 1일의 요일을 저장하는 메서드 변수
		if(year!=thisYear || month!=thisMonth) {
			int subYear = 0;	//현재 년도와 사용자가 입력한 년도의 년도 수의 차이를 저장하는 메서드 변수
			int subMonth = 0;	//현재 달과 사용자가 입력한 달의 달 수의 차이를 저장한는 메서드 변수
			int cDay = 0;	//1일의 요일을 계산하는데 필요한 값을 저장하는 메서드 변수
			int lyCount = 0;	//윤년이 포함된 년도가 몇 번 있는지를 저장하는 메서드 변수
			if(thisYear>=year) {
				subYear = thisYear - year;
				for(int i = 0;i<subYear;i++) {
					if(leapYear(year)) {
						lyCount++;
					}	
					year++;
				}
				if(leapYear(this.year) && month>2) {
					lyCount--;
				}
				sDay = (sDay + 7 - ((subYear * 365) + lyCount)%7) % 7;
				if(sDay == 0) {
					sDay = 7;
				}
				
				if(thisMonth>=month) {
					subMonth = thisMonth-month;
					for(int i = 0;i<subMonth;i++) {
						if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) {
							cDay -= 31 % 7;
						}else if(month==4 || month==6 || month==9 ||month==11) {
							cDay -= 30 % 7;
						}else {
							if(leapYear(thisYear)) {
								cDay -= 29 % 7;
							}else {
								cDay -= 28 % 7;
							}
						}
						month++;
					}
					
				}else {
					subMonth = month - thisMonth;
					for(int i = 0;i<subMonth;i++) {
						if(month-1==1 || month-1==3 || month-1==5 || month-1==7 || month-1==8 || month-1==10 || month-1==12) {
							cDay += 31 % 7;
						}else if(month-1==4 || month-1==6 || month-1==9 || month-1==11) {
							cDay += 30 % 7;
						}else {
							if(leapYear(thisYear)) {
								cDay += 29 % 7;
							}else {
								cDay += 28 % 7;
							}
						}
						month--;
					}
				}
			}else {
				subYear = year - thisYear;
				for(int i = 0;i<subYear;i++) {
					if(leapYear(year)) {
						lyCount++;
					}
					year--;
				}
				if(leapYear(this.year) && month<=2) {
					lyCount--;
				}
				sDay = (sDay +((subYear * 365) + lyCount)) % 7;
				if(sDay == 0) {
					sDay = 7;
				}
				
				if(thisMonth>=month) {
					subMonth = thisMonth-month;
					for(int i = 0;i<subMonth;i++) {
						if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) {
							cDay -= 31 % 7;
						}else if(month==4 || month==6 || month==9 || month==11) {
							cDay -= 30 % 7;
						}else {
							if(leapYear(thisYear)) {
								cDay -= 29 % 7;
							}else {
								cDay -= 28 % 7;
							}
						}
						month++;
					}
					
				}else {
					subMonth = month - thisMonth;
					for(int i = 0;i<subMonth;i++) {
						if(month-1==1 || month-1==3 || month-1==5 || month-1==7 || month-1==8 || month-1==10 || month-1==12) {
							cDay += 31 % 7;
						}else if(month-1==4 || month-1==6 || month-1==9 || month-1==11) {
							cDay += 30 % 7;
						}else {
							if(leapYear(thisYear)) {
								cDay += 29 % 7;
							}else {
								cDay += 28 % 7;
							}
						}
						month--;
					}
				}
			}
			sDay = (sDay + (7*subMonth) + cDay) % 7;
		}
		if(sDay == 0) {
			sDay = 7;
		}
		return sDay;
	}
	
	private void makeCalendar(int year,int month) {	//사용자가 입력한 년도와 달의 달력 배열을 만드는 메서드
		int date = 1;	//날의 값
		if(leapYear(year) && month == 2) {
			fd[1] = 29;
		}
		for(int i = 0;i<cal.length;i++) {
			for(int j = 0;j<cal[i].length;j++) {
				if(!(i == 0 && j<startDay(year, month))) {
					cal[i][j] = date++;
					
					if(date>fd[month-1]+1) {
						cal[i][j] = -1;
					}
				}else {
					cal[i][j] = 0;
				}
			}
		}
	}
	
	public calendar(int year, int month, int date) {	//calendar 생성자
		this.year = year;
		this.month = month;
		this.date = date;
		makeCalendar(year,month);
	}
}
