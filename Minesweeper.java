import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;

class Minesweeper1 extends JFrame implements ActionListener,Runnable,MouseListener
{
	JButton b1[]=new JButton[100];
	JButton b2;

	JTextField t1,t2;

	JLabel l1=new JLabel("Mines Left : ");
	JLabel l2=new JLabel("Time Over : ");
	JLabel l3=new JLabel("        ");
	JLabel l4=new JLabel("        ");

	ImageIcon im=new ImageIcon("icon.jpg");
	ImageIcon im1=new ImageIcon("mine.jpg");

	Color coo=new Color(170,230,140);
	Thread t;
	
	MenuBar mbar;
	Menu file,view,help;
	MenuItem item1,item2,item3,item4,item5,item6,item7,itemc;

	Panel p1,p2;

	FileInputStream fin;
	FileOutputStream fout;

	int i,j,flag,chk,chk1,minesleft,timeover,thrdchk;
	int mine[]=new int[16];
	int usermine[]=new int[16];
	int val[]=new int[100];
	int disabled[]=new int[100];
	
	int count,minecount,discount;

	boolean suspendthrd;	

	Minesweeper1(int[] val,int[] disabled,int[] mine,int[] usermine,int minecount,int minesleft,int timeover,int thrdchk,int discount)
	{
		this.minesleft=minesleft;
		this.timeover=timeover;
		this.thrdchk=thrdchk;
		this.minecount=minecount;
		this.discount=discount;
		for(i=0;i<100;i++)
		{
			this.val[i]=val[i];
			this.disabled[i]=disabled[i];
		}
		for(i=0;i<16;i++)
		{
			this.mine[i]=mine[i];
			this.usermine[i]=usermine[i];
		}
		setTitle("Mine Sweeper By- Vishal Vinayak");
		Container c =this.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.white);	
		b2=new JButton(im);
		b2.setActionCommand(" ");
		t1=new JTextField(3);
		t1.setEnabled(false);
		t1.setText(String.valueOf(minesleft));
		t=new Thread(this);
		t2=new JTextField(3);
		t2.setText(String.valueOf(timeover));
		t2.setEnabled(false);
	
		mbar=new MenuBar();
		setMenuBar(mbar);
		file=new Menu("File");
		view=new Menu("View");
		help=new Menu("Help");

		file.add(item1=new MenuItem("New..."));
		file.add(item2=new MenuItem("Open"));
		file.add(item3=new MenuItem("Save"));
		file.add(item4=new MenuItem("Exit"));
		mbar.add(file);	
		
		view.add(item5=new MenuItem("Best Times"));
		view.add(itemc=new MenuItem("Colour Chooser"));
		mbar.add(view);
		
		help.add(item6=new MenuItem("About"));
		help.add(item7=new MenuItem("Instructions"));
		mbar.add(help);

		b2.addActionListener(this);
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
		item4.addActionListener(this);
		item5.addActionListener(this);
		item6.addActionListener(this);
		item7.addActionListener(this);
		itemc.addActionListener(this);

		p1=new Panel();
		p1.setBackground(new Color(230,230,140));
		p1.setBounds(0,0,450,50);
		p1.add(l1);
		p1.add(t1);
		p1.add(l3);
		p1.add(b2);
		p1.add(l4);
		p1.add(l2);
		p1.add(t2);

		p2=new Panel(new GridLayout(10,10));
		p2.setBackground(coo);
		p2.setBounds(0,50,450,450);
		
		for(i=0;i<100;i++)
		{
			b1[i]=new JButton();
			b1[i].setActionCommand(String.valueOf(i+1));
		}
		for(i=0;i<100;i++)
		{
			b1[i].addActionListener(this);
		}
		for(i=0;i<100;i++)
		{
			for(j=0;j<100;j++)
			{
				if(i==disabled[j])
				{
					b1[i].setForeground(Color.gray);
					b1[i].setBackground(Color.white);
					b1[i].setEnabled(false);
					b1[i].setText(String.valueOf(val[i]));					
				}
				else
				{
					if(b1[i].isEnabled()==true)
					{
						b1[i].setBackground(coo);
					}
				}	
			}
		}
		b1[90].addMouseListener(this);
		for(i=0;i<100;i++)
		{
			for(j=1;j<16;j++)
			{
				if(i==usermine[j])
				{
					b1[i].setEnabled(false);
					b1[i].setBackground(Color.red);
					b1[i].setForeground(Color.gray);
					b1[i].setText("x");
				}
			}
		}

		for(i=0;i<100;i++)
		{
			p2.add(b1[i]);
		}
		c.add(p1);
		c.add(p2);	
		JOptionPane.showMessageDialog(this,"Game Opened Successfully");		
	}

	Minesweeper1()
	{
		minesleft=15;
		timeover=0;
		thrdchk=0;
		count=1;
		minecount=1;
		discount=0;
		suspendthrd=false;

		setTitle("Mine Sweeper By- Vishal Vinayak");
		Container c =this.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.white);	
		
		b2=new JButton(im);
		b2.setActionCommand(" ");
		t1=new JTextField(3);
		t1.setEnabled(false);
		t1.setText(String.valueOf(minesleft));
		t=new Thread(this);
		t2=new JTextField(3);
		t2.setText("0");
		t2.setEnabled(false);
	
		mbar=new MenuBar();
		setMenuBar(mbar);
		file=new Menu("File");
		view=new Menu("View");
		help=new Menu("Help");

		file.add(item1=new MenuItem("New..."));
		file.add(item2=new MenuItem("Open"));
		file.add(item3=new MenuItem("Save"));
		file.add(item4=new MenuItem("Exit"));
		mbar.add(file);	
		
		view.add(item5=new MenuItem("Best Times"));
		view.add(itemc=new MenuItem("Colour Chooser"));
		mbar.add(view);
		
		help.add(item6=new MenuItem("About"));
		help.add(item7=new MenuItem("Instructions"));
		mbar.add(help);

		b2.addActionListener(this);
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
		item4.addActionListener(this);
		item5.addActionListener(this);
		item6.addActionListener(this);
		item7.addActionListener(this);
		itemc.addActionListener(this);

		p1=new Panel();
		p1.setBackground(new Color(230,230,140));
		p1.setBounds(0,0,450,50);
		p1.add(l1);
		p1.add(t1);
		p1.add(l3);
		p1.add(b2);
		p1.add(l4);
		p1.add(l2);
		p1.add(t2);

		p2=new Panel(new GridLayout(10,10));
		p2.setBackground(coo);
		p2.setBounds(0,50,450,450);
		
		for(i=0;i<100;i++)
		{
			b1[i]=new JButton();
			b1[i].setActionCommand(String.valueOf(i+1));
			b1[i].setForeground(coo);
			b1[i].setBackground(coo);
		}
		for(i=0;i<100;i++)
		{
			b1[i].addActionListener(this);
		}
		b1[90].addMouseListener(this);
		for(i=0;i<100;i++)
		{
			p2.add(b1[i]);
		}
		for(i=0;i<16;i++)
		{
			usermine[i]=111;
		}
		for(i=0;i<100;i++)
		{
			disabled[i]=112;
		}
		c.add(p1);
		c.add(p2);

		Random r=new Random();
		mine[0]=0;
		do
		{
			go:
			{
				chk1=r.nextInt(99);
				chk=chk1+1;
				for(j=1;j<=count;j++)
				{
					if(chk==mine[j])
					{
						flag=0;
						break;
					}
					else
					{
						flag=1;	
					}
				}
				if(flag==0)
				{
					break go;					
				}
				if(flag==1)
				{
					mine[count]=chk;
					count++;
				}
			}
		}
		while(count!=16);
	
		for(i=0;i<100;i++)
		{
			int chkmine=0;
			for(j=1;j<16;j++)
			{
				if((i>10&&i<19)||(i>20&&i<29)||(i>30&&i<39)||(i>40&&i<49)||(i>50&&i<59)||(i>60&&i<69)||(i>70&&i<79)||(i>80&&i<89))
				{	
					if(mine[j]==(i-11))
					{
						chkmine++;
					}
					if(mine[j]==(i-10))
					{
						chkmine++;
					}
					if(mine[j]==(i-9))
					{
						chkmine++;
					}
					if(mine[j]==(i-1))
					{
						chkmine++;
					}
					if(mine[j]==(i+1))
					{
						chkmine++;
					}
					if(mine[j]==(i+9))
					{
						chkmine++;
					}
					if(mine[j]==(i+10))
					{
						chkmine++;
					}
					if(mine[j]==(i+11))
					{
						chkmine++;
					}
				}

				if(i>0&&i<9)
				{
					if(mine[j]==(i-1))
					{
						chkmine++;
					}
					if(mine[j]==(i+1))
					{
						chkmine++;
					}
					if(mine[j]==(i+9))
					{
						chkmine++;
					}
					if(mine[j]==(i+10))
					{
						chkmine++;
					}
					if(mine[j]==(i+11))
					{
						chkmine++;
					}
				}
				if(i>90&&i<99)
				{
					if(mine[j]==(i-11))
					{
						chkmine++;
					}
					if(mine[j]==(i-10))
					{
						chkmine++;
					}
					if(mine[j]==(i-9))
					{
						chkmine++;
					}
					if(mine[j]==(i-1))
					{
						chkmine++;
					}
					if(mine[j]==(i+1))
					{
						chkmine++;
					}	
				}
				if(i==10||i==20||i==30||i==40||i==50||i==60||i==70||i==80)
				{
					if(mine[j]==(i-10))
					{
						chkmine++;
					}
					if(mine[j]==(i-9))
					{
						chkmine++;
					}
					if(mine[j]==(i+1))
					{
						chkmine++;
					}
					if(mine[j]==(i+10))
					{
						chkmine++;
					}
					if(mine[j]==(i+11))
					{
						chkmine++;
					}
				}
				if(i==19||i==29||i==39||i==49||i==59||i==69||i==79||i==89)
				{
					if(mine[j]==(i-11))
					{
						chkmine++;
					}
					if(mine[j]==(i-10))
					{
						chkmine++;
					}
					if(mine[j]==(i-1))
					{
						chkmine++;
					}
					if(mine[j]==(i+9))
					{
						chkmine++;
					}
					if(mine[j]==(i+10))
					{
						chkmine++;
					}
				}
				if(i==0)
				{
					if(mine[j]==(i+1))
					{
						chkmine++;
					}
					if(mine[j]==(i+10))
					{
						chkmine++;
					}
					if(mine[j]==(i+11))
					{
						chkmine++;
					}					
				}
				if(i==9)
				{
					if(mine[j]==(i-1))
					{
						chkmine++;
					}
					if(mine[j]==(i+9))
					{
						chkmine++;
					}
					if(mine[j]==(i+10))
					{
						chkmine++;
					}				
				}
				if(i==90)
				{
					if(mine[j]==(i-10))
					{
						chkmine++;
					}
					if(mine[j]==(i-9))
					{
						chkmine++;
					}
					if(mine[j]==(i+1))
					{
						chkmine++;
					}
				}
				if(i==99)
				{
					if(mine[j]==(i-11))
					{
						chkmine++;
					}
					if(mine[j]==(i-10))
					{
						chkmine++;
					}
					if(mine[j]==(i-1))
					{
						chkmine++;
					}
				}

			}
			val[i]=chkmine;
		}
	}
	
	public void run()
	{
		try
		{
			do
			{
				t.sleep(1000);
				t2.setText(String.valueOf(timeover));
				timeover++;
			}
			while(timeover!=1001 && suspendthrd!=true);
			if(timeover>=1000)
			{
				for(i=0;i<100;i++)
				{
					b1[i].setEnabled(false);
				}
				JOptionPane.showMessageDialog(this,"        You took a lot of time \n               YOU LOOSE");
				System.exit(0);
			}
		}
		catch(InterruptedException e1)
		{
		}
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String s1=(String) ae.getActionCommand();
		if(s1.equals("New...")||s1.equals(" "))
		{
			dispose();
			Minesweeper1 m=new Minesweeper1();
			m.setSize(460,555);
			m.setVisible(true);
		}
		if(s1.equals("Open"))
		{
			try
			{
				OpenGame();
			}	
			catch(IOException ioe)
			{
			}
		}
		if(s1.equals("Save"))
		{
			try
			{
				SaveGame();
			}	
			catch(IOException ioe)
			{
			}
		}
		if(s1.equals("Exit"))
		{
			System.exit(0);
		}
		if(s1.equals("Best Times"))
		{
			OpenBestTime();
		}
		if(s1.equals("Colour Chooser"))
		{
			coo=JColorChooser.showDialog(Minesweeper1.this,"Select Color",coo);
			for(i=0;i<100;i++)
			{
				if(b1[i].isEnabled()==true)
				{
					b1[i].setBackground(coo);
				}
			}
		}
		if(s1.equals("About"))
		{
			JOptionPane.showMessageDialog(this," Mine Sweeper \n\n\t Made By :  \n Vishal Vinayak \n C.S.E.       '03 \n S.B.S.C.E.T. Ferozepur \n Punjab, India. \n\n http://vishal.says.it \n vishalvinayak@rediffmail.com ");
	
		}
		if(s1.equals("Instructions"))
		{
			JOptionPane.showMessageDialog(this,"                                   Mine Sweeper \n                              Copyrights Protected \n Try to find the Mines in the field. Use 'CTRL+Left Click' to \n spot the mine. You can guess the position of a mine \n from it's neighbouring boxes. 0 indicates no mine nearby, \n 1 indicates 1 mine nearby & so on........ \n Find all 15 mines to win the game. & Be Careful to \n complete the game in time. \n Don't step over the mine (Left Click only) else \n BANG - GAME OVER !!!  ");
	
		}
		int v=(int) ae.getModifiers();
		if(v==16)		//ActionEvent's No Modifier
		{	
			if(thrdchk==0||thrdchk==200)
			{
				t.start();
				thrdchk++;
			}
			for(i=0;i<100;i++)
			{
				String s2=String.valueOf(i+1);
				if(s1.equals(s2))
				{
					for(int j=1;j<=15;j++)
					{
						if(mine[j]==i)
						{	
							suspendthrd=true;
							for(int q=0;q<100;q++)
							{
								b1[q].setEnabled(false);
							}
							for(int q1=1;q1<16;q1++)
							{
								b1[mine[q1]].setText("");
								b1[mine[q1]].setBackground(Color.yellow);
								b1[mine[q1]].setIcon(im1);
							}
							JOptionPane.showMessageDialog(this,"        You Stepped on a Mine \n                YOU LOOSE");
							System.exit(0);
						}	
					}
					b1[i].setForeground(Color.blue);
					b1[i].setBackground(Color.white);
					b1[i].setText(String.valueOf(val[i]));
					b1[i].setEnabled(false);
					disabled[discount]=i;
					discount++;					
				}		
			}
		}
		if(v==18)		//ActionEvent.CTRL_MASK
		{
			if(thrdchk==0||thrdchk==200)
			{
				t.start();
				thrdchk++;
			}
			usermine[0]=0;
			for(i=0;i<100;i++)
			{
				String s2=String.valueOf(i+1);
				if(s1.equals(s2))
				{
					usermine[minecount]=i;
					minecount++;	
					minesleft--;
					t1.setText(String.valueOf(minesleft));
					b1[i].setBackground(Color.red);
					b1[i].setForeground(Color.gray);
					b1[i].setText("x");
					b1[i].setEnabled(false);
				}		
			}
		}
		if(minesleft==0)
		{
			winner();
		}
	}

	void winner()
	{
		int okflag=0;
		for(i=1;i<16;i++)
		{
			for(j=1;j<16;j++)
			{
				if(mine[i]==usermine[j])
				{
					okflag++;
					break;
				}
			}
		}
		if(okflag==15)
		{
			suspendthrd=true;
			int time=Integer.parseInt(t2.getText());
			time++;
			JOptionPane.showMessageDialog(this,"           You Win The Game !!!!! \n You Completed the Game in : "+time+" sec");
			SaveBestTime(time);
			System.exit(0);
		}
		else
		{
			suspendthrd=true;
			for(int q=0;q<100;q++)
			{
				b1[q].setEnabled(false);
			}
			for(int q1=1;q1<16;q1++)
			{
				b1[mine[q1]].setText("");
				b1[mine[q1]].setBackground(Color.yellow);
				b1[mine[q1]].setIcon(im1);
			}
			int time=Integer.parseInt(t2.getText());
			time++;
			JOptionPane.showMessageDialog(this," You did'nt spotted the Mines correctly \n            You Loose the Game");
			System.exit(0);
		}
	}
	void SaveGame() throws IOException
	{
		try
		{
			try
			{
				fout=new FileOutputStream(new File("Save.vis"));
			}
			catch(FileNotFoundException e)
			{
			}
		}	
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
		for(i=0;i<100;i++)
		{
			fout.write(val[i]);
		}
		fout.write(minesleft);
		fout.write(timeover);
		thrdchk=200;
		fout.write(thrdchk);
		for(i=0;i<16;i++)
		{
			fout.write(mine[i]);
		}
		for(i=0;i<16;i++)
		{
			fout.write(usermine[i]);
		}
		fout.write(minecount);
		for(i=0;i<100;i++)
		{
			fout.write(disabled[i]);
		}
		fout.write(discount);		
		fout.close();
		JOptionPane.showMessageDialog(this,"     Game Saved Successfully");
		System.exit(0);
	}

	void OpenGame() throws IOException
	{
		dispose();
		int mine[]=new int[16];
		int usermine[]=new int[16];
		int val[]=new int[100];
		int disabled[]=new int[100];
		try
		{
			try
			{
				fin=new FileInputStream("Save.vis");	
			}
			catch(FileNotFoundException fe)
			{
				JOptionPane.showMessageDialog(this,"    No Saved Game Present");
				System.exit(0);
			}
			catch(NullPointerException ne)
			{
				JOptionPane.showMessageDialog(this,"    No Saved Game Present");
				System.exit(0);
			}
		}	
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
		for(i=0;i<100;i++)
		{
			j=fin.read();
			val[i]=j;
		}
		minesleft=fin.read();
		timeover=fin.read();
		thrdchk=fin.read();
		for(i=0;i<16;i++)
		{
			j=fin.read();
			mine[i]=j;
		}
		for(i=0;i<16;i++)
		{
			j=fin.read();
			usermine[i]=j;
		}
		minecount=fin.read();
		for(i=0;i<100;i++)
		{
			j=fin.read();
			disabled[i]=j;
		}
		discount=fin.read();
		fin.close();

		Minesweeper1 m=new Minesweeper1(val,disabled,mine,usermine,minecount,minesleft,timeover,thrdchk,discount);
		m.setSize(460,555);
		m.setVisible(true);
	}
	void SaveBestTime(int time)
	{
		FileInputStream fins;
		FileOutputStream fouts;
		int a[]=new int[8];
		int j=0,f=1,rem=0;
		String sb[]=new String[8];
		String sbf=new String();
		try
		{
			try
			{
				fins=new FileInputStream("BestTimes.vis");	
			}
			catch(FileNotFoundException e)
			{
				fouts=new FileOutputStream(new File("BestTimes.vis"));
				fouts.write(time);	
				JOptionPane.showMessageDialog(this,"      Best Time Made: "+time+" sec"); 
				f=0;
				return;
			}
			if(f==1)
			{
				do
				{
					i=fins.read();
					if(i!=-1)
					{
						a[j]=i;
						j++;
					}					
				}
				while(i!=-1);
				a[j]=9;
				j++;
				a[j]=9;
				j++;
				a[j]=9;
				j++;	
				
				for(i=0;i<5;i++)
				{
					if(a[i]==9 && a[i+1]==9 && a[i+2]==9)
					{	
						rem=i;	
						break;	
					}
				}
				
				for(i=0;i<rem;i++)
				{
					sb[i]=new String();
					sb[i]=String.valueOf(a[i]);	
					sbf=sbf+sb[i];
				}
				int m=Integer.parseInt(sbf);
				if(time<m)
				{
					try
					{
						fouts=new FileOutputStream("BestTimes.vis");
						fouts.write(time);
						JOptionPane.showMessageDialog(this,"      Best Time Made: "+time+" sec"); 
					}
					catch(FileNotFoundException fe)
					{	
					}
				}	
			}
			fins.close();
		}
		catch(IOException ioe)
		{
		}
		catch(NullPointerException ne)
		{
		}
	}	
	void OpenBestTime()
	{
		FileInputStream fins;
		FileOutputStream fouts;
		int a[]=new int[8];
		int j=0,rem=0;
		String sb[]=new String[8];
		String sbf=new String();
		try
		{
			try
			{
				fins=new FileInputStream("BestTimes.vis");	
			}
			catch(FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(this,"    No Best Times Saved"); 
				return;
			}
			do
			{
				i=fins.read();
				if(i!=-1)
				{
					a[j]=i;
					j++;
				}					
			}
			while(i!=-1);
			a[j]=9;
			j++;
			a[j]=9;
			j++;
			a[j]=9;
			j++;	
					
			for(i=0;i<5;i++)
			{
				if(a[i]==9 && a[i+1]==9 && a[i+2]==9)
				{	
					rem=i;	
					break;	
				}
			}					
			for(i=0;i<rem;i++)
			{
				sb[i]=new String();
				sb[i]=String.valueOf(a[i]);	
				sbf=sbf+sb[i];
			}
			int m=Integer.parseInt(sbf);
			JOptionPane.showMessageDialog(this,"      Best Time : "+m+" sec");
			fins.close();
		}
		catch(IOException ioe)
		{
		}
		catch(NullPointerException ne)
		{
		}
	}
	public void mouseClicked(MouseEvent me)
	{
		int key=(int) me.getButton();
		if(key==MouseEvent.BUTTON3)
		{
			String sss=JOptionPane.showInputDialog("Enter Code:");
			if(sss.equalsIgnoreCase("show"))
			{
				for(i=1;i<16;i++)
				{
					b1[mine[i]].setForeground(Color.red);
					b1[mine[i]].setText("x");
				}
			}
			if(sss.equalsIgnoreCase("hide"))
			{
				for(i=1;i<16;i++)
				{
					b1[mine[i]].setForeground(new Color(170,230,140));
					b1[mine[i]].setText("x");
				}
			}
			if(sss.equalsIgnoreCase("remove"))
			{
				for(i=1;i<16;i++)
				{
					b1[mine[i]].setText(null);
				}	
				for(i=1;i<=minecount;i++)
				{
					b1[usermine[i]].setForeground(new Color(170,230,140));
					b1[usermine[i]].setText("x");
				}
			}
			if(sss.equalsIgnoreCase("savetime"))
			{
				timeover=0;	
			}
			if(sss.equalsIgnoreCase("exit"))
			{
				System.exit(0);	
			}
		}
	}
	public void mouseEntered(MouseEvent me)
	{
	}
	public void mouseExited(MouseEvent me)
	{
	}
	public void mousePressed(MouseEvent me)
	{
	}
	public void mouseReleased(MouseEvent me)
	{
	}
}
public class Minesweeper
{
	public static void main(String args[])
	{
		Minesweeper1 m=new Minesweeper1();
		m.setSize(460,555);
		m.setVisible(true);
	}
}