import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class EmployeeHome extends JFrame implements ActionListener
{
	JLabel welcomeLabel, ebackGround;
	JButton manageEmployeeBtn, changePasswordBtn, viewDetailsBtn, logoutBtn, studentDetailsBtn, bookManageBtn, borrowRequestBtn, returnBookBtn;
	JPanel panel;
	String userId;
	
	public EmployeeHome(String userId)
	{
		super("Employee Home");
		
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
		
		manageEmployeeBtn = new JButton("Manage Employee");
		manageEmployeeBtn.setBounds(30, 90, 170, 35);
		manageEmployeeBtn.addActionListener(this);
		panel.add(manageEmployeeBtn);
		
		bookManageBtn = new JButton ("Manage Book");
		bookManageBtn.setBounds (30, 130, 170, 35);
		bookManageBtn.addActionListener (this);
		panel.add (bookManageBtn);
		
		studentDetailsBtn = new JButton ("Student Details");
		studentDetailsBtn.setBounds (30, 170, 170, 35);
		studentDetailsBtn.addActionListener (this);
		panel.add (studentDetailsBtn);
		
		
		viewDetailsBtn = new JButton("My Information");
		viewDetailsBtn.setBounds(30, 210, 170, 35);
		viewDetailsBtn.addActionListener(this);
		panel.add(viewDetailsBtn);
		
		borrowRequestBtn = new JButton ("Issue Book");
		borrowRequestBtn.setBounds (30, 250, 170, 35);
		borrowRequestBtn.addActionListener (this);
		panel.add (borrowRequestBtn);
		
		returnBookBtn = new JButton ("Return Book");
		returnBookBtn.setBounds (30, 290, 170, 35);
		returnBookBtn.addActionListener(this);
		panel.add (returnBookBtn);
		
		changePasswordBtn = new JButton("Change Password");
		changePasswordBtn.setBounds(30, 330, 170, 35);
		changePasswordBtn.addActionListener(this);
		panel.add(changePasswordBtn);
		
		
		this.add(panel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String text = ae.getActionCommand ();
		
		if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		else if (text.equals(manageEmployeeBtn.getText()))
		{
			if(checkRole().equals("manager"))
			{
				ManageEmployee me = new ManageEmployee(userId);
				me.setVisible(true);
				this.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Access Denied");
			}
		}
		else if (text.equals(bookManageBtn.getText()))
		{
			ManageBook mb = new ManageBook (userId);
			mb.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals(viewDetailsBtn.getText()))
		{
			EmployeeInformation ei = new EmployeeInformation(userId);
			ei.setVisible(true);
			ei.information();
			this.setVisible(false);
			
		}
		else if (text.equals(changePasswordBtn.getText()))
		{
			ChangePassword cp = new ChangePassword (userId,"employee");
			cp.setVisible (true);
			this.setVisible (false);
		}
		else if (text.equals(studentDetailsBtn.getText()))
		{
			StudentDetail sd = new StudentDetail(userId);
			sd.setVisible (true);
			sd.information ();
			this.setVisible (false);
 		}
		else if (text.equals(borrowRequestBtn.getText()))
		{
			IssueBook ib = new IssueBook (userId);
			ib.setVisible (true);
			ib.information ();
			this.setVisible(false);
		}
		
		else if (text.equals (returnBookBtn.getText()))
		{
			ReturnBook rb = new ReturnBook (userId);
			rb.setVisible (true);
			this.setVisible (false);
		}
		else
		{
			
		}
		
	}
	public String checkRole(){
		String loadId = userId;
		String query = "SELECT `role` FROM `employee` WHERE `userId`='"+loadId+"';";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		String role = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
			st = con.createStatement();//create statement
			rs = st.executeQuery(query);//getting result
			
			boolean flag = false;
			while(rs.next()){
                role = rs.getString("role");
				flag=true;
			}
			if(!flag){
				JOptionPane.showMessageDialog(this,"Invalid ID"); 
			}
			
		}
        catch(Exception ex){
			System.out.println("Exception : " +ex.getMessage());
        }
        finally{
            try{
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
		return role;
	}
}