import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class ViewBook extends JFrame implements ActionListener
{
	JLabel welcomeLabel, bookIdLabel, bookNameLabel, authorLabel, yearLabel, availableQuantityLabel,availableBook;
	JTextField bookIdTF, authorTF, bookNameTF, yearTF, availableQuantityTF;
	JButton loadBtn, updateBtn, delBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	String cols[]={"BookId","Book Title","Author Name","Publication Year","AvailableQuantity"};
			
	public ViewBook(String userId)
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
		
		updateBtn = new JButton("Update");
		updateBtn.setBounds(100, 350, 120, 30);
		updateBtn.setEnabled(false);
		updateBtn.addActionListener(this);
		panel.add(updateBtn);
		
		delBtn = new JButton("Delete");
		delBtn.setBounds(250, 350, 120, 30);
		delBtn.setEnabled(false);
		delBtn.addActionListener(this);
		panel.add(delBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		availableBook = new JLabel ("Available Book");
		availableBook.setBounds (250, 380, 200, 50);
		panel.add (availableBook);
		
		table = new JTable ();
		
		model = new DefaultTableModel ();
		model.setColumnIdentifiers (cols);
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
			ManageBook mb = new ManageBook (userId);
			mb.setVisible(true);
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
		else if(text.equals(updateBtn.getText()))
		{
			updateInDB();
			information ();
		}
		else if(text.equals(delBtn.getText()))
		{
			deleteFromDB();
			information ();
		}
		else
		{
			
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
				bookIdTF.setEnabled(false);
				
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
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
	
	
	public void information (){
        String query = "SELECT `bookId`, `bookTitle`, `authorName`, `publicationYear`,`availiableQuantity` FROM `book`";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		
		try{
			Class.forName("com.mysql.jdbc.Driver");//load driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05","root","");
			st = con.createStatement();//create statement
			rs = st.executeQuery(query);//getting result
			int i=0,rowCount=0;
			try {
				rs.last();
				i = rs.getRow();
				rs.beforeFirst();
			} catch (SQLException ex) {
				
			} 
			String columnData[][]=new String[i][5];
			try {
				while(rs.next()){
					columnData [rowCount][0] = rs.getString ("bookID");
					columnData [rowCount][1] = rs.getString ("bookTitle");
					columnData [rowCount][2] = rs.getString ("authorName");
					columnData [rowCount][3] = rs.getString ("publicationYear");
					columnData [rowCount][4] = rs.getString ("availiableQuantity");
					rowCount++;
				}
			} catch (Exception ex) {
				System.out.println("Error!"+ex);
				ex.printStackTrace();
			} finally{
				DefaultTableModel tModel = new DefaultTableModel();
				tModel=(DefaultTableModel)table.getModel();
				tModel.setDataVector(columnData, cols);
			}
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
	}
	
	
	public void updateInDB()
	{
		String newId = bookIdTF.getText();
		String bookName = bookNameTF.getText();
		String authorName = authorTF.getText();
		String publicationYear = yearTF.getText();
		String availableQuantity = availableQuantityTF.getText();
		String query = "UPDATE book SET bookTitle='"+bookName+"', authorName = '"+authorName+"', publicationYear = "+publicationYear+", availiableQuantity = "+availableQuantity+" WHERE bookId='"+newId+"'";	
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
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorTF.setText("");
			yearTF.setText("");
			availableQuantityTF.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void deleteFromDB()
	{
		String newId = bookIdTF.getText();
		String query1 = "DELETE from book WHERE bookId='"+newId+"';";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/c05", "root", "");
			Statement stm = con.createStatement();
			stm.execute(query1);
			stm.close();
			con.close();
			JOptionPane.showMessageDialog(this, "Success !!!");
			
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			bookIdTF.setEnabled(true);
			bookIdTF.setText("");
			bookNameTF.setText("");
			authorTF.setText("");
			yearTF.setText("");
			availableQuantityTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
}