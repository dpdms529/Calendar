package project;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Frame extends JFrame implements ActionListener{
	private JPanel tPanel,cPanel,calP[][],ttPanel,ctPanel,clPanel,ctfPanel,stPanel,slPanel,sPanel,sptPanel,spcPanel,spbPanel,srPanel;
	private JLabel ttpL,clpL,calLD[],calL[][],ctfpLY,ctfpLM,slL,slLD,srLT,srLSR,srLE;
	private JButton ttpB,clpBL,clpBR,calB[][],ctfpB,sptpB,spbpB;
	private JTextField ctfpTY,ctfpTM,sptpTF;
	ArrayList<JCheckBox> spcpC = new ArrayList<JCheckBox>(),spcpC2; 
	ArrayList<JButton> spcpB = new ArrayList<JButton>();
	ArrayList<JPanel> spcpPanel = new ArrayList<JPanel>();
	int index = 0,check = 0;	
	calendar c;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	//제목 만들기
	private void makeTitle() {
				ttPanel = new JPanel();	//topTitlePanel 전체 제목 패널
				ttpL = new JLabel("Scheduler");	//topTitlePanelLabel 전체 제목 패널 라벨
				ttpL.setFont(new Font(ttpL.getFont().getName(),ttpL.getFont().getStyle(),50));
				ttpL.setForeground(Color.magenta);
				ttpB = new JButton("TODAY");	//topTitlePanelButton 전체 제목 패널 버튼
				ttpB.setBackground(Color.pink);
				ttpB.addActionListener(this);
				ttPanel.add(ttpL);
				ttPanel.add(ttpB);
	}
	
	//달력 라벨 만들기
	private void makeCalendarLabel() {
		clPanel = new JPanel(new FlowLayout());	//calendarLabelPanel 달력 라벨 패널
		clpL = new JLabel(c.thisYear + "년" + c.thisMonth + "월");	//calendarLabelPanelLabel 달력 라벨 패널 라벨
		clpL.setFont(new Font(clpL.getFont().getName(),clpL.getFont().getStyle(),30));
		clpBL = new JButton("<");	//calendarLabelPanelButtonLeft 달력 라벨 패널 버튼 왼쪽
		clpBR = new JButton(">");	//calendarLabelPanelButtonRight 달력 라벨 패널 버튼 오른쪽
		clpBL.setBackground(Color.pink);
		clpBR.setBackground(Color.pink);
		clpBL.addActionListener(this);
		clpBR.addActionListener(this);
		clPanel.add(clpBL);
		clPanel.add(clpL);
		clPanel.add(clpBR);
		ctPanel.add(clPanel,BorderLayout.NORTH);
	}
	
	//달력 만들기
	private void makeCalendar(int year, int month) {
		c = new calendar(year,month,c.today);
		cPanel = new JPanel(new GridLayout(0,7,3,3));	//calendarPanel 달력 패널
		calP = new JPanel[7][7];	//calPanel 달력 구성 패널
		calLD = new JLabel[7];	//calLabelDay	달력 구성 라벨 요일
		calB = new JButton[6][7];	//calButton 달력 구성 버튼
		calL = new JLabel[6][7];	//calendarLabel 달력 구성 라벨
		
		for(int i = 0;i<7;i++) {	//요일 적기
			calP[0][i] = new JPanel(new BorderLayout());
			calP[0][i].setBackground(Color.white);
			calLD[i] = new JLabel(c.day[i]);
			calLD[i].setHorizontalAlignment(JLabel.CENTER);
			calLD[i].setFont(new Font(calLD[i].getFont().getName(),calLD[i].getFont().getStyle(),20));
			if(calLD[i].getText() == "일") {
				calLD[i].setForeground(Color.red);
			}
			calP[0][i].setPreferredSize(new Dimension(60,80));
			calP[0][i].add(calLD[i],BorderLayout.CENTER);
			cPanel.add(calP[0][i]);
		}
		
		for(int rows = 1;rows<7;rows++) {	//날짜 적기
			for(int cols = 0;cols<7;cols++) {
				if(c.cal[rows-1][cols]!=0 && c.cal[rows-1][cols]!=-1) {
					calP[rows][cols] = new JPanel(new BorderLayout());
					calB[rows-1][cols] = new JButton(Integer.toString(c.cal[rows-1][cols]));
					calB[rows-1][cols].setFont(new Font(calB[rows-1][cols].getFont().getName(),calB[rows-1][cols].getFont().getStyle(),20));
					if(cols == 0) {
						calB[rows-1][cols].setForeground(Color.red);
					}
					calB[rows-1][cols].setBackground(Color.pink);
					calB[rows-1][cols].setPreferredSize(new Dimension(60,20));
					calB[rows-1][cols].addActionListener(this);
					calP[rows][cols].add(calB[rows-1][cols],BorderLayout.NORTH);
					calP[rows][cols].setBackground(Color.white);
					cPanel.add(calP[rows][cols]);
				}else{
					if(c.cal[0][6] != 0 || (c.cal[rows-1][cols] == -1 && rows-1 == 5)) {
						int m1count = 0; //minus1count
						for(int i = 0;i<7;i++) {
							if(c.cal[5][i] == -1) {
								m1count++;
							}
						}
						if(m1count == 7 && rows-1 == 5) {
							break;
						}
						calP[rows][cols] = new JPanel();
						calP[rows][cols].setBackground(Color.white);
						cPanel.add(calP[rows][cols]);
					}
				}
			}
		}
		
		for(int rows = 1;rows<7;rows++) {
			for(int cols = 0;cols<7;cols++) {
				String sMonth = Integer.toString(c.month);	//StringMonth
				String sDate = null;	//StringDate
				if(c.cal[rows-1][cols]!=0 && c.cal[rows-1][cols]!=-1) {	
					sDate = calB[rows-1][cols].getText();
					calL[rows-1][cols] = new JLabel();
					calL[rows-1][cols].setHorizontalAlignment(JLabel.CENTER);
				}	
				if(c.isSolarHoliday(sMonth,sDate)) {
					switch(c.holiday) {
					case "11" :
						calL[rows-1][cols].setText("신정");
						break;
					case "31" :
						calL[rows-1][cols].setText("삼일절");
						break;
					case "55" :
						calL[rows-1][cols].setText("어린이날");
						break;
					case "66" :
						calL[rows-1][cols].setText("현충일");
						break;
					case "815" :
						calL[rows-1][cols].setText("광복절");
						break;
					case "103" :
						calL[rows-1][cols].setText("개천절");
						break;
					case "109" :
						calL[rows-1][cols].setText("한글날");
						break;
					case "1225" :
						calL[rows-1][cols].setText("성탄절");
						break;
					}
					calB[rows-1][cols].setForeground(Color.red);
					calP[rows][cols].add(calL[rows-1][cols],BorderLayout.CENTER);
				}else if(c.isAnniversary(sMonth,sDate)) {
					switch(c.avs) {
					case "45" :
						calL[rows-1][cols].setText("식목일");
						break;
					case "58" :
						calL[rows-1][cols].setText("어버이날");
						break;
					case "515" :
						calL[rows-1][cols].setText("스승의날");
						break;
					case "717" :
						calL[rows-1][cols].setText("제헌절");
						break;
					}
					calP[rows][cols].add(calL[rows-1][cols],BorderLayout.CENTER);
				}
			}
		}
		ctPanel.add(cPanel,BorderLayout.CENTER);
	}
	
	//달력 텍스트 필드 만들기
	private void makeCalendarTextField() {
		ctfPanel = new JPanel();	//calendarTextFieldPanel 달력 텍스트 필드 패널
		ctfpTY = new JTextField(4);	//calendarTextFieldPanelTextYear 달력 텍스트 필드 패널 텍스트 연도
		ctfpLY = new JLabel("년");	//calendarTextFieldPanelLabelYear 달력 텍스트 필드 패널 라벨 연도
		ctfpLY.setFont(new Font(ctfpLY.getFont().getName(),ctfpLY.getFont().getStyle(),20));
		ctfpTM = new JTextField(4);	//calendarTextFieldPanelTextMonth 달력 텍스트 필드 패널 텍스트 달
		ctfpLM = new JLabel("월");	//calendarTextFieldPanelLabelMonth 달력 텍스트 필드 패널 라벨 달
		ctfpLM.setFont(new Font(ctfpLM.getFont().getName(),ctfpLM.getFont().getStyle(),20));
		ctfpB = new JButton("이동");	//calendarTextFieldPanelButton 달력 텍스트 필드 패널 버튼
		ctfpB.setBackground(Color.pink);
		ctfpB.addActionListener(this);
		ctfPanel.add(ctfpTY);
		ctfPanel.add(ctfpLY);
		ctfPanel.add(ctfpTM);
		ctfPanel.add(ctfpLM);
		ctfPanel.add(ctfpB);
		ctPanel.add(ctfPanel,BorderLayout.SOUTH);
	}
	
	private void makeSchedulerLabel() {
		slPanel = new JPanel();
		slL = new JLabel("TO DO LIST");
		slL.setFont(new Font(slL.getFont().getName(),slL.getFont().getStyle(),30));
		slLD = new JLabel(c.year + "/" + c.month + "/" + c.today);
		slLD.setFont(new Font(slL.getFont().getName(),slL.getFont().getStyle(),20));
		slLD.setForeground(Color.magenta);
		slPanel.add(slL);
		slPanel.add(slLD);
		stPanel.add(slPanel,BorderLayout.NORTH);
	}
	
	private void makeScheduler() {
		sPanel = new JPanel(new BorderLayout());	//schedulerPanel
		
		sptPanel = new JPanel();	//schedulerPanelTextPanel
		sptpTF = new JTextField(30);	//schedulerPanelTextPanelTextField
		sptpB = new JButton("입력");	//schedulerPanelTextPanelButton
		sptpB.setBackground(Color.pink);
		sptpB.addActionListener(this);
		sptPanel.add(sptpTF);
		sptPanel.add(sptpB);
		
		spcPanel = new JPanel();
		spcPanel.setLayout(new BoxLayout(spcPanel,BoxLayout.Y_AXIS));
		
		spbPanel = new JPanel();	//schedulerPanelButtonPanel
		spbpB = new JButton("저장");	//schedulerPanelButton
		spbpB.setBackground(Color.pink);
		spbpB.addActionListener(this);
		spbPanel.add(spbpB);
		
		sPanel.add(sptPanel,BorderLayout.NORTH);
		sPanel.add(spcPanel,BorderLayout.CENTER);
		sPanel.add(spbPanel,BorderLayout.SOUTH);
		stPanel.add(sPanel,BorderLayout.CENTER);
	}
	
	private void makeSuccessRatePanel() throws FileNotFoundException, IOException {
		srPanel = new JPanel();	//successratePanel
		srPanel.setLayout(new BoxLayout(srPanel,BoxLayout.Y_AXIS));
		srLT = new JLabel("Success Rate");	//successratePanelLabelTitle
		srLSR = new JLabel();	//successratePanelLabelSucessRate
		srLE = new JLabel();	//successratePanelLabelEvaluation
		srLT.setFont(new Font(srLT.getFont().getName(),srLT.getFont().getStyle(),30));
		srLSR.setFont(new Font(srLSR.getFont().getName(),srLSR.getFont().getStyle(),20));
		srLE.setFont(new Font(srLE.getFont().getName(),srLE.getFont().getStyle(),20));
		this.success();
		srPanel.add(srLT);
		srPanel.add(srLSR);
		srPanel.add(srLE);
		
		stPanel.add(srPanel,BorderLayout.SOUTH);
	}
	
	
	//Frame 생성자
	public Frame() throws FileNotFoundException, ClassNotFoundException, IOException {	
		this.setSize(1000,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Scheduler");
		
		tPanel = new JPanel(new BorderLayout());	//topPanel 전체 패널
		this.makeTitle();
		
		//달력
		ctPanel = new JPanel(new BorderLayout());	//calendarTopPanel 달력 전체 패널
		this.makeCalendarLabel();
		this.makeCalendar(c.thisYear,c.thisMonth);
		this.makeCalendarTextField();
		
		//스케쥴러
		stPanel = new JPanel(new BorderLayout());	//schedulerTopPanel
		this.makeSchedulerLabel();
		this.makeScheduler();
		if(new File(Integer.toString(c.year)+Integer.toString(c.month)+Integer.toString(c.date)+ ".txt").exists()) {
			this.getSchedule(c.year, c.month, c.date);
			this.saveSchedule(c.year,c.month,c.date);
		}
		
		this.makeSuccessRatePanel();
		
		tPanel.add(ttPanel,BorderLayout.NORTH);
		tPanel.add(ctPanel,BorderLayout.WEST);
		tPanel.add(stPanel,BorderLayout.EAST);
		this.add(tPanel);
		this.setVisible(true);
	}
	
	//이벤트 처리
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == clpBL) {	//이전 달로 이동
			this.lastMonth();
		}else if(e.getSource() == clpBR) {	//다음 달로 이동
			this.nextMonth();
		}else if(e.getSource() == ctfpB ) {	//입력한 연도,월로 이동
			this.move();
		}else if(e.getSource() == sptpB) {	//스케줄 추가
			try {
				this.addSchedule(index);
				this.success();
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(spcpB.contains(e.getSource())) {	//스케줄 삭제
			int ClikedIndex = spcpB.indexOf(e.getSource());
			try {
				this.deleteSchedule(ClikedIndex);
				this.success();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource() == spbpB) {	//스케줄 저장
			try {
				this.saveSchedule(c.year, c.month, c.date);
				this.success();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource() == ttpB) {	//오늘로 이동
			try {
				this.Today(e);
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
		}else {	//스케줄러 날짜 이동
			try {
				this.changeDate(e);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	//이벤트 처리 메서드
		private void lastMonth() {	//지난 달로 이동
			ctPanel.remove(cPanel);
			if(c.month>1 && c.month<=12) {
				this.makeCalendar(c.year,c.month-1);
			}else {
				this.makeCalendar(c.year-1, 12);
			}
			ctPanel.updateUI();
			clpL.setText(c.year + "년" + c.month + "월");
		}
		
		private void nextMonth() {	//다음 달로 이동
			ctPanel.remove(cPanel);
			if(c.month>=1 && c.month<12) {
				this.makeCalendar(c.year,c.month+1);
			}else {
				this.makeCalendar(c.year+1, 1);
			}
			ctPanel.updateUI();
			clpL.setText(c.year + "년" + c.month + "월");
		}
		
		private void move() {	//년,월 이동
			ctPanel.remove(cPanel);
			this.makeCalendar(Integer.parseInt(ctfpTY.getText()), Integer.parseInt(ctfpTM.getText()));
			ctPanel.updateUI();
			clpL.setText(c.year + "년" + c.month + "월");
			ctfpTY.setText("");
			ctfpTY.updateUI();
			ctfpTM.setText("");
			ctfpTM.updateUI();
		}
		
		private void Today(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
			ctPanel.remove(cPanel);
			this.makeCalendar(c.thisYear, c.thisMonth);
			ctPanel.updateUI();
			clpL.setText(c.year + "년" + c.month + "월");
			this.changeDate(e);
			
		}
	
		private void addSchedule(int index) throws FileNotFoundException, IOException {	//스케쥴 추가	
			spcpPanel.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
			spcpPanel.get(index).setMaximumSize(new Dimension(400,50));
			spcpC.add(new JCheckBox(sptpTF.getText()));
			spcpC.get(index).setFont(new Font(spcpC.get(index).getFont().getName(),spcpC.get(index).getFont().getStyle(),20));
			spcpB.add(new JButton("삭제"));
			spcpB.get(index).setBackground(Color.pink);
			spcpB.get(index).addActionListener(this);
			spcpPanel.get(index).add(spcpC.get(index));
			spcpPanel.get(index).add(spcpB.get(index));
			spcPanel.add(spcpPanel.get(index));
			this.index++;
			spcPanel.updateUI();
			sptpTF.setText("");
			sptpTF.updateUI();
			this.saveSchedule(c.year,c.month,c.date);
		}
		
		private void deleteSchedule(int ClikedIndex) throws FileNotFoundException, IOException {	//스케쥴 삭제
			spcPanel.remove(spcpPanel.get(ClikedIndex));
			spcpC.remove(ClikedIndex);
			spcpB.remove(ClikedIndex);
			spcpPanel.remove(ClikedIndex);
			spcPanel.updateUI();
			index--;
			this.saveSchedule(c.year, c.month, c.date);
		}
		
		private void saveSchedule(int year, int month ,int date) throws FileNotFoundException, IOException {	//스케줄 저장
			oos = new ObjectOutputStream(new FileOutputStream(Integer.toString(year)+Integer.toString(month)+Integer.toString(date)+ ".txt"));
			oos.writeObject(spcpC);
			for(int i = 0;i<spcpC.size();i++) {
				oos.writeBoolean(spcpC.get(i).isSelected());
			}
			oos.close();
		}
		
		private void getSchedule(int year, int month, int date) throws FileNotFoundException, IOException, ClassNotFoundException {	//저장된 스케줄 불러오기
			ois = new ObjectInputStream(new FileInputStream(Integer.toString(year)+Integer.toString(month)+Integer.toString(date)+".txt"));
			spcpC2 = (ArrayList<JCheckBox>)ois.readObject();
			boolean selected[] = new boolean[spcpC2.size()];
			for(int i = 0;i<spcpC2.size();i++) {
				selected[i] = ois.readBoolean();
			}
			for(int i = 0;i<spcpC2.size();i++) {
				sptpTF.setText(spcpC2.get(i).getText());
				this.addSchedule(i);
				if(selected[i]) {
					spcpC.get(i).setSelected(true);
					spcpC.get(i).updateUI();
				}
			}
			ois.close();
		}
		
		private void changeDate(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {	//스케줄러 날짜 변경
			for(int i = 0;i<6;i++) {
				for(int j = 0;j<7;j++) {
					if((e.getSource()) == calB[i][j]) {
						c.date = Integer.parseInt(calB[i][j].getText());
						slLD.setText(c.year + "/" + c.month + "/" + c.date);
					}else if(e.getSource() == ttpB){
						slLD.setText(c.thisYear + "/" + c.thisMonth + "/" + c.today);
					}
				}
			}
			
			spcPanel.removeAll();
			spcpC.clear();
			spcpB.clear();
			spcpPanel.clear();
			index = 0;
			spcPanel.updateUI();
			
			if(new File(Integer.toString(c.year)+Integer.toString(c.month)+Integer.toString(c.date)+ ".txt").exists()) {
				this.getSchedule(c.year, c.month, c.date);
				this.saveSchedule(c.year, c.month, c.date);
			}
			this.success();
		}
		
		public void success() throws FileNotFoundException, IOException {	//달성률 메서드
			int check = 0;
			float successRate = 0;
			for(int i = 0;i<spcpC.size();i++) {
				if(spcpC.get(i).isSelected()) {
					check++;
				}
			}
			if(spcpC.size() == 0) {
				srLSR.setText("");
				srLE.setText("입력된 스케줄이 없습니다.");
				srLE.setForeground(Color.black);
			}else {
				successRate = ((float)check/spcpC.size())*100;
				srLSR.setText(String.format("%.2f",successRate) + "%");
				srLSR.updateUI();
				if(successRate==100) {
					srLE.setText("Perfect!!");
					srLE.setForeground(Color.green);
				}else if(successRate>=75) {
					srLE.setText("Good!");
					srLE.setForeground(Color.cyan);
				}else if(successRate>=50) {
					srLE.setText("So So...");
					srLE.setForeground(Color.orange);
				}else if(successRate>=25) {
					srLE.setText("Bad!");
					srLE.setForeground(Color.magenta);
				}else {
					srLE.setText("Terrible!!");
					srLE.setForeground(Color.red);
				}
			}
			srLE.updateUI();
		}
}
