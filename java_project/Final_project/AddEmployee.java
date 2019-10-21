import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.io.*;

public class AddEmployee extends JFrame implements ActionListener
{
	JLabel welcomeLabel,userLabel, passLabel, eNameLabel, phoneLabel, roleLabel, salaryLabel;
	JTextField userTF, passTF, phoneTF2, eNameTF, salaryTF;
	JComboBox roleCombo;
	JButton addBtn, backBtn, logoutBtn;
	JPanel panel;
	
	String userId;
	
	public AddEmployee(String userId)
	{
		super("Add Employee");
		this.userId = userId;
		this.setSize(600,450);
		this.setLocation(300,150);
		this.setResizable (false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		panel = new JPanel();
		panel.setLayout(null);
		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(210, 50, 400, 30);
		panel.add(welcomeLabel);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(450, 55, 120, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(100, 80, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(200, 80, 120, 30);
		panel.add(userTF);
		
		passLabel = new JLabel("Password : ");
		passLabel.setBounds(100, 130, 120, 30);
		panel.add(passLabel);
		
		passTF = new JTextField();
		passTF.setBounds(200, 130, 120, 30);
		panel.add(passTF);
		
		eNameLabel = new JLabel("Employee Name : ");
		eNameLabel.setBounds(100, 180, 120, 30);
		panel.add(eNameLabel);
		
		eNameTF = new JTextField();
		eNameTF.setBounds(200, 180, 120, 30);
		panel.add(eNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(100, 230, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(200, 230, 85, 30);
		panel.add(phoneTF2);
		
		roleLabel = new JLabel("Role : ");
		roleLabel.setBounds(100, 280, 120, 30);
		panel.add(roleLabel);
		
		String []items = {"manager", "employee"};
		roleCombo = new JComboBox(items);
		roleCombo.setBounds(200, 280, 120, 30);
		panel.add(roleCombo);
		
		salaryLabel = new JLabel("Salary : ");
		salaryLabel.setBounds(100, 330, 120, 30);
		panel.add(salaryLabel);
		
		salaryTF = new JTextField();
		salaryTF.setBounds(200, 330, 120, 30);
		panel.add(salaryTF);
		
		addBtn = new JButton("Add");
		addBtn.setBounds(250, 380, 100, 30);
		addBtn.addActionListener(this);
		panel.add(addBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			ManageEmployee me = new ManageEmployee(userId);
			me.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(addBtn.getText()))
		{
			insertIntoDB();
			
		}
		else
		{
			
		}
	}
	public void insertIntoDB()
	{
		String newId = userTF.getText();
		String newPass = passTF.getText();
		String eName = eNameTF.getText();
		String phnNo =phoneTF2.getText();
		String role = roleCombo.getSelectedItem().toString();
		double salary = Double.parseDouble(salaryTF.getText());
		int status = 0;
		
		
		String query1 = "INSERT INTO Employee VALUES ('"+newId+"','"+eName+"',"+ phnNo+",'"+role+"',"+salary+");";
		String query2 = "INSERT INTO Login VALUES ('"+newId+"','"+newPass+"',"+status+");";
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
			
			userTF.setText ("");
			passTF.setText ("");
			eNameTF.setText ("");
			phoneTF2.setText ("");
			salaryTF.setText ("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "User Name Already Exist !!!");
        }
    }	
}