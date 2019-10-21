import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class StudentChangePassword extends JFrame implements ActionListener

{
	JLabel welcomeLabel, oldPassLabel, newPassLabel, confirmPassLabel;
	JPasswordField oldPassTF, newPassTF, confirmPassTF;
	JButton changePasswordBtn, logoutBtn, backBtn;
	JPanel panel;
	
	String userId;
	
	public StudentChangePassword (String userId)
	{
		super ("Password Change");
		this.userId = userId;
		this.setSize(600,415);
		this.setLocation(300,150);
		this.setResizable (false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.userId = userId;
		
		
		panel = new JPanel();
		panel.setLayout(null);

		
		welcomeLabel = new JLabel("Welcome, "+userId);
		welcomeLabel.setBounds(410, 40, 400, 30);
		panel.add(welcomeLabel);
		
		oldPassLabel = new JLabel ("Old Password : ");
		oldPassLabel.setBounds (150, 140, 120, 30);
		panel.add(oldPassLabel);
		
		oldPassTF = new JPasswordField ();
		oldPassTF.setBounds(300, 140, 120, 30);
		panel.add(oldPassTF);
		
		newPassLabel = new JLabel ("New Password : ");
		newPassLabel.setBounds(150, 190, 120, 30);
		panel.add(newPassLabel);
		
		newPassTF = new JPasswordField ();
		newPassTF.setBounds(300, 190, 120, 30);
		panel.add(newPassTF);
		
		confirmPassLabel = new JLabel ("Confirm Password : ");
		confirmPassLabel.setBounds(150, 240, 120, 30);
		panel.add(confirmPassLabel);
		
		confirmPassTF = new JPasswordField ();
		confirmPassTF.setBounds(300, 240, 120, 30);
		panel.add(confirmPassTF);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		logoutBtn = new JButton("logout");
		logoutBtn.setBounds(550, 45, 24, 24);
		logoutBtn.addActionListener(this);
		panel.add(logoutBtn);
		
		changePasswordBtn = new JButton ("Change");
		changePasswordBtn.setBounds (250, 290, 100, 30);
		changePasswordBtn.addActionListener(this);
		panel.add (changePasswordBtn);
		
		this.add(panel);
		
	}
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand();

		if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(backBtn.getText()))
		{
			
			StudentHome sh = new StudentHome (userId);
			sh.setVisible (true);
			this.setVisible (false);
			
		}
		else if(text.equals(changePasswordBtn.getText()))
		{
			checkPassword();
		}
	}
	
	public void checkPassword()
	{
		
		String query = "SELECT  `password` FROM `login` WHERE `userId`='"+userId+"';";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3","root","");
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			boolean flag = false;			
			while(rs.next())
			{

				String password = rs.getString("password");
                String loadPass = oldPassTF.getText();
                String newpass = newPassTF.getText();
                String confirmpass = confirmPassTF.getText(); 
				
				if(password.equals(oldPassTF.getText()) && confirmpass.equals(newPassTF.getText()))
				{

					flag= true;
					String query1 = "UPDATE login SET  password ='"+newpass+"' WHERE userId='"+userId+"'";
					System.out.println(query1);

					try{

				Class.forName("com.mysql.jdbc.Driver");//load driver
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/m3","root","");
				st = con.createStatement();//create statement
				st.executeUpdate(query1);
				st.close();
				con.close();
				JOptionPane.showMessageDialog(this, "Success !!!");
				oldPassTF.setText("");
				newPassTF.setText("");
				confirmPassTF.setText("");
					}
					catch(Exception e)
							{
								System.out.println(e.getMessage());
								JOptionPane.showMessageDialog(this, "Oops !!!");
							}
				}
			}
			if(!flag)
			{
				oldPassTF.setText("");
				newPassTF.setText("");
				confirmPassTF.setText("");
				JOptionPane.showMessageDialog(this,"Invalid Password"); 
			}
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
        finally
		{
            try
			{
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
	}
}