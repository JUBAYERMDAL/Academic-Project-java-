import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class HomeWindow extends JFrame implements  ActionListener
{
	JLabel backGround, ebackGround, sbackGround;
	JButton btnE, btnS, btnExit;
	ImageIcon img, eImg, sImg;
	JPanel panel;
	
	public HomeWindow(){
		super("Library management System");
		this.setSize(650,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		img = new ImageIcon("background.jpg");
		eImg = new ImageIcon("Screenshot_2.png");
		sImg = new ImageIcon("Screenshot_1.png");
		
		
		
		ebackGround = new JLabel (eImg);
		ebackGround.setBounds (145,125,144,151);
		panel.add (ebackGround);
		
		sbackGround = new JLabel (sImg);
		sbackGround.setBounds (375,125,144,151);
		panel.add (sbackGround);
		
		btnE = new JButton("E-login");
		btnE.setBounds(160, 280, 120, 30);
		btnE.addActionListener(this);
		panel.add(btnE);
		
		btnS = new JButton("S-login");
		btnS.setBounds(390,280,120,30);
		btnS.addActionListener(this);
		panel.add(btnS);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(280,380,100,30);
		btnExit.addActionListener(this);
		panel.add(btnExit);
		
		backGround = new JLabel (img);
		backGround.setBounds (0,0,650,500);
		panel.add (backGround);
		
		this.add(panel);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String str = ae.getActionCommand();
		if(str.equals(btnE.getText()))
		{
			EmployeeLogin el = new EmployeeLogin (this);
			el.setVisible(true);
			this.setVisible(false);
		}
		else if(str.equals(btnS.getText()))
		{
			StudentLogin sl = new StudentLogin();
			sl.setVisible(true);
			this.setVisible(false);
		}
		else if(str.equals(btnExit.getText()))
		{
			System.exit (0);
		}
		else
		{
			
		}
	}
}
	
		
	