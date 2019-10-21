import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class ChangePassword extends JFrame implements ActionListener

{
	JLabel welcomeLabel, oldPassLabel, newPassLabel, confirmPassLabel;
	JPasswordField oldPassTF, newPassTF, confirmPassTF;
	JButton changePasswordBtn, logoutBtn, backBtn;
	JPanel panel;
	
	String userId,user;
	
	public ChangePassword (String userId,String user){
		super ("Password Change");
		this.userId = userId;
		this.user=user;
		this.setSize(600,415);
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
			if(user=="student"){
				StudentHome sh = new StudentHome (userId);
				sh.setVisible (true);
				this.setVisible (false);				
			}
			else{
				EmployeeHome sh = new EmployeeHome (userId);
				sh.setVisible (true);
				this.setVisible (false);
			}
			
		}
		else if(text.equals(changePasswordBtn.getText()))
		{
			String o= oldPassTF.getText();
			if(checkPass().equals(o)){
				String n1=newPassTF.getText(),n2=confirmPassTF.getText();
				if(n1.equals(n2)){
					
					String query = "UPDATE login SET password='"+n1+"' WHERE userId='"+userId+"';";	
				
					Connection con=null;//for connection
					Statement st = null;//for query execution
					try{
						Class.forName("com.mysql.jdbc.Driver");//load driver
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
						st = con.createStatement();//create statement
						st.executeUpdate(query);
						st.close();
						con.close();
						JOptionPane.showMessageDialog(this, "Success !!!");
						if(user.equals("employee")){
							EmployeeHome eh = new EmployeeHome(userId);
							eh.setVisible(true);
							this.setVisible(false);
						}else {
							StudentHome ch = new StudentHome(userId);
							ch.setVisible(true);
							this.setVisible(false);
						}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						JOptionPane.showMessageDialog(this, "Oops !!!");
					}										
				}
				else{
					JOptionPane.showMessageDialog(this, "New Passwords Dosen't Match !!!");
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "Old Password Dosen't Match !!!");
			}
		}
	}
	
	public String checkPass(){
		String query = "SELECT password FROM login WHERE `userId`='"+userId+"';";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		String pass = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
			st = con.createStatement();//create statement
			rs = st.executeQuery(query);//getting result
			
			boolean flag = false;
			while(rs.next()){
                pass = rs.getString("password");
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
		return pass;
	}
}