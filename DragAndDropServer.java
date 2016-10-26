import javax.swing.*;
import java.awt.*; 
import java.net.*;
import java.io.*;
public class DragAndDropServer extends JFrame 
{
	 JTextArea textarea;
	DragAndDropServer()
	{
		setTitle("Drag and Drop Server");
		textarea = new JTextArea();
		textarea.setBackground(new Color(150,200,243));
		add(textarea,"Center");		
		setSize(500,500);
		setVisible(true);	
	}
	public static void main(String args[]) throws Exception
	{
		ServerSocket ss = new ServerSocket(3000);
		DragAndDropServer dndserver = new DragAndDropServer(); 
		dndserver.textarea.append("Waiting for Client...");
		while(true)
		{
			TransferFile tf = new TransferFile(ss.accept(),dndserver);
		}
	}
}

class TransferFile extends Thread
{
	DataInputStream din;
	FileOutputStream fileout;
	Socket socket;
	DragAndDropServer dndserver;
	TransferFile(Socket s, DragAndDropServer dndserver)
	{	
		try{
		this.dndserver = dndserver; 
		dndserver.textarea.append("\nClient connected..");
		socket = s;
		din = new DataInputStream(socket.getInputStream());
		start();
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void run() 
	{
		try{
		while(true)
		{
			if((din.readUTF()).equals("SEND"))
			{
				dndserver.textarea.append("\nSEND command received.");
				receive();
			}
		}}
		catch(Exception e){e.printStackTrace();}
	}
	public void receive()
	{
	try{
		String filename = din.readUTF();
		dndserver.textarea.append("\nFile name : "+filename);
		File file = new File(filename);
		fileout = new FileOutputStream(file);
		String str;int ch;
		do
		{
			str = din.readUTF();
			ch = Integer.parseInt(str);
			if(ch!=-1)
				fileout.write(ch);
		}
		while(ch!=-1);
		dndserver.textarea.append("\nFile received successfully");
	} 
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
} 		
