import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Date;
import java.sql.Time;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class server implements ActionListener
{

	String ipaddr;
	public static Connection con=null;
 	public static Statement st=null,st1=null;
 	public static ResultSet rs=null,rs1=null;
	public String strings[]=new String[10];
	public String name,status="";
	public StringBuffer sbr = new StringBuffer();
	public JButton b1= new JButton("Browse");
	public JButton b3= new JButton("Send");
	public JButton b4= new JButton("View File");
	public JButton b5= new JButton("Download");
	public JButton b6= new JButton("Save Log");
	public JLabel la1= new JLabel("");
	public JLabel la2= new JLabel("Select the file      : ");
	public JLabel la3= new JLabel("File path             : ");
	public JLabel la4= new JLabel("File Size (Bytes)  : ");
	public JLabel la5= new JLabel("Attached File");
	public JLabel la6= new JLabel("Destination : ");
	public JLabel la7= new JLabel("Status Window ");
	public JLabel path= new JLabel("");
	public JLabel size= new JLabel("");
	public JTextArea t1= new JTextArea("");
	public JTextArea t2= new JTextArea("");
	public JTextArea t3= new JTextArea("");
	public JScrollPane sc=new JScrollPane();
	public JScrollPane sc1=new JScrollPane();
	public JScrollPane sc2=new JScrollPane();
	public JComboBox cb;


	public Font l = new Font("Times New roman" , Font.BOLD , 18);
	public Font l1 = new Font("Times New roman" , Font.BOLD , 30);
	public Font l2 = new Font("Times New roman" , Font.BOLD , 16);
	public JFrame jf,jf1;
	public Container c,c1;
	static
    {
		try {
	        Connection con=getDBConnection();
			st.execute("delete from router1");
			st.execute("delete from router2");
			st.execute("delete from router3");
			routing();
			possiblepath();
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
	server(String ipaddr,String name)
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
		jf = new JFrame(name);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
        jf.setLocation((screenWidth-800) / 2, (screenHeight-600) / 2);
		this.ipaddr=ipaddr;
		this.name=name;
		la1.setText(name);
		c = jf.getContentPane();
		c.setLayout(null);
		jf.setSize(800,600);
		jf.setResizable(false);
		c.setBackground( new Color(236,216,234));
		la3.setVisible(false);
		la4.setVisible(false);
		la5.setVisible(false);
		t1.setVisible(false);
		sc.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);
		path.setVisible(false);
		size.setVisible(false);
		la1.setBounds(350,0,150,50);
		la2.setBounds(50,50,150,50);
		la3.setBounds(50,100,150,50);
		la4.setBounds(50,150,150,50);
		la5.setBounds(50,200,150,50);
		la6.setBounds(500,50,150,50);
		la7.setBounds(500,200,150,50);
		la1.setFont(l1);
		la2.setFont(l);
		la3.setFont(l);
		la4.setFont(l);
		la5.setFont(l);
		la6.setFont(l);
		la7.setFont(l);
		cb = new JComboBox();
		cb.setBounds(620,60,100,35);
		refresh();
		b1.setBounds(250,60,100,35);
		b1.setFont(l2);
		b1.setForeground(new Color(10,70,198));
		b3.setBounds(125,500,150,35);
		b3.setFont(l2);
		b3.setForeground(new Color(10,70,198));
		b4.setBounds(325,500,150,35);
		b4.setFont(l2);
		b4.setForeground(new Color(10,70,198));
		b5.setBounds(525,500,150,35);
		b5.setFont(l2);
		b5.setForeground(new Color(10,70,198));
		b6.setBounds(525,500,150,35);
		b6.setFont(l2);
		b6.setForeground(new Color(10,70,198));
		path.setBounds(250,110,400,35);
		path.setFont(l2);
		path.setForeground(new Color(120,0,0));
		size.setBounds(250,160,100,35);
		size.setForeground(new Color(30,70,98));
		size.setFont(l2);
		sc.setBounds(50,250,300,200);
		t1.setColumns(20);
		t1.setRows(10);
		t1.setForeground(Color.BLUE);
		t1.setFont(l);
		t1.setEditable(false);
  		sc.setViewportView(t1);
		sc1.setBounds(0,0,791,568);
		t2.setColumns(20);
		t2.setRows(10);
		t2.setForeground(Color.BLUE);
		t2.setFont(l);
		t2.setEditable(false);
  		sc1.setViewportView(t2);
		sc2.setBounds(450,250,300,200);
		t3.setColumns(20);
		t3.setRows(10);
		t3.setForeground(Color.BLUE);
		t3.setFont(l);
		t3.setEditable(false);
  		sc2.setViewportView(t3);
		c.add(b1);
		c.add(b3);
		c.add(b4);
		c.add(b5);
		c.add(b6);
		c.add(la1);
		c.add(la2);
		c.add(la3);
		c.add(la4);
		c.add(la5);
		c.add(la6);
		c.add(la7);
		c.add(path);
		c.add(size);
		c.add(cb);
		c.add(sc,BorderLayout.CENTER);
		c.add(sc2,BorderLayout.CENTER);
		b1.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		jf.show();
		Date d=new Date();
		Time time=new Time(d.getTime());
		status+=time+" : Server Started . . .\n\n";
		t3.setText(status);

	   	jf.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent win)
			{
				System.exit(0);
			}
		});
		Thread t = new Thread(new PortListener(ipaddr));
		t.setName("Listener-" + ipaddr);
		t.start();
	}

	public void refresh()
	{
		try
		{
			con=getDBConnection();
			st=con.createStatement();
			int i=0;
			rs=st.executeQuery("select * from clients");
			while(rs.next())
			{
				//if(!(this.name).equals(rs.getString(1).trim()))
				//{
					strings[i++]=rs.getString(2).trim();
					cb.addItem(strings[i-1]);
				//}
			}
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
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

	class PortListener implements Runnable
	{
		BufferedOutputStream bos = null;
		ServerSocket server;
		Socket connection;
		BufferedReader br = null;
		String ipaddr;
		public PortListener(String ipaddr)
		{
			this.ipaddr= ipaddr;
			System.out.println("Server IP : "+ipaddr);
		}

		public void run()
		{
			try
			{
		    	server = new ServerSocket(3000);
		    	while (true)
		    	{
					connection = server.accept();
					br = new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));
					String strLine;
					StringBuffer buffer = new StringBuffer();
					while ((strLine = br.readLine()) != null)
					{
						buffer.append(strLine + "\n");
						sbr.append(strLine + "\n");
					}
					br.close();
					connection.close();
					/*b1.setVisible(false);
					la2.setVisible(false);
					cb.setVisible(false);
					la6.setVisible(false);
					t1.setText(buffer.toString());
					String content=buffer.toString();
					int len=content.length();
					String a=Integer.toString(len);
					size.setText(a);*/
					jf.toFront();
					Thread.sleep(1000);
					Date d=new Date();
					Time time=new Time(d.getTime());
					status+=time + " : " +buffer.toString()+" \n\n";
					t3.setText(status);
					JOptionPane.showMessageDialog(jf, "Ack Received","Received",  JOptionPane.INFORMATION_MESSAGE);
					/*b4.setVisible(true);
					b5.setVisible(true);*/
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	void sending(Socket router)
	{
		BufferedOutputStream bos = null;
	 	byte[] byteArray;
		try
		{
			bos = new BufferedOutputStream(router.getOutputStream());
			byteArray = t1.getText().getBytes();
			int i=0;
			bos.write(byteArray, 0, byteArray.length);
			bos.flush();
			String t=cb.getSelectedItem().toString();
			con=getDBConnection();
			st=con.createStatement();
    		rs=st.executeQuery("select * from clients");
    		while(rs.next())
    		{
				if(t.equals(rs.getString(2).trim()))
					break;
				i++;
			}
			String str=Integer.toString(i);
			byteArray = str.getBytes();
			bos.write(byteArray, 0,byteArray.length);
			bos.flush();
			bos.close();
			router.close();
		}
		catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
	 	BufferedOutputStream bos = null;
	 	byte[] byteArray;
	 	String strLine = null;
	 	if(e.getSource()== b1)
		{
			JFileChooser chooser = new JFileChooser();
	    	try
	    	{
				File f = new File(new File("filename.txt").getCanonicalPath());
				chooser.setSelectedFile(f);
	       	}
	    	catch (IOException e1)
			{
				e1.printStackTrace();
			}
			int retval = chooser.showOpenDialog(b1);
	       	if (retval == JFileChooser.APPROVE_OPTION)
	    	{
	              	File field = chooser.getSelectedFile();
	              	path.setText(field.getAbsolutePath());
	       	}
	       	File curFile = chooser.getSelectedFile();
	       	try
	       	{
		        FileInputStream fstream = new FileInputStream(curFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				StringBuffer buffer = new StringBuffer();
				while ((strLine = br.readLine()) != null)
				{
				  	buffer.append(strLine+ "\n");
				}
				Date d=new Date();
				Time time=new Time(d.getTime());
				status+=time+" : File is Ready to Send . . .\n\n";
				t3.setText(status);
				t1.setText(buffer.toString());
				String content=buffer.toString();
				int len=content.length();
				String a=Integer.toString(len);
				size.setText(a);
				la3.setVisible(true);
				la4.setVisible(true);
				la5.setVisible(true);
				t1.setVisible(true);
				sc.setVisible(true);
				b3.setVisible(true);
				path.setVisible(true);
				size.setVisible(true);
				JOptionPane.showMessageDialog(jf, "File is Ready to Send","Ready",  JOptionPane.INFORMATION_MESSAGE);
			}
			catch (Exception e1)
			{
				System.err.println("Error: " + e1.getMessage());
			}
		}
	    if(e.getSource()== b3)
        {
	        try
	        {
				con=getDBConnection();
				st=con.createStatement();
				Random r = new Random();
				int count=0;
				rs=st.executeQuery("select * from "+name);
				while(rs.next())
					count++;
				int p1 =(Math.abs(r.nextInt()) % count);
				rs=st.executeQuery("select * from "+name);
				for(int i=0;i<=p1;i++)
					rs.next();
				String rname=rs.getString(1);
				SocketAddress sockaddr = new InetSocketAddress(rs.getString(3),rs.getInt(2));
				Socket clientSocket = new Socket();
				clientSocket.connect(sockaddr);
				Date d=new Date();
				Time time=new Time(d.getTime());
				status+=time+" : File is Sent to "+rname+" . . .\n\n";
				t3.setText(status);
				JOptionPane.showMessageDialog(jf, "File Sent to "+rname+" !","Sent",  JOptionPane.INFORMATION_MESSAGE);
				Thread.sleep(2000);
				sending(clientSocket);
			}
			catch(UnknownHostException e1)
			{
				e1.printStackTrace();
			}
			catch (Exception e1){
				e1.printStackTrace();
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
        if(e.getSource()== b4)
		{
			la4.setVisible(true);
			size.setVisible(true);
			t1.setVisible(true);
			sc.setVisible(true);
			jf1 = new JFrame("Received File");
			c1 = jf1.getContentPane();
			c1.setLayout(null);
			jf1.setSize(800,600);
			jf1.setResizable(false);
			c1.add(sc1,BorderLayout.CENTER);
			t2.setText(sbr.toString());
			jf1.show();
		}
		if(e.getSource()== b5)
		{
			try
			{
				String filename = JOptionPane.showInputDialog("Enter the file name","filename.txt");
				BufferedOutputStream outputFile=new BufferedOutputStream(new FileOutputStream(filename));
				byte[] data=sbr.toString().getBytes();
				outputFile.write(data,0,data.length);
				outputFile.close();
				JOptionPane.showMessageDialog(jf, "File Downloaded Successfully !","Downloaded",  JOptionPane.INFORMATION_MESSAGE);
			}
			catch(FileNotFoundException ex)
			{
				ex.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
		if(e.getSource()== b6)
		{
			try
			{
				String filename = JOptionPane.showInputDialog("Enter the file name","server_log.txt");
				BufferedOutputStream outputFile=new BufferedOutputStream(new FileOutputStream(filename));
				byte[] data=t2.getText().getBytes();
				outputFile.write(data,0,data.length);
				outputFile.close();
				JOptionPane.showMessageDialog(jf, "Log Status saved !","Saved",  JOptionPane.INFORMATION_MESSAGE);
				Date d=new Date();
				Time time=new Time(d.getTime());
				status+=time+" : Log Status saved . . .\n\n";
				t2.setText(status);

			}
			catch(FileNotFoundException ex)
			{
				ex.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	public static Connection getDBConnection()
	{
		try
		{
             Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
             con = DriverManager.getConnection("jdbc:odbc:mrc");
             st=con.createStatement();
        }
        catch(Exception ex)
        {
             System.out.println("Error : " + ex);
             ex.printStackTrace();
        }
        return con;
	}
	public static void routing()
	{
		int index[]=new int[10];
		String clientname[]=new String[10];
		String clientip[]=new String[10];
		String routername;
		int clientcost,routerport,i=0,j=0;
		try
		{
			con=getDBConnection();
			st=con.createStatement();
			rs=st.executeQuery("select * from clients");
			while(rs.next())
			{
				index[i]=rs.getInt(1);
				clientname[i]=rs.getString(2).trim();
				clientip[i]=rs.getString(3).trim();
				i++;
			}
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
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
  		try
  		{
			con=getDBConnection();
			st1=con.createStatement();
			for(j=0;j<i;j++)
			{
				rs1=st1.executeQuery("select * from "+clientname[j]);
				while(rs1.next())
				{
					routername=rs1.getString(1).trim();
					routerport=rs1.getInt(2)+1000;
					clientcost=rs1.getInt(3);
					st.execute("insert into "+routername+" values("+index[j] + ",\'" + clientip[j] + "\'," +clientcost + "," + routerport + ")");
					System.out.println("Router table inserted..."+routername);
				}
			}
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
		}
		try
  		{
   			if (con != null)
   			{
				st1.close();
    			con.close();
   			}
  		}
  		catch (SQLException ex)
  		{
   			ex.printStackTrace();
  		}
	}
	public static void possiblepath()
	{
		/*try
		{
			con=getDBConnection();
			st=con.createStatement();
			int index[]=new int[10];
			String clientname[]=new String[10];
			String routername[][]=new String[10][];
			int routerport[][]=new int[10][];
			int cost[][]=new int[10][];
			int i=0,j=0,k=0;
			rs=st.executeQuery("select * from clients");
			while(rs.next())
			{
				index[i]=rs.getInt(1);
				clientname[i]=rs.getString(2).trim();
				i++;
			}
			for(j=0;j<i;j++)
			{
				st1=con.createStatement();
				rs1=st1.executeQuery("select * from "+clientname[j]);
				k=0;
				while(rs1.next())
				{
					routername[j][k]=rs1.getString(1).trim();
					routerport[j][k]=rs1.getInt(2);
					cost[j][k]=rs1.getInt(3);
					k++;
				}
			}
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
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
  		}*/
	}
}