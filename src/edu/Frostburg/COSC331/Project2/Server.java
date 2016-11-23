package edu.Frostburg.COSC331.Project2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 
 * @author aarondavis
 *
 */
public class Server {

	private DatagramSocket socket;
	private FileToTransfer fileToTransfer;
	
	/**
	 * Constructor(not used)
	 */
	public Server() {}
	
	/**
	 * 
	 */
	public void listenSocket() {
		
		try {
			
			socket = new DatagramSocket(4000);
			byte[] incomingData = new byte[1024*1000*50];
			
			while(true) {
				
				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				socket.receive(incomingPacket);
				
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				
				fileToTransfer = (FileToTransfer)is.readObject();
				
				if(fileToTransfer.getStatus().equalsIgnoreCase("Error")){
					System.out.println("Something has happened to the packet via Client side. Please restart program and try again");
					System.exit(0);
				}
				
				//Write file to hard disk
				createFile();
				
				InetAddress IP = incomingPacket.getAddress();
				int port = incomingPacket.getPort();
				String reply = "Thank you.";
				
				byte[] replyBytes = reply.getBytes();
				DatagramPacket replyPacket = new DatagramPacket(replyBytes, replyBytes.length, IP, port);
				
				socket.send(replyPacket);
				//Thread waits 3 second
				Thread.sleep(3000);
				System.exit(0);
			}
			
		} catch (SocketException e) {
			System.err.println("The Server is having an issue. Please try again.");
		} catch (IOException e) {
			System.err.println("Sorry. The Server isn't receiving information. Please try again.");
		} catch (ClassNotFoundException e) {
			System.err.println("Sorry. An internal error happened. Please try to run the program again.");
		} catch (InterruptedException e) {
			System.err.println("Program faced an issue. Please rerun the program.");
		}
	}
	
	/**
	 * Create and write file
	 */
	public void createFile() {
		
		String output = fileToTransfer.getDestination() + fileToTransfer.getFileName();
		
		
		//Will make a directory for the destination file if not already made
		if(!new File(fileToTransfer.getDestination()).exists()) {
			new File(fileToTransfer.getDestination()).mkdirs();
		}
		
		//Give the directory and name of the file
		File destinationFile = new File(output);
		FileOutputStream outputStream;
		
		try {
			//
			outputStream = new FileOutputStream(destinationFile);
			outputStream.write(fileToTransfer.getFileData());
			outputStream.flush();
			outputStream.close();
			System.out.println("Output file: " + output + "has been successfully saved.");
			
		} catch (FileNotFoundException e) {
			System.err.println("This file could not be found, sorry! Please try again.");
		} catch (IOException e) {
			System.err.println("Sorry. Could not write to file. Please try again.");
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.createFile();
	}
}
