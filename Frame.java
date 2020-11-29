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
	
	//���� �����
	private void makeTitle() {
				ttPanel = new JPanel();	//topTitlePanel ��ü ���� �г�
				ttpL = new JLabel("Scheduler");	//topTitlePanelLabel ��ü ���� �г� ��
				ttpL.setFont(new Font(ttpL.getFont().getName(),ttpL.getFont().getStyle(),50));
				ttpL.setForeground(Color.magenta);
				ttpB = new JButton("TODAY");	//topTitlePanelButton ��ü ���� �г� ��ư
				ttpB.setBackground(Color.pink);
				ttpB.addActionListener(this);
				ttPanel.add(ttpL);
				ttPanel.add(ttpB);
	}
	
	//�޷� �� �����
	private void makeCalendarLabel() {
		clPanel = new JPanel(new FlowLayout());	//calendarLabelPanel �޷� �� �г�
		clpL = new JLabel(c.thisYear + "��" + c.thisMonth + "��");	//calendarLabelPanelLabel �޷� �� �г� ��
		clpL.setFont(new Font(clpL.getFont().getName(),clpL.getFont().getStyle(),30));
		clpBL = new JButton("<");	//calendarLabelPanelButtonLeft �޷� �� �г� ��ư ����
		clpBR = new JButton(">");	//calendarLabelPanelButtonRight �޷� �� �г� ��ư ������
		clpBL.setBackground(Color.pink);
		clpBR.setBackground(Color.pink);
		clpBL.addActionListener(this);
		clpBR.addActionListener(this);
		clPanel.add(clpBL);
		clPanel.add(clpL);
		clPanel.add(clpBR);
		ctPanel.add(clPanel,BorderLayout.NORTH);
	}
	
	//�޷� �����
	private void makeCalendar(int year, int month) {
		c = new calendar(year,month,c.today);
		cPanel = new JPanel(new GridLayout(0,7,3,3));	//calendarPanel �޷� �г�
		calP = new JPanel[7][7];	//calPanel �޷� ���� �г�
		calLD = new JLabel[7];	//calLabelDay	�޷� ���� �� ����
		calB = new JButton[6][7];	//calButton �޷� ���� ��ư
		calL = new JLabel[6][7];	//calendarLabel �޷� ���� ��
		
		for(int i = 0;i<7;i++) {	//���� ����
			calP[0][i] = new JPanel(new BorderLayout());
			calP[0][i].setBackground(Color.white);
			calLD[i] = new JLabel(c.day[i]);
			calLD[i].setHorizontalAlignment(JLabel.CENTER);
			calLD[i].setFont(new Font(calLD[i].getFont().getName(),calLD[i].getFont().getStyle(),20));
			if(calLD[i].getText() == "��") {
				calLD[i].setForeground(Color.red);
			}
			calP[0][i].setPreferredSize(new Dimension(60,80));
			calP[0][i].add(calLD[i],BorderLayout.CENTER);
			cPanel.add(calP[0][i]);
		}
		
		for(int rows = 1;rows<7;rows++) {	//��¥ ����
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
						calL[rows-1][cols].setText("����");
						break;
					case "31" :
						calL[rows-1][cols].setText("������");
						break;
					case "55" :
						calL[rows-1][cols].setText("��̳�");
						break;
					case "66" :
						calL[rows-1][cols].setText("������");
						break;
					case "815" :
						calL[rows-1][cols].setText("������");
						break;
					case "103" :
						calL[rows-1][cols].setText("��õ��");
						break;
					case "109" :
						calL[rows-1][cols].setText("�ѱ۳�");
						break;
					case "1225" :
						calL[rows-1][cols].setText("��ź��");
						break;
					}
					calB[rows-1][cols].setForeground(Color.red);
					calP[rows][cols].add(calL[rows-1][cols],BorderLayout.CENTER);
				}else if(c.isAnniversary(sMonth,sDate)) {
					switch(c.avs) {
					case "45" :
						calL[rows-1][cols].setText("�ĸ���");
						break;
					case "58" :
						calL[rows-1][cols].setText("����̳�");
						break;
					case "515" :
						calL[rows-1][cols].setText("�����ǳ�");
						break;
					case "717" :
						calL[rows-1][cols].setText("������");
						break;
					}
					calP[rows][cols].add(calL[rows-1][cols],BorderLayout.CENTER);
				}
			}
		}
		ctPanel.add(cPanel,BorderLayout.CENTER);
	}
	
	//�޷� �ؽ�Ʈ �ʵ� �����
	private void makeCalendarTextField() {
		ctfPanel = new JPanel();	//calendarTextFieldPanel �޷� �ؽ�Ʈ �ʵ� �г�
		ctfpTY = new JTextField(4);	//calendarTextFieldPanelTextYear �޷� �ؽ�Ʈ �ʵ� �г� �ؽ�Ʈ ����
		ctfpLY = new JLabel("��");	//calendarTextFieldPanelLabelYear �޷� �ؽ�Ʈ �ʵ� �г� �� ����
		ctfpLY.setFont(new Font(ctfpLY.getFont().getName(),ctfpLY.getFont().getStyle(),20));
		ctfpTM = new JTextField(4);	//calendarTextFieldPanelTextMonth �޷� �ؽ�Ʈ �ʵ� �г� �ؽ�Ʈ ��
		ctfpLM = new JLabel("��");	//calendarTextFieldPanelLabelMonth �޷� �ؽ�Ʈ �ʵ� �г� �� ��
		ctfpLM.setFont(new Font(ctfpLM.getFont().getName(),ctfpLM.getFont().getStyle(),20));
		ctfpB = new JButton("�̵�");	//calendarTextFieldPanelButton �޷� �ؽ�Ʈ �ʵ� �г� ��ư
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
		sptpB = new JButton("�Է�");	//schedulerPanelTextPanelButton
		sptpB.setBackground(Color.pink);
		sptpB.addActionListener(this);
		sptPanel.add(sptpTF);
		sptPanel.add(sptpB);
		
		spcPanel = new JPanel();
		spcPanel.setLayout(new BoxLayout(spcPanel,BoxLayout.Y_AXIS));
		
		spbPanel = new JPanel();	//schedulerPanelButtonPanel
		spbpB = new JButton("����");	//schedulerPanelButton
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
	
	
	//Frame ������
	public Frame() throws FileNotFoundException, ClassNotFoundException, IOException {	
		this.setSize(1000,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Scheduler");
		
		tPanel = new JPanel(new BorderLayout());	//topPanel ��ü �г�
		this.makeTitle();
		
		//�޷�
		ctPanel = new JPanel(new BorderLayout());	//calendarTopPanel �޷� ��ü �г�
		this.makeCalendarLabel();
		this.makeCalendar(c.thisYear,c.thisMonth);
		this.makeCalendarTextField();
		
		//�����췯
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
	
	//�̺�Ʈ ó��
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == clpBL) {	//���� �޷� �̵�
			this.lastMonth();
		}else if(e.getSource() == clpBR) {	//���� �޷� �̵�
			this.nextMonth();
		}else if(e.getSource() == ctfpB ) {	//�Է��� ����,���� �̵�
			this.move();
		}else if(e.getSource() == sptpB) {	//������ �߰�
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
		}else if(spcpB.contains(e.getSource())) {	//������ ����
			int ClikedIndex = spcpB.indexOf(e.getSource());
			try {
				this.deleteSchedule(ClikedIndex);
				this.success();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource() == spbpB) {	//������ ����
			try {
				this.saveSchedule(c.year, c.month, c.date);
				this.success();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource() == ttpB) {	//���÷� �̵�
			try {
				this.Today(e);
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
		}else {	//�����ٷ� ��¥ �̵�
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
	
	
	//�̺�Ʈ ó�� �޼���
		private void lastMonth() {	//���� �޷� �̵�
			ctPanel.remove(cPanel);
			if(c.month>1 && c.month<=12) {
				this.makeCalendar(c.year,c.month-1);
			}else {
				this.makeCalendar(c.year-1, 12);
			}
			ctPanel.updateUI();
			clpL.setText(c.year + "��" + c.month + "��");
		}
		
		private void nextMonth() {	//���� �޷� �̵�
			ctPanel.remove(cPanel);
			if(c.month>=1 && c.month<12) {
				this.makeCalendar(c.year,c.month+1);
			}else {
				this.makeCalendar(c.year+1, 1);
			}
			ctPanel.updateUI();
			clpL.setText(c.year + "��" + c.month + "��");
		}
		
		private void move() {	//��,�� �̵�
			ctPanel.remove(cPanel);
			this.makeCalendar(Integer.parseInt(ctfpTY.getText()), Integer.parseInt(ctfpTM.getText()));
			ctPanel.updateUI();
			clpL.setText(c.year + "��" + c.month + "��");
			ctfpTY.setText("");
			ctfpTY.updateUI();
			ctfpTM.setText("");
			ctfpTM.updateUI();
		}
		
		private void Today(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
			ctPanel.remove(cPanel);
			this.makeCalendar(c.thisYear, c.thisMonth);
			ctPanel.updateUI();
			clpL.setText(c.year + "��" + c.month + "��");
			this.changeDate(e);
			
		}
	
		private void addSchedule(int index) throws FileNotFoundException, IOException {	//������ �߰�	
			spcpPanel.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
			spcpPanel.get(index).setMaximumSize(new Dimension(400,50));
			spcpC.add(new JCheckBox(sptpTF.getText()));
			spcpC.get(index).setFont(new Font(spcpC.get(index).getFont().getName(),spcpC.get(index).getFont().getStyle(),20));
			spcpB.add(new JButton("����"));
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
		
		private void deleteSchedule(int ClikedIndex) throws FileNotFoundException, IOException {	//������ ����
			spcPanel.remove(spcpPanel.get(ClikedIndex));
			spcpC.remove(ClikedIndex);
			spcpB.remove(ClikedIndex);
			spcpPanel.remove(ClikedIndex);
			spcPanel.updateUI();
			index--;
			this.saveSchedule(c.year, c.month, c.date);
		}
		
		private void saveSchedule(int year, int month ,int date) throws FileNotFoundException, IOException {	//������ ����
			oos = new ObjectOutputStream(new FileOutputStream(Integer.toString(year)+Integer.toString(month)+Integer.toString(date)+ ".txt"));
			oos.writeObject(spcpC);
			for(int i = 0;i<spcpC.size();i++) {
				oos.writeBoolean(spcpC.get(i).isSelected());
			}
			oos.close();
		}
		
		private void getSchedule(int year, int month, int date) throws FileNotFoundException, IOException, ClassNotFoundException {	//����� ������ �ҷ�����
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
		
		private void changeDate(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {	//�����ٷ� ��¥ ����
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
		
		public void success() throws FileNotFoundException, IOException {	//�޼��� �޼���
			int check = 0;
			float successRate = 0;
			for(int i = 0;i<spcpC.size();i++) {
				if(spcpC.get(i).isSelected()) {
					check++;
				}
			}
			if(spcpC.size() == 0) {
				srLSR.setText("");
				srLE.setText("�Էµ� �������� �����ϴ�.");
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
