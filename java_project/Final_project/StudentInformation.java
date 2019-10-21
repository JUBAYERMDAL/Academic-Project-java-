import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;


public class StudentInformation extends JFrame implements ActionListener
{
	JLabel welcomeLabel, userLabel, sNameLabel, phoneLabel, addressLabel;
	JTextField userTF, phoneTF, sNameTF, addressTF;
	JButton backBtn, logoutBtn, updateBtn, confirmBtn;
	JPanel panel;
	String userId;
	

	public StudentInformation(String userId)
	{
		super("My Information");
		this.userId = userId;
		this.setSize(600,415);
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
		userLabel.setBounds(150, 90, 120, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(250, 90, 120, 30);
		userTF.setEditable (false);
		panel.add(userTF);
		
		
		
		sNameLabel = new JLabel("Student Name : ");
		sNameLabel.setBounds(150, 140, 120, 30);
		panel.add(sNameLabel);
		
		sNameTF = new JTextField();
		sNameTF.setBounds(250, 140, 120, 30);
		sNameTF.setEditable (false);
		panel.add(sNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(150, 190, 120, 30);
		panel.add(phoneLabel);
		
		
		phoneTF = new JTextField();
		phoneTF.setBounds(250, 190, 120, 30);
		phoneTF.setEditable(false);
		panel.add(phoneTF);
		
		addressLabel = new JLabel("Address : ");
		addressLabel.setBounds(150, 240, 120, 30);
		panel.add(addressLabel);
		
		
		addressTF = new JTextField();
		addressTF.setBounds(250, 240, 120, 30);
		addressTF.setEditable(false);
		panel.add(addressTF);
		
		updateBtn = new JButton ("Update");
		updateBtn.setBounds (180, 300, 100, 30);
		updateBtn.addActionListener (this);
		panel.add (updateBtn);
		
		confirmBtn = new JButton ("Confirm");
		confirmBtn.setBounds (300, 300, 100,30);
		confirmBtn.setEnabled (false);
		confirmBtn.addActionListener (this);
		panel.add (confirmBtn);
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);

		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
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
			StudentHome sh = new StudentHome(userId);
			sh.setVisible(true);
			this.setVisible(false);
		}
		else if (text.equals(updateBtn.getText()))
		{
			sNameTF.setEditable (true);
			phoneTF.setEditable(true);
			addressTF.setEditable(true);
			confirmBtn.setEnabled (true);
			updateBtn.setEnabled (false);
		}
		
		else if (text.equals (confirmBtn.getText()))
		{
			updateInDB ();
			updateBtn.setEnabled (true);
			sNameTF.setEditable (false);
			phoneTF.setEditable(false);
			addressTF.setEditable(false);
			confirmBtn.setEnabled (false);
			
		}
	}
	
	public void updateInDB()
	{
		
		String sName = sNameTF.getText();
		String phnNo = phoneTF.getText();
		String address = addressTF.getText();
		
		String query = "UPDATE customer SET customerName ='"+sName+"', phoneNumber = '"+phnNo+"', address = '"+address+"' WHERE userId='"+userId+"'";	
        Connection con=null;//for connection
        Statement st = null;//for query execution
		try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
			st = con.createStatement();//create statement
			st.executeUpdate(query);
			st.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void information ()
	{
        String query = "SELECT `userId`, `customerName`, `phoneNumber`, `address` FROM `customer`WHERE `userId`='"+userId+"';";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
			st = con.createStatement();//create statement
			rs = st.executeQuery(query);//getting result
			while(rs.next())
			{
				String userId = rs.getString("userID");
				String customerNamer = rs.getString("CustomerName");
				String phoneNumber = rs.getString("PhoneNumber");
				String address = rs.getString("Address");
				userTF.setText(""+userId);
				phoneTF.setText(""+phoneNumber);
				sNameTF.setText(""+customerNamer);
				addressTF.setText(""+address);
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