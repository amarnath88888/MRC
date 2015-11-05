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
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.InetAddress;

public class addrouter implements ActionListener
{
 	public static Connection con=null;
 	public static Statement st=null;
 	public static ResultSet r=null;
 	public static int portno=1001;
	public JFrame jf;
	public Container c;
    public JButton jButton1;
    public JLabel jLabel1;
    public JLabel jLabel2;
    public JLabel jLabel4;
    public JTextField jTextField1;
    public JTextField jTextField5;
    public Font l = new Font("Times New roman" , Font.BOLD , 18);
	public Font l1 = new Font("Times New roman" , Font.BOLD , 30);
	public Font l2 = new Font("Times New roman" , Font.BOLD , 16);
	static
    {
		try {
	        Connection con=getDBConnection();
			st.execute("delete from SERVER");
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
    public addrouter() {
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

		jf = new JFrame("Add Router");
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
		jLabel1 = new JLabel("ADD ROUTER");
        jLabel2 = new JLabel("Router Name :");
        jLabel4 = new JLabel("Router IP     :");
        jButton1 = new JButton("Add Router");
        jTextField1 = new JTextField();
        jTextField5 = new JTextField();

        jLabel1.setFont(l1);
        jLabel2.setFont(l);
        jLabel4.setFont(l);
        jLabel1.setBounds(160,30,250,30);
		jLabel2.setBounds(100,100,150,30);
		jLabel4.setBounds(100,200,150,30);
		jTextField1.setBounds(300,100,100,30);
		jTextField5.setBounds(300,200,100,30);
		jTextField1.setForeground(new Color(120,0,0));
		jTextField5.setForeground(new Color(120,0,0));
		jTextField1.setFont(l2);
		jTextField5.setFont(l2);
		jButton1.setBounds(200,300,140,30);
		jButton1.setFont(l);
		jButton1.setForeground(new Color(10,70,198));

		c.add(jLabel1);
		c.add(jLabel2);
		c.add(jLabel4);
		c.add(jTextField1);
        c.add(jTextField5);
        c.add(jButton1);
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

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand()=="Add Router")
		{
			try
			{
				Connection con=getDBConnection();
				String tname=jTextField1.getText();
				String routerip=jTextField5.getText();
				st.execute("insert into SERVER values(\'"+ tname + "\'," + portno + ",\'" + routerip + "\')");
				portno++;
				int b = JOptionPane.showConfirmDialog(new JFrame(),"Router Added...\n Do you want to Add another Router?", "Title",JOptionPane.YES_NO_OPTION);
				if(b == 0)
	            {
	                new addrouter();
	                jf.dispose();
	            }
	            else
	            {
	                jf.dispose();
	                InetAddress obj=InetAddress.getLocalHost();
					String serverip=obj.getHostAddress();
					new addclient();
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
         new addrouter();
    }
}