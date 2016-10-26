import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;
public class DragAndDrop extends JFrame
{
	JButton button;
	JTextField textfield;
	JFileChooser filechooser;
	JTextArea textarea;	
	Socket socket;
	FileTransferClient filetransferclient;
	public DragAndDrop() 
	{
		try{
		socket = new Socket("127.0.0.1",3000);
		filetransferclient = new FileTransferClient(socket);
		}
		catch(Exception e){e.printStackTrace();}
		setTitle("Drag and Drop Client");
		button = new JButton("Send");
		
		filechooser = new JFileChooser();
		filechooser.setDragEnabled(true);
		
		
		textarea=new JTextArea(100,100);
		textarea.setBackground(new Color(89,34,12));
		textarea.setForeground(Color.WHITE);
		textarea.setTransferHandler(new TransferHandler("text"));
	
		button.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			String filepath = textarea.getText();
			filetransferclient.send_file(filepath);
		}});
		add(textarea,"Center");
		add(filechooser,"North");
		add(button,"South");
		setSize(500,500);
		setBackground(Color.GREEN);
		setVisible(true);
	}
	public static void main(String args[])
	{
		DragAndDrop dnd = new DragAndDrop();
	}
}

class FileTransferClient
{
	Socket socket;
	DataOutputStream dout;
	FileTransferClient(Socket s)
	{
		try
		{
			socket = s;
			dout = new DataOutputStream(socket.getOutputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	} 			
	public void send_file(String filepath)
	{
	try
	{
		dout.writeUTF("SEND");
		String[] parts = filepath.split("\\\\");
		int n = parts.length;
		dout.writeUTF(parts[n-1]);
		File file= new File(filepath);
		if(file.exists())
		{
			FileInputStream fis = new FileInputStream(file);
			int ch;
			do 
			{
				ch = fis.read();
				dout.writeUTF(String.valueOf(ch));
			}
			while(ch!=-1);
			fis.close();
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
}
