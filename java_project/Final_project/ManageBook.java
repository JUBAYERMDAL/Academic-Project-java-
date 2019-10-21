import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class ManageBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton addBookBtn, viewBookBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	
	
	public ManageBook(String userId)
	{
		super("Manage Book");
		
		
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
		
		addBookBtn = new JButton("Add Book");
		addBookBtn.setBounds(200, 150, 170, 55);
		addBookBtn.addActionListener(this);
		panel.add(addBookBtn);
		
		viewBookBtn = new JButton("View Book");
		viewBookBtn.setBounds(200, 230, 170, 55);
		viewBookBtn.addActionListener(this);
		panel.add(viewBookBtn);
		
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
		else if (text.equals(addBookBtn.getText()))
		{
			AddBook adb = new AddBook(userId);
			adb.setVisible (true);
			this.setVisible (false);
		}	
		else if (text.equals(viewBookBtn.getText()))
		{
			ViewBook vb = new ViewBook (userId);
			vb.setVisible (true);
			vb.information();
			this.setVisible (false);
		}
	}
}	