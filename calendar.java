package project;

import java.io.Serializable;
import java.util.Calendar;

public class calendar implements Serializable{
	static Calendar c = Calendar.getInstance();
	static int thisYear = c.get(Calendar.YEAR), thisMonth = c.get(Calendar.MONTH)+1, today = c.get(Calendar.DATE);
	int year = thisYear, month = thisMonth, date = today;
	int fd[] = {31,28,31,30,31,30,31,31,30,31,30,31};	//finalDate ������ �� �ϼ�
	String day[] = {"��","��","ȭ","��","��","��","��"};	//����
	int cal[][] = new int[6][7];
	private static String solar[] = {"11","31","55","66","815","103","109","1225"};	//��� ������
	private static String anniversary[]	= {"45","58","515","717"};	//�����
//	private static String lunar[] = {"11","12","48","814","815","816"};	//���� ������, ����
	String holiday;
	String avs;	//anniversary
	
	public boolean isSolarHoliday(String month,String date) {	//��� ���������� Ȯ���ϴ� �޼���
		for(int i = 0;i<solar.length;i++) {
			if(solar[i].equals(month+date)) {
				holiday = solar[i];
				return true;
			}
		}
		return false;
	}
	
	public boolean isAnniversary(String month,String date) {	//��������� Ȯ���ϴ� �޼���
		for(int i = 0;i<anniversary.length;i++) {
			if(anniversary[i].equals(month+date)) {
				avs = anniversary[i];
				return true;
			}
		}
		return false;
	}
	
	private boolean leapYear(int year) {	//�������� Ȯ���ϴ� �޼���
		if(year%4==0 && year%100!=0 || year%400==0) {
			return true;
		}else {
			return false;
		}
	}
	
	private int startDay(int year, int month) {	//1���� ������ ���ϴ� �޼���
		int sDay = (c.get(Calendar.DAY_OF_WEEK)+7-(c.get(Calendar.DAY_OF_MONTH)%7))%7;	//startDay 1���� ������ �����ϴ� �޼��� ����
		if(year!=thisYear || month!=thisMonth) {
			int subYear = 0;	//���� �⵵�� ����ڰ� �Է��� �⵵�� �⵵ ���� ���̸� �����ϴ� �޼��� ����
			int subMonth = 0;	//���� �ް� ����ڰ� �Է��� ���� �� ���� ���̸� �����Ѵ� �޼��� ����
			int cDay = 0;	//1���� ������ ����ϴµ� �ʿ��� ���� �����ϴ� �޼��� ����
			int lyCount = 0;	//������ ���Ե� �⵵�� �� �� �ִ����� �����ϴ� �޼��� ����
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
	
	private void makeCalendar(int year,int month) {	//����ڰ� �Է��� �⵵�� ���� �޷� �迭�� ����� �޼���
		int date = 1;	//���� ��
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
	
	public calendar(int year, int month, int date) {	//calendar ������
		this.year = year;
		this.month = month;
		this.date = date;
		makeCalendar(year,month);
	}
}
