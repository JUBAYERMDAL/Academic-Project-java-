import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class StudentHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel;
	JButton viewBookBtn, changePasswordBtn, viewDetailsBtn, logoutBtn, deleteAccountBtn, borrowBookBtn;
	JPanel panel;
	String userId;
	
	
	public 	StudentHome(String userId)
	{
		super("Student Home");
		
		
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
		
		viewBookBtn = new JButton("View Book");
		viewBookBtn.setBounds(30, 90, 170, 35);
		viewBookBtn.addActionListener(this);
		panel.add(viewBookBtn);
		
		borrowBookBtn = new JButton ("Borrow Info");
		borrowBookBtn.setBounds (30, 130, 170, 35);
		borrowBookBtn.addActionListener (this);
		panel.add (borrowBookBtn);
		
		viewDetailsBtn = new JButton ("My Information");
		viewDetailsBtn.setBounds (30, 170, 170, 35);
		viewDetailsBtn.addActionListener (this);
		panel.add (viewDetailsBtn);
		
		
		changePasswordBtn = new JButton("Change Password");
		changePasswordBtn.setBounds(30, 210, 170, 35);
		changePasswordBtn.addActionListener(this);
		panel.add(changePasswordBtn);
		
		deleteAccountBtn = new JButton("Delete Account");
		deleteAccountBtn.setBounds(30, 250, 170, 35);
		deleteAccountBtn.addActionListener(this);
		panel.add(deleteAccountBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand ();
		
		if (text.equals(deleteAccountBtn.getText()))
		{
			
			int yesOrNo = JOptionPane.showConfirmDialog(null, "Are You Sure ??", "Confirm", JOptionPane.YES_NO_OPTION);
			if (yesOrNo == 0)
			{
				deleteFromDB();
				HomeWindow hw = new HomeWindow();
			    hw.setVisible(true);
			    this.setVisible(false);
			}
			else 
			{
				this.setVisible (true);
			}
		
			
		}
		else if (text.equals (logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		
		else if (text.equals(borrowBookBtn.getText()))
		{
			BorrowInfo bi = new BorrowInfo (userId);
		    bi.setVisible (true);
			this.setVisible(false);
		}
			
		
		else if (text.equals(viewDetailsBtn.getText()))
		{
			StudentInformation si = new StudentInformation (userId);
			si.setVisible (true);
			si.information ();
			this.setVisible (false);
			
		}
		else if (text.equals(changePasswordBtn.getText()))
		{
			ChangePassword scp = new ChangePassword (userId,"student");
			scp.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals (viewBookBtn.getText()))
		{
			SviewBook svb = new SviewBook (userId);
			svb.setVisible(true);
			svb.information ();
			this.setVisible (false);
		}
		
	}

	public void deleteFromDB()
	{
		String query1 = "DELETE from Customer WHERE userId='"+userId+"';";
		String query2 = "DELETE from login WHERE userId='"+userId+"';";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.execute(query2);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}