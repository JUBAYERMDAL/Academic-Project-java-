import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class StudentDetail extends JFrame implements ActionListener
{
	JLabel welcomeLabel, userIdLabel, studentNameLabel, phoneLabel, addressLabel, studentsInfo;
	JTextField userIdTF, studentNameTF, phoneTF, addressTF;
	JButton loadBtn, backBtn, logoutBtn;
	JPanel panel;
	String userId;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	
	String [] cols = {"Student Id","Student Name","PhoneNo","Address"};
	
	public StudentDetail(String userId)
	{
		super("Student Details");
		
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
		
		userIdLabel = new JLabel("Student ID : ");
		userIdLabel.setBounds(100, 100, 100, 30);
		panel.add(userIdLabel);
		
		userIdTF = new JTextField();
		userIdTF.setBounds(200, 100, 160, 30);
		panel.add(userIdTF);
		
		loadBtn = new JButton("Search");
		loadBtn.setBounds(400, 100, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		studentNameLabel = new JLabel("Student Name : ");
		studentNameLabel.setBounds(100, 150, 120, 30);
		panel.add(studentNameLabel);
		
		studentNameTF = new JTextField();
		studentNameTF.setBounds(200, 150, 160, 30);
		panel.add(studentNameTF);
		
		phoneLabel = new JLabel("PhoneNo. : ");
		phoneLabel.setBounds(100, 200, 120, 30);
		panel.add(phoneLabel);
		
		phoneTF = new JTextField();
		phoneTF.setBounds(200, 200, 160, 30);
		panel.add(phoneTF);
		
		
		addressLabel = new JLabel("Address : ");
		addressLabel.setBounds(100, 250, 120, 30);
		panel.add(addressLabel);
		
		addressTF = new JTextField();
		addressTF.setBounds(200, 250, 160, 30);
		panel.add(addressTF);
		
		
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		studentsInfo = new JLabel ("All Students Info");
		studentsInfo.setBounds (250, 380, 200, 50);
		panel.add (studentsInfo);
		
		table = new JTable ();
		
		model = new DefaultTableModel();
		model.setColumnIdentifiers(cols);
		table.setModel(model);
		table.setRowHeight(20);
		
		scroll = new JScrollPane(table);
		scroll.setBounds(10,420,650,150);
		panel.add(scroll);
		
		this.add(panel);
	}
	
	public void actionPerformed (ActionEvent ae)
	{
		String text = ae.getActionCommand();
		
		if(text.equals(backBtn.getText()))
		{
			EmployeeHome mh = new EmployeeHome (userId);
			mh.setVisible(true);
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
		String loadId = userIdTF.getText();
		String query = "SELECT `userId`, `customerName`, `phoneNumber`, `address` FROM `customer` WHERE `userId`='"+loadId+"';";     
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
			String userId = null;
			String customerNamer = null;
			String phoneNumber = null;
			String address = null;
			while(rs.next())
			{
                userId = rs.getString("userId");
				customerNamer = rs.getString("customerName");
				phoneNumber = rs.getString("phoneNumber");
				address = rs.getString("address");
				flag=true;
				
				userIdTF.setText(""+userId);
				studentNameTF.setText(""+customerNamer);
				phoneTF.setText(""+phoneNumber);
				addressTF.setText(""+address);
				
				studentNameTF.setEnabled (false);
				phoneTF.setEnabled (false);
				addressTF.setEnabled (false);
				
			}
			if(!flag)
			{
				userIdTF.setText("");
				studentNameTF.setText("");
				phoneTF.setText("");
				addressTF.setText("");
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
		
        String query = "SELECT userId, customerName, phoneNumber, `address` FROM `customer`";     
        Connection con=null;//for connection
        Statement st = null;//for query execution
		ResultSet rs = null;//to get row by row result from DB
		try
			{
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
				String columnData[][]=new String[i][4];
				try {
					while(rs.next()){
						columnData [rowCount][0] = rs.getString ("userId");
						columnData [rowCount][1] = rs.getString ("customerName");
						columnData [rowCount][2] = rs.getString ("phoneNumber");
						columnData [rowCount][3] = rs.getString ("address");
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
	
	
}