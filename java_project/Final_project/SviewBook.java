import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class SviewBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel, bookIdLabel, bookNameLabel, authorLabel, yearLabel, availableQuantityLabel, availableBook;
	JTextField bookIdTF, authorTF, bookNameTF, yearTF, availableQuantityTF;
	JButton refreshBtn, loadBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	
	String [] columns = {"BookId","Book Title","Author Name","Publication Year","Quantity"};
	String [] rows = new String [5];
	
	public SviewBook(String userId)
	{
		super("View Book");
		
		this.userId = userId;
		this.setSize(680,620);
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
		
		bookIdLabel = new JLabel("Book ID : ");
		bookIdLabel.setBounds(100, 100, 100, 30);
		panel.add(bookIdLabel);
		
		bookIdTF = new JTextField();
		bookIdTF.setBounds(200, 100, 140, 30);
		panel.add(bookIdTF);
		
		loadBtn = new JButton("Search");
		loadBtn.setBounds(400, 100, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		bookNameLabel = new JLabel("Book Name : ");
		bookNameLabel.setBounds(100, 150, 120, 30);
		panel.add(bookNameLabel);
		
		bookNameTF = new JTextField();
		bookNameTF.setBounds(200, 150, 140, 30);
		panel.add(bookNameTF);
		
		authorLabel = new JLabel("Author Name : ");
		authorLabel.setBounds(100, 200, 120, 30);
		panel.add(authorLabel);
		
		authorTF = new JTextField();
		authorTF.setBounds(200, 200, 140, 30);
		panel.add(authorTF);
		
		
		yearLabel = new JLabel("Publication Year : ");
		yearLabel.setBounds(100, 250, 120, 30);
		panel.add(yearLabel);
		
		yearTF = new JTextField();
		yearTF.setBounds(200, 250, 140, 30);
		panel.add(yearTF);
		
		availableQuantityLabel = new JLabel("Quantity : ");
		availableQuantityLabel.setBounds(100, 300, 120, 30);
		panel.add(availableQuantityLabel);
		
		availableQuantityTF = new JTextField();
		availableQuantityTF.setBounds(200, 300, 140, 30);
		panel.add(availableQuantityTF);
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		availableBook = new JLabel ("Available Book");
		availableBook.setBounds (250, 380, 200, 50);
		panel.add (availableBook);
		
		table = new JTable ();
		
		model = new DefaultTableModel ();
		model.setColumnIdentifiers (columns);
		table.setModel (model);
		table.setRowHeight (20);
		
		scroll = new JScrollPane (table);
		scroll.setBounds (10,420,650,150);
		panel.add (scroll);
		
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
		String loadId = bookIdTF.getText();
		String query = "SELECT bookTitle, `authorName`, `publicationYear`, `availiableQuantity` FROM `book` WHERE `bookId`='"+loadId+"';";     
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
				
				bookNameTF.setText(""+bookName);
				authorTF.setText(""+authorName);
				yearTF.setText(""+publicationYear);
				availableQuantityTF.setText(""+availableQuantity);
				
				bookNameTF.setEnabled (false);
				authorTF.setEnabled (false);
				yearTF.setEnabled (false);
				availableQuantityTF.setEnabled (false);
				
			}
			if(!flag)
			{
				bookNameTF.setText("");
				authorTF.setText("");
				yearTF.setText("");
				availableQuantityTF.setText("");
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
	
	
	public void information ()
	{
		
        String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`,`availiableQuantity` FROM `book`";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		PreparedStatement pst = null;
		try
			{
				Class.forName("com.mysql.jdbc.Driver");//load driver
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
				st = con.createStatement();//create statement
				rs = st.executeQuery(query);//getting result
				String []columnData = new String [5];
				while(rs.next())
				{
					columnData [0] = rs.getString ("bookID");
					columnData [1] = rs.getString ("bookTitle");
					columnData [2] = rs.getString ("authorName");
					columnData [3] = rs.getString ("publicationYear");
					columnData [4] = rs.getString ("availiableQuantity");
					
					model.addRow (columnData);
				}
			}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
	}
	
}