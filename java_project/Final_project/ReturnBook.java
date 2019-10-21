import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class ReturnBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel, bookIdLabel, bookNameLabel, authorLabel, yearLabel, availableQuantityLabel, availableBook,borrowId, borrowBookId, sUserId, borrowDate, returnDate;
	JTextField bookIdTF, authorTF, bookNameTF, yearTF, availableQuantityTF, borrowIdTF, borrowBookIdTF, userIdTF, borrowDateTF, returnDateTF;
	JButton loadBtn, returnBtn, delBtn, backBtn, logoutBtn, searchBtn;
	JPanel panel;
	String userId;
	
	
	
	public ReturnBook(String userId)
	{
		super("Return Book");
		
		this.userId = userId;
		this.setSize(750,450);
		this.setLocation(300,50);
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
		
		searchBtn = new JButton("Find");
		searchBtn.setBounds(640, 100, 80, 30);
		searchBtn.addActionListener(this);
		panel.add(searchBtn);

		bookIdLabel = new JLabel("Book ID : ");
		bookIdLabel.setBounds(50, 100, 100, 30);
		panel.add(bookIdLabel);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(150, 100, 120, 30);
		bookIdTF.setEnabled (false);
		panel.add(bookIdTF);
		
		loadBtn = new JButton("load");
		loadBtn.setBounds(280, 100, 80, 30);
		loadBtn.addActionListener(this);
		loadBtn.setEnabled (false);
		panel.add(loadBtn);
		
		bookNameLabel = new JLabel("Book Name : ");
		bookNameLabel.setBounds(50, 150, 120, 30);
		panel.add(bookNameLabel);
		
		bookNameTF = new JTextField();
		bookNameTF.setBounds(150, 150, 120, 30);
		bookNameTF.setEnabled (false);
		panel.add(bookNameTF);
		
		authorLabel = new JLabel("Author Name : ");
		authorLabel.setBounds(50, 200, 120, 30);
		panel.add(authorLabel);
		
		authorTF = new JTextField();
		authorTF.setBounds(150, 200, 120, 30);
		authorTF.setEnabled (false);
		panel.add(authorTF);
		
		
		yearLabel = new JLabel("Publication Year : ");
		yearLabel.setBounds(50, 250, 120, 30);
		panel.add(yearLabel);
		
		yearTF = new JTextField();
		yearTF.setBounds(150, 250, 120, 30);
		yearTF.setEnabled (false);
		panel.add(yearTF);
		
		availableQuantityLabel = new JLabel("Quantity : ");
		availableQuantityLabel.setBounds(50, 300, 120, 30);
		panel.add(availableQuantityLabel);
		
		availableQuantityTF = new JTextField();
		availableQuantityTF.setBounds(150, 300, 120, 30);
		availableQuantityTF.setEnabled (false);
		panel.add(availableQuantityTF);
		
		returnBtn = new JButton ("Return");
		returnBtn.setBounds (510, 300, 100, 30);
		returnBtn.setEnabled(false);
		returnBtn.addActionListener(this);
		panel.add(returnBtn);
		
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		
		borrowId = new JLabel("Borrow ID : ");
		borrowId.setBounds(400, 100, 100, 30);
		panel.add(borrowId);
		
		borrowIdTF = new JTextField();
		borrowIdTF.setBounds(500, 100, 120, 30);
		panel.add(borrowIdTF);
		
		
		
		
		sUserId = new JLabel("User ID : ");
		sUserId.setBounds(400, 150, 100, 30);
		panel.add(sUserId);
		
		userIdTF = new JTextField();
		userIdTF.setBounds(500, 150, 120, 30);
		userIdTF.setEnabled (false);
		panel.add(userIdTF);
		
		
		borrowDate = new JLabel("Borrow Date : ");
		borrowDate.setBounds(400, 200, 100, 30);
		panel.add(borrowDate);
		
		borrowDateTF = new JTextField();
		borrowDateTF.setBounds(500, 200, 120, 30);
		borrowDateTF.setEnabled (false);
		panel.add(borrowDateTF);
		
		returnDate = new JLabel ("Return Date : ");
		returnDate.setBounds (400, 250, 100, 30);
		panel.add (returnDate);
		
		returnDateTF = new JTextField ();
		returnDateTF.setBounds (500, 250, 120, 30);
		returnDateTF.setEnabled (false);
		panel.add (returnDateTF);
		
		this.add(panel);
	}
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			EmployeeHome eh = new EmployeeHome (userId);
			eh.setVisible(true);
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
			returnBtn.setEnabled (true);
		}
		else if(text.equals(returnBtn.getText()))
		{
			deleteFromDB ();
			updateInDB();
		}
		
		else if (text.equals(searchBtn.getText()))
		{
			searchInDB ();
			loadBtn.setEnabled (true);
		}
	}
	
	public void searchInDB()
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
				
			}
			if(!flag)
			{
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
	
	
	public void loadFromDB()
	{
		String loadId = bookIdTF.getText();
		String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`, `availiableQuantity` FROM `book` WHERE `bookId`='"+loadId+"';";     
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
			String bookName = null;
			String authorName = null;
			int publicationYear = 0;
			int availableQuantity = 0;
			while(rs.next())
			{
                bookName = rs.getString("bookTitle");
				authorName = rs.getString("authorName");
				publicationYear = rs.getInt("publicationYear");
				availableQuantity = rs.getInt("availiableQuantity");
				flag=true;
				
				bookNameTF.setText(bookName);
				authorTF.setText(""+authorName);
				yearTF.setText(""+publicationYear);
				availableQuantityTF.setText(""+availableQuantity);
				
			}
			if(!flag)
			{
				
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
	
	
	public void updateInDB()
	{
		String newId = bookIdTF.getText();
		String bookName = bookNameTF.getText();
		String authorName = authorTF.getText();
		int publicationYear = 0;
		int availableQuantity = 0;
		try
		{
			publicationYear = Integer.parseInt(yearTF.getText());
			availableQuantity = Integer.parseInt(availableQuantityTF.getText())+1;
		}
		catch(Exception e){}
		
		String query = "UPDATE book SET bookTitle='"+bookName+"', authorName = '"+authorName+"', publicationYear = '"+publicationYear+"', availiableQuantity = "+availableQuantity+" WHERE bookId='"+newId+"'";	
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
			
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorTF.setText("");
			yearTF.setText("");
			availableQuantityTF.setText("");
			
			borrowIdTF.setText ("");
			userIdTF.setText ("");
			borrowDateTF.setText ("");
			returnDateTF.setText ("");
			
			returnBtn.setEnabled (false);
			loadBtn.setEnabled (false);
			borrowIdTF.setEnabled(true);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void deleteFromDB()
	{
		String newId = borrowIdTF.getText();
		String query1 = "DELETE from borrowinfo WHERE borrowId='"+newId+"';";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog (this, "Opps !!!");
        }
	}
}