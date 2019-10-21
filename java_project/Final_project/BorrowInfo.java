import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class BorrowInfo extends JFrame implements ActionListener
{
	JLabel welcomeLabel, borrowIdLabel, bookIdLabel, userIdLabel, borrowDateLabel, returnDateLabel;
	JTextField bookIdTF, borrowIdTF, userIdTF, borrowDateTF, returnDateTF;
	JButton loadBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	JTable table;
	
	
	
	public BorrowInfo(String userId)
	{
		super("Borrow Info");
		
		this.userId = userId;
		this.setSize(600,450);
		this.setLocation(300,100);
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
		
		borrowIdLabel = new JLabel("Borrow ID : ");
		borrowIdLabel.setBounds(100, 100, 100, 30);
		panel.add(borrowIdLabel);
		
		borrowIdTF = new JTextField();
		borrowIdTF.setBounds(200, 100, 140, 30);
		panel.add(borrowIdTF);
		
		loadBtn = new JButton("Search");
		loadBtn.setBounds(400, 100, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		bookIdLabel = new JLabel("Book ID : ");
		bookIdLabel.setBounds(100, 150, 120, 30);
		panel.add(bookIdLabel);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(200, 150, 140, 30);
		panel.add(bookIdTF);
		
		userIdLabel = new JLabel("User ID: ");
		userIdLabel.setBounds(100, 200, 120, 30);
		panel.add(userIdLabel);
		
		userIdTF = new JTextField();
		userIdTF.setBounds(200, 200, 140, 30);
		panel.add(userIdTF);
		
		
		borrowDateLabel = new JLabel("Borrow Date : ");
		borrowDateLabel.setBounds(100, 250, 120, 30);
		panel.add(borrowDateLabel);
		
		borrowDateTF = new JTextField();
		borrowDateTF.setBounds(200, 250, 140, 30);
		panel.add(borrowDateTF);
		
		returnDateLabel = new JLabel("Return Date : ");
		returnDateLabel.setBounds(100, 300, 120, 30);
		panel.add(returnDateLabel);
		
		returnDateTF = new JTextField();
		returnDateTF.setBounds(200, 300, 140, 30);
		panel.add(returnDateTF);
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		this.add(panel);
	}
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			StudentHome sh = new StudentHome (userId);
			sh.setVisible(true);
			this.setVisible(false);
		}
		else if(text.equals(logoutBtn.getText()))
		{
			HomeWindow hw = new HomeWindow();
			hw.setVisible(true);
			this.setVisible(false);
		}
		
		else if(text.equals(loadBtn.getText()))
		{
			loadFromDB();			
		}
		
	}
	
	public void loadFromDB()
	{
		String loadId = borrowIdTF.getText();
		String query = "SELECT `borrowId`, `bookId`, `userId`, `borrowDate`, `returnDate` FROM `borrowinfo` WHERE `borrowId`='"+loadId+"';";     
        Connection con = null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		try
		{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
			st = con.createStatement();//create statement
			rs = st.executeQuery(query);//getting result
			
			boolean flag = false;
			String bookId = null;
			String userId = null;
			String borrowDate = null;
			String returnDate = null;
			while(rs.next())
			{
                bookId = rs.getString("bookId");
				userId = rs.getString("userId");
				borrowDate = rs.getString("borrowDate");
				returnDate = rs.getString ("returnDate");
				
				
				flag=true;
				
				bookIdTF.setText(""+bookId);
				userIdTF.setText(""+userId);
				borrowDateTF.setText(""+borrowDate);
				returnDateTF.setText(""+returnDate);
				borrowIdTF.setEnabled(false);
				
				bookIdTF.setEnabled (false);
				userIdTF.setEnabled (false);
				borrowDateTF.setEnabled (false);
				returnDateTF.setEnabled (false);
				
			}
			if(!flag)
			{
				
				bookIdTF.setText("");
				userIdTF.setText("");
				borrowDateTF.setText("");
				returnDateTF.setText("");
				JOptionPane.showMessageDialog(this,"Invalid ID"); 
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