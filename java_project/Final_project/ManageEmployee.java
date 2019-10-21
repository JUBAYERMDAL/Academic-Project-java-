import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class ManageEmployee extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton addEmployeeBtn, viewEmployeeBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	
	public ManageEmployee(String userId)
	{
		super("Manage Employee");
		this.userId = userId;
		this.setSize(600,410);
		this.setLocation(300,150);
		this.setResizable (false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(210, 50, 400, 30);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(450, 55, 120, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		addEmployeeBtn = new JButton("Add Employee");
		addEmployeeBtn.setBounds(200, 150, 170, 55);
		addEmployeeBtn.addActionListener(this);
		panel.add(addEmployeeBtn);
		
		viewEmployeeBtn = new JButton("View Employee");
		viewEmployeeBtn.setBounds(200, 230, 170, 55);
		viewEmployeeBtn.addActionListener(this);
		panel.add(viewEmployeeBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand ();
		
		if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		
		else if (text.equals(backBtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome (userId);
			eh.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals(addEmployeeBtn.getText()))
		{
			AddEmployee ade = new AddEmployee (userId);
			ade.setVisible (true);
			this.setVisible (false);
		}	
		else if (text.equals(viewEmployeeBtn.getText()))
		{
			ViewEmployee ve = new ViewEmployee (userId);
			ve.setVisible (true);
			ve.information();
			this.setVisible (false);
		}
	}
}	