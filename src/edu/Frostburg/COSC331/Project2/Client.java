package edu.Frostburg.COSC331.Project2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * @author aarondavis
 *
 */
public class Client {

	private DatagramSocket socket;
	private FileToTransfer fileToTransfer;
	private String source;
	private String destination;
	private String host = "localHost";
	
	/**
	 * No constructor needed
	 */
	public Client(){}
	
	/**
	 * Makes connection with the server
	 */
	public void makeConnection() {
		
		try {
			
			//Socket created and IP address made.
			socket = new DatagramSocket();
			InetAddress IP = InetAddress.getByName(host);
			
			byte[] incomingData = new byte[1024];
			fileToTransfer = getFileToTransfer();
			
			//Stream that reads bytes out the stream
			//Files are then written to outStream
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ObjectOutputStream objOutStream = new ObjectOutputStream(outStream);
			objOutStream.writeObject(fileToTransfer);
			
			byte[] data = outStream.toByteArray();
			
			//Sending packet to sever
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, IP, 4000);
			socket.send(sendPacket);
			
			System.out.println("File sent from client");
			
			//Receives packet from sever
			DatagramPacket incoming = new DatagramPacket(incomingData, incomingData.length);
			socket.receive(incoming);
			
			String response = new String(incoming.getData());
			System.out.println("Response from server: " + response);
			Thread.sleep(2000);
			System.exit(0);
			
		} catch (SocketException e) {
			System.err.println("Server is facing some issues. Please retry.");
		} catch (UnknownHostException e) {
			System.err.println("Sorry. That host is not available.");
		} catch (IOException e) {
			System.err.println("Could not write to disk. Please try again.");
		} catch (InterruptedException e) {
			System.err.println("Error within System.");
		}
	}
	
	/**
	 * Readies file to be transferred
	 * @return FileToTransfer object
	 */
	public FileToTransfer getFileToTransfer() {
		
		FileToTransfer fileToTransfer = new FileToTransfer();
		
		String fileName = source.substring(source.lastIndexOf("/") + 1, source.length());
		String path = source.substring(0, source.lastIndexOf("/") + 1);
		
		fileToTransfer.setDestination(destination);
		fileToTransfer.setFileName(fileName);
		fileToTransfer.setSource(path);
		
		File file = new File(source);
		
		//Checks whether file is a "normal" file
		//A file is normal if it's not a directory, amongst other things
		if(file.isFile()) {
			try {
				
				//Used to read primitive data types
				DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
			
				long length = file.length();
				byte[] fileBytes = new byte[(int)length];
				int read = 0;
				int numberRead = 0;
				
				while(read < fileBytes.length && (numberRead = inputStream.read(fileBytes, read, fileBytes.length - read)) >= 0){
					read = read + numberRead;
				}
				
				fileToTransfer.setFileSize(length);
				fileToTransfer.setFileData(fileBytes);
				fileToTransfer.setStatus("Has worked");
				
				inputStream.close();
				
			} catch (FileNotFoundException e) {
				System.err.println("The file was not found.");
				fileToTransfer.setStatus("Error!");
			} catch (IOException e) {
				System.err.println("Could not read from disk. Please try again.");
			}
		}
		else {
			System.out.println("Path specified is not pointing to a file.");
			fileToTransfer.setStatus("Error!");
		}
		return fileToTransfer;
	}
}
