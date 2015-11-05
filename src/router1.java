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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Date;
import java.sql.Time;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class router1 implements ActionListener
{
	String status="";
	public JLabel la1= new JLabel("ROUTER A");
	public JLabel la2= new JLabel("Received File");
	public JLabel la3= new JLabel("File Size (Bytes)  :");
	public JLabel la4= new JLabel("Acknowlegment Status");
	public JLabel la5= new JLabel("");
	public JButton b= new JButton("Save Log");
	public JTextArea t1= new JTextArea("");
	public JTextArea t2= new JTextArea("");
	public JScrollPane sc=new JScrollPane();
	public JScrollPane sc1=new JScrollPane();

	Connection con=null;
	Statement st=null;
 	ResultSet rs=null;

	public Font l = new Font("Times New roman" , Font.BOLD , 18);
	public Font l1 = new Font("Times New roman" , Font.BOLD, 30);
	public Font l2 = new Font("Times New roman" , Font.BOLD , 16);
	public JFrame jf;
	public Container c;
	router1()
	{
		jf = new JFrame("Router A");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
        jf.setLocation((screenWidth-800) / 2, (screenHeight-600) / 2);
		c = jf.getContentPane();
		c.setLayout(null);
		jf.setSize(800,600);
		jf.setResizable(false);
		c.setBackground( new Color(236,216,234));
		b.setBounds(525,500,150,35);
		b.setFont(l2);
		b.setForeground(new Color(10,70,198));
		la1.setBounds(330,30,200,35);
		la2.setBounds(100,100,150,35);
		la3.setBounds(100,450,150,35);
		la4.setBounds(500,100,250,35);
		la5.setBounds(250,450,150,35);
		la1.setFont(l1);
		la2.setFont(l);
		la3.setFont(l);
		la4.setFont(l);
		la5.setFont(l);

		la5.setForeground(new Color(30,30,98));

		sc.setBounds(50,200,300,200);
		t1.setColumns(20);
		t1.setRows(10);
		t1.setForeground(new Color(10,50,100));
		t1.setFont(l);
		t1.setEditable(false);
  		sc.setViewportView(t1);

		sc1.setBounds(450,200,300,200);
		t2.setColumns(20);
		t2.setRows(10);
		t2.setForeground(new Color(11,54,3));
		t2.setFont(l);
		t2.setEditable(false);
  		sc1.setViewportView(t2);

		c.add(sc);
		c.add(sc1);
		c.add(la1);
		c.add(la2);
		c.add(la3);
		c.add(la4);
		c.add(la5);
		c.add(b);
		b.addActionListener(this);
		jf.show();
	    Date d=new Date();
		Time time=new Time(d.getTime());
		status+=time+" : Router is ready . . .\n\n";
		t2.setText(status);
	    jf.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent win)
			{
				System.exit(0);
			}
		});

	    int[] ports = new int[] { 1001,2001 };

		for (int i = 0; i < 2; i++)
		{
			Thread t = new Thread(new PortListener(ports[i]));
			t.setName("Listener-" + ports[i]);
			t.start();
		}
	}
	public static void main (String args[])
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		new router1();
	}

	class PortListener implements Runnable
	{
	  	BufferedOutputStream bos = null;
	   	ServerSocket server;
	   	Socket connection;
	   	BufferedReader br = null;
	   	int port,des,flag=0;
	   	String index,str;
	   	char dest;
	   	int ind;

	   	public PortListener(int port)
		{
	   		this.port = port;
	    }

	    public void run()
	    {
			String routerip[]=new String[5];
			int size=0;
		    try
		    {
				con=getDBConnection();
				st=con.createStatement();
				rs=st.executeQuery("select * from SERVER");
				while(rs.next())
					routerip[size++]=rs.getString(3);
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if (con != null)
				{
					st.close();
					con.close();
				}
			}
			catch (Exception e)
	  		{
				e.printStackTrace();
			}
			if(this.port==1001)
	   		{
	   			try
		   		{
	   				 server = new ServerSocket(1001);
	   				 while (true)
	   				 {
	   					connection = server.accept();
	   					br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	   					con=getDBConnection();
						st=con.createStatement();
						//st.execute("drop table updatemain");
						st.execute("select * into updatemain from main where destination<>2001");
	   					String strLine;
	   					StringBuffer buffer = new StringBuffer();
	   					while ((strLine = br.readLine()) != null)
	   					{
	   						buffer.append(strLine + "\n");
	   					}
	   				    br.close();
	   					connection.close();
	   					dest=buffer.charAt(buffer.length()-2);
						buffer.deleteCharAt(buffer.length()-1);
						buffer.deleteCharAt(buffer.length()-1);
         				t1.setText(buffer.toString());
	   					String filelen=buffer.toString();
	   					int len=filelen.length();
	   					String a=Integer.toString(len);
	   					la5.setText(a);
	   					Date d=new Date();
						Time time=new Time(d.getTime());
						status+=time+" : File received from server . . .\n\n";
						t2.setText(status);
						JOptionPane.showMessageDialog(jf, "File received from server !","Received",  JOptionPane.INFORMATION_MESSAGE);
						Thread.sleep(2000);
						time.setTime(d.getTime());
						status+=time+" : The 'Router A' has some failure . . .\n\n";
		  				t2.setText(status);
		  				status+=time+" : Data is transfered using subpath(router) . . .\n\n";
		  				t2.setText(status);
			   			JOptionPane.showMessageDialog(jf, "Router A has some failure !\nFile sent through subpath !","Failure",  JOptionPane.INFORMATION_MESSAGE);
						Thread.sleep(2000);
						byte[] byteArray;
		  				Socket router = null;
		  				try
			  			{
							rs=st.executeQuery("select * from main where source=1001");
							int t[]=new int[10];
							int i=0,j,min=0;
							String rip=null;
							while(rs.next())
							{
								t[i++]=rs.getInt(2);
							}
							for(j=1;j<i;j++)
								if(t[j]<t[min])
									min=j;
							if(min==0)
								rip=routerip[1];
							else
								rip=routerip[2];
		  					rs=st.executeQuery("select * from main where source=1001");
		  					for(i=0;i<=min;i++)
		  						rs.next();
		  					SocketAddress sockaddr = new InetSocketAddress(rip, rs.getInt(3));
							router = new Socket();
							router.connect(sockaddr);
		  			    	bos = new BufferedOutputStream(router.getOutputStream());
		  			    	byteArray =t1.getText().getBytes();
		  			    	bos.write(byteArray, 0, byteArray.length);
		  			    	bos.flush();
		  			    	str=Character.toString(dest);
							byteArray = str.getBytes();
       						bos.write(byteArray, 0, byteArray.length);
		  			    	bos.close();
		  			    	router.close();
		  				}
		  				catch(UnknownHostException e1)
						{
							e1.printStackTrace();
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}
		  			}
		   		}
	   			catch (IOException e)
	   			{
					e.printStackTrace();
	   			}
	   			catch (SQLException ex)
				{
					ex.printStackTrace();
  				}
  				catch (Exception e)
				{
					e.printStackTrace();
				}
  				try
				{
					if (con != null)
					{
						st.close();
						con.close();
					}
				}
				catch (Exception e)
		  		{
					e.printStackTrace();
				}
	   		}
	   		if(this.port==2001)
	   		{
		   		try
			   	{
				   	 server = new ServerSocket(2001);
				   	 while (true)
				   	 {
						 connection = server.accept();
					   	 br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	   					 String strLine;
	   				     StringBuffer buffer = new StringBuffer();
	   					 while ((strLine = br.readLine()) != null)
	   					 {
							 buffer.append(strLine + "\n");
	   					 }
	   					 br.close();
	   					 connection.close();
	   					 dest=buffer.charAt(buffer.length()-2);
						 buffer.deleteCharAt(buffer.length()-1);
						 buffer.deleteCharAt(buffer.length()-1);
	   					 t1.setText(buffer.toString());
	   					 String filelen=buffer.toString();
	   					 int len=filelen.length();
	   					 String a=Integer.toString(len);
	   					 la5.setText(a);
	   				 	 Date d=new Date();
	   				 	 Time time=new Time(d.getTime());
	   				 	 status+=time+" : Data received from subpath (router) . . .\n\n";
	   				 	 t2.setText(status);
	   					 JOptionPane.showMessageDialog(jf, "File received from subpath !","Received",  JOptionPane.INFORMATION_MESSAGE);
						 Thread.sleep(2000);
						 byte[] byteArray;
	   					 Socket client = null;
	   					 Character c=new Character(dest);
	   					 des=Integer.parseInt(c.toString());
	   					 //des=des+3000;
		  				 try
			  			 {
				  			con=getDBConnection();
				  			st=con.createStatement();
				  			rs=st.executeQuery("select * from router1");
							while(rs.next())
							{
								if(rs.getInt(1)==des)
								{
									SocketAddress sockaddr = new InetSocketAddress(rs.getString(2).trim(),5000);
									client = new Socket();
									client.connect(sockaddr);
									/*server = new ServerSocket(5000);
									client=server.accept();*/
									st.execute("drop table updatemain");
		  			    			flag=1;
		  			    			break;
								}
							}
							if(flag==0)
							{
								rs=st.executeQuery("select * from updatemain where source = 1001");
								rs.next();
								int pno=rs.getInt(3);
								SocketAddress sockaddr=null;
								if(pno==2002)
									sockaddr = new InetSocketAddress(routerip[1],pno);
								else
									sockaddr = new InetSocketAddress(routerip[2],pno);
								client = new Socket();
								client.connect(sockaddr);
							}
							bos = new BufferedOutputStream(client.getOutputStream());
		  			    	byteArray =t1.getText().getBytes();
		  			    	bos.write(byteArray, 0, byteArray.length);
		  			    	bos.flush();
		  			    	Date d1=new Date();
							Time time1=new Time(d1.getTime());
							if(flag==0)
		  			    	{
								String str1=Character.toString(dest);
								byteArray = str1.getBytes();
             					bos.write(byteArray, 0, byteArray.length);
             					status+=time1+" : Data is transferred to next router . . .\n\n";
             					t2.setText(status);
             					JOptionPane.showMessageDialog(jf, "Data is transferred to next router !","Sent",  JOptionPane.INFORMATION_MESSAGE);
								Thread.sleep(2000);
							}
							else
							{
								status+=time1+" : Data is transferred to destination . . .\n\n";
								t2.setText(status);
								JOptionPane.showMessageDialog(jf, "Data is transferred to destination !","Sent",  JOptionPane.INFORMATION_MESSAGE);
								Thread.sleep(2000);
							}
							bos.close();
		  			    	client.close();
		  				 }
		  				 catch (UnknownHostException e1)
		  				 {
		  				 	e1.printStackTrace();
					     }
					     catch (SQLException e1)
						 {
						 	e1.printStackTrace();
					     }
		  				 catch (IOException e1)
		  				 {
							 e1.printStackTrace();
						 }
						 catch (Exception e1)
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
	   			}
	   			catch (IOException e)
	   			{

	   	        }
	   	        catch (Exception e)
				{

	   	        }
	   		}
	    }
	}

	public void actionPerformed(ActionEvent e)
	{
	 	BufferedOutputStream bos = null;
	 	byte[] byteArray;
	 	String strLine = null;
		if(e.getSource()== b)
		{
			try
			{
				String filename = JOptionPane.showInputDialog("Enter the file name","router1_log.txt");
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

	public Connection getDBConnection()
	{
		try
		{
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
}