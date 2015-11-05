import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.InetAddress;

public class addclient implements ActionListener,ItemListener
{
 	public static Connection con=null;
 	public static Statement st=null;
 	public static ResultSet r=null;
 	public static int i=0;
	public JFrame jf;
	public Container c;
    public JButton jButton1;
    public JCheckBox jCheckBox1;
    public JCheckBox jCheckBox2;
    public JCheckBox jCheckBox3;
    public JLabel jLabel1;
    public JLabel jLabel2;
    public JLabel jLabel3;
    public JLabel jLabel4;
    public JTextField jTextField1;
    public JTextField jTextField2;
    public JTextField jTextField3;
    public JTextField jTextField4;
    public JTextField jTextField5;
    public Font l = new Font("Times New roman" , Font.BOLD , 18);
	public Font l1 = new Font("Times New roman" , Font.BOLD , 30);
	public Font l2 = new Font("Times New roman" , Font.BOLD , 16);
	static
    {
		try {
	        Connection con=getDBConnection();
			st.execute("delete from clients");
			st.execute("delete from client1");
			st.execute("delete from client2");
			st.execute("delete from client3");
			//st.execute("delete from client4");
			//st.execute("delete from client5");
		}
		catch (SQLException ex)
		{
		   	ex.printStackTrace();
  		}
		try
  		{
  			if (con != null)
  			{
   				st.close();
   				con.close();
   			}
  		}
  		catch (SQLException ex)
  		{
   			ex.printStackTrace();
  		}
	}
    public addclient() {
        initComponents();

    }
	private void initComponents()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: "+ex);
		}

		jf = new JFrame("Add Client");
		c = jf.getContentPane();
		c.setLayout(null);
		jf.setSize(500,420);
		jf.setResizable(false);
		c.setBackground( new Color(236,216,234));
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
        jf.setLocation((screenWidth-500) / 2, (screenHeight-420) / 2);
		jLabel1 = new JLabel("ADD CLIENT");
        jLabel2 = new JLabel("Client Name :");
        jLabel3 = new JLabel("Select Router :");
        jLabel4 = new JLabel("Client ip :");
        jButton1 = new JButton("Add Client");
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();
        jTextField4 = new JTextField();
		jTextField5 = new JTextField();
		jCheckBox1 = new JCheckBox("router1");
        jCheckBox2 = new JCheckBox("router2");
        jCheckBox3 = new JCheckBox("router3");

        jLabel1.setFont(l1);
        jLabel2.setFont(l);
        jLabel3.setFont(l);
        jLabel4.setFont(l);
        jLabel1.setBounds(160,0,200,30);
		jLabel2.setBounds(100,50,150,30);
		jLabel3.setBounds(100,100,150,30);
		jLabel4.setBounds(100,300,150,30);
		jCheckBox1.setBounds(150,150,150,30);
		jCheckBox2.setBounds(150,200,150,30);
		jCheckBox3.setBounds(150,250,150,30);
		jCheckBox1.setBackground( new Color(236,216,234));
		jCheckBox2.setBackground( new Color(236,216,234));
		jCheckBox3.setBackground( new Color(236,216,234));
		jTextField1.setBounds(300,50,100,30);
		jTextField2.setBounds(300,150,100,30);
		jTextField3.setBounds(300,200,100,30);
		jTextField4.setBounds(300,250,100,30);
		jTextField5.setBounds(300,300,100,30);
		jTextField1.setForeground(new Color(120,0,0));
		jTextField2.setForeground(new Color(120,0,0));
		jTextField3.setForeground(new Color(120,0,0));
		jTextField4.setForeground(new Color(120,0,0));
		jTextField5.setForeground(new Color(120,0,0));
		jTextField1.setFont(l2);
		jTextField2.setFont(l2);
		jTextField3.setFont(l2);
		jTextField4.setFont(l2);
		jTextField5.setFont(l2);
		jButton1.setBounds(200,350,120,30);
		jButton1.setFont(l);
		jButton1.setForeground(new Color(10,70,198));
		jTextField2.setEnabled(false);
		jTextField3.setEnabled(false);
		jTextField4.setEnabled(false);

		c.add(jLabel1);
		c.add(jLabel2);
		c.add(jLabel3);
		c.add(jLabel4);
		c.add(jCheckBox1);
        c.add(jCheckBox2);
        c.add(jCheckBox3);
        c.add(jTextField1);
        c.add(jTextField2);
        c.add(jTextField3);
        c.add(jTextField4);
        c.add(jTextField5);
        c.add(jButton1);
        jCheckBox1.addItemListener(this);
        jCheckBox2.addItemListener(this);
        jCheckBox3.addItemListener(this);
        jButton1.addActionListener(this);
        jf.show();
        jf.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent win)
			{
				System.exit(0);
			}
		});
    }

	public void itemStateChanged(ItemEvent e)
	{
		if(jCheckBox1.isSelected()==true)
			jTextField2.setEnabled(true);
		else
			jTextField2.setEnabled(false);
		if(jCheckBox2.isSelected()==true)
			jTextField3.setEnabled(true);
		else
			jTextField3.setEnabled(false);
		if(jCheckBox3.isSelected()==true)
			jTextField4.setEnabled(true);
		else
			jTextField4.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e)
	{
		String rname=null,cost=null;
		int c=0;
		if(e.getActionCommand()=="Add Client")
		{
			try
			{
				Connection con=getDBConnection();
				String tname=jTextField1.getText();
				String routerip=jTextField5.getText();
				//st.execute("create table "+tname+" (routername char(10),routerip char(20),cost char(5))");
				if(jCheckBox1.isSelected()==true)
				{
					rname=jCheckBox1.getActionCommand();
					cost=jTextField2.getText();
					if(cost!=null)
						c=Integer.parseInt(cost);
					st.execute("insert into "+tname+"(routername,routerport,cost,routerip) values(\'" + rname  + "\',1001,\'" + c + "\',\'" + routerip + "\')");
				}
				if(jCheckBox2.isSelected()==true)
				{
					rname=jCheckBox2.getActionCommand();
					cost=jTextField3.getText();
					if(cost!=null)
						c=Integer.parseInt(cost);
					st.execute("insert into "+tname+"(routername,routerport,cost,routerip) values(\'" + rname  + "\',1002,\'" + c + "\',\'" + routerip + "\')");
				}
				if(jCheckBox3.isSelected()==true)
				{
					rname=jCheckBox3.getActionCommand();
					cost=jTextField4.getText();
					if(cost!=null)
						c=Integer.parseInt(cost);
					st.execute("insert into "+tname+"(routername,routerport,cost,routerip) values(\'" + rname  + "\',1003,\'" + c + "\',\'" + routerip + "\')");
				}
				st.execute("insert into clients values("+i+",\'" + tname + "\',\'" + routerip + "\')");
				i++;
				int b = JOptionPane.showConfirmDialog(new JFrame(),"Client Added...\n Do you want to Add another client?", "Title",JOptionPane.YES_NO_OPTION);
				if(b == 0)
	            {
	                new addclient();
	                jf.dispose();
	            }
	            else
	            {
	                jf.dispose();
	                InetAddress obj=InetAddress.getLocalHost();
					String serverip=obj.getHostAddress();
					new server(serverip,"SERVER");
	            }
			}
			catch(Exception ex)
 			{
   				ex.printStackTrace();
  			}
  			try
  			{
  				if (con != null)
  				{
   					st.close();
   					con.close();
   				}
  			}
  			catch (SQLException ex)
  			{
   				ex.printStackTrace();
  			}
		}
	}

	public static Connection getDBConnection()
	{
		try {
             Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
             con = DriverManager.getConnection("jdbc:odbc:mrc");
             st=con.createStatement();
        }
        catch(Exception ex)
        {
             System.out.println("Error: "+ex);
        }
        return con;
	}

    public static void main(String ars[])
    {
         new addclient();
    }
}