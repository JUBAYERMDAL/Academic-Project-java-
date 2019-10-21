import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.table.*;

public class ViewEmployee extends JFrame implements ActionListener
{
	JLabel welcomeLabel, userLabel, eNameLabel, phoneLabel, roleLabel, salaryLabel, allInformation;
	JButton refreshBtn, loadBtn, updateBtn, delBtn, backBtn, logoutBtn;
	JPanel panel;
	JTextField userTF,phoneTF2,roleTF,salaryTF,eNameTF;
	String userId;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	
	String [] cols = {"userId","employeeName","phoneNumber","role","salary"};
	
	public ViewEmployee(String userId)
	{
		super("View Employee");
		
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
		
		userLabel = new JLabel("User ID : ");
		userLabel.setBounds(100, 100, 100, 30);
		panel.add(userLabel);
		
		userTF = new JTextField();
		userTF.setBounds(200, 100, 120, 30);
		panel.add(userTF);
		
		loadBtn = new JButton("Load");
		loadBtn.setBounds(400, 100, 120, 30);
		loadBtn.addActionListener(this);
		panel.add(loadBtn);
		
		eNameLabel = new JLabel("Employee Name : ");
		eNameLabel.setBounds(100, 150, 120, 30);
		panel.add(eNameLabel);
		
		eNameTF = new JTextField();
		eNameTF.setBounds(200, 150, 120, 30);
		panel.add(eNameTF);
		
		phoneLabel = new JLabel("Phone No. : ");
		phoneLabel.setBounds(100, 200, 120, 30);
		panel.add(phoneLabel);
		
		
		phoneTF2 = new JTextField();
		phoneTF2.setBounds(200, 200, 85, 30);
		panel.add(phoneTF2);
		
		roleLabel = new JLabel("Role : ");
		roleLabel.setBounds(100, 250, 120, 30);
		panel.add(roleLabel);
		
		roleTF = new JTextField();
		roleTF.setBounds(200, 250, 120, 30);
		panel.add(roleTF);
		
		salaryLabel = new JLabel("Salary : ");
		salaryLabel.setBounds(100, 300, 120, 30);
		panel.add(salaryLabel);
		
		salaryTF = new JTextField();
		salaryTF.setBounds(200, 300, 120, 30);
		panel.add(salaryTF);
		
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
		
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(400, 350, 120, 30);
		refreshBtn.addActionListener(this);
		panel.add(refreshBtn);
		
		backBtn = new JButton ("<  Back");
		backBtn.setBounds (5,5,100,30);
		backBtn.addActionListener (this);
		panel.add (backBtn);
		
		allInformation = new JLabel ("All Employee Info.");
		allInformation.setBounds (250, 380, 200, 50);
		panel.add (allInformation);
		
		table = new JTable ();
		
		model = new DefaultTableModel ();
		model.setColumnIdentifiers (cols);
		table.setModel (model);
		table.setRowHeight (20);
		
		scroll = new JScrollPane(table);
		scroll.setBounds(10,420,650,150);
		panel.add(scroll);
		
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
		else if(text.equals(refreshBtn.getText()))
		{
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			userTF.setEnabled(true);
			userTF.setText("");
			eNameTF.setText("");
			phoneTF2.setText("");
			roleTF.setText("");
			salaryTF.setText("");
		}
		else
		{
			
		}
	}
	
	public void loadFromDB()
	{
		String loadId = userTF.getText();
		String query = "SELECT `employeeName`, `phoneNumber`, `role`, `salary` FROM `employee` WHERE `userId`='"+loadId+"';";     
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
			String eName = null;
			String phnNo = null;
			String role = null;
			double salary = 0.0;			
			while(rs.next())
			{
                eName = rs.getString("employeeName");
				phnNo = rs.getString("phoneNumber");
				role = rs.getString("role");
				salary = rs.getDouble("salary");
				flag=true;
				
				eNameTF.setText(eName);
				phoneTF2.setText(phnNo);
				roleTF.setText(role);
				salaryTF.setText(""+salary);
				userTF.setEnabled(false);
				updateBtn.setEnabled(true);
				delBtn.setEnabled(true);
			}
			if(!flag)
			{
				eNameTF.setText("");
				phoneTF2.setText("");
				roleTF.setText("");
				salaryTF.setText("");
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
		String newId = userTF.getText();
		String eName = eNameTF.getText();
		String phnNo = phoneTF2.getText();
		String role = roleTF.getText();
		double salary=0.0;
		try
		{
			salary = Double.parseDouble(salaryTF.getText());
		}
		catch(Exception e){}
		String query = "UPDATE employee SET employeeName='"+eName+"', phoneNumber = "+phnNo+", role = '"+role+"', salary = "+salary+" WHERE userId='"+newId+"'";	
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
			userTF.setEnabled(true);
			userTF.setText("");
			eNameTF.setText("");
			phoneTF2.setText("");
			roleTF.setText("");
			salaryTF.setText("");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, "Oops !!!");
		}
	}
	
	public void deleteFromDB()
	{
		String newId = userTF.getText();
		String query1 = "DELETE from employee WHERE userId='"+newId+"';";
		String query2 = "DELETE from login WHERE userId='"+newId+"';";
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
			
			information();
			updateBtn.setEnabled(false);
			delBtn.setEnabled(false);
			userTF.setEnabled(true);
			userTF.setText("");
			eNameTF.setText("");
			phoneTF2.setText("");
			roleTF.setText("");
			salaryTF.setText("");
		}
        catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Oops !!!");
        }
	}
	
	public void information ()
	{
		
        String query = "SELECT `userId`, `employeeName`, `phoneNumber`, `role`,`salary` FROM `employee`";     
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
			String columnData[][]=new String[i][5];
			try {
				while(rs.next()){
					columnData [rowCount][0] = rs.getString ("userID");
					columnData [rowCount][1] = rs.getString ("employeeName");
					columnData [rowCount][2] = rs.getString ("phoneNumber");
					columnData [rowCount][3] = rs.getString ("role");
					columnData [rowCount][4] = rs.getString ("salary");
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