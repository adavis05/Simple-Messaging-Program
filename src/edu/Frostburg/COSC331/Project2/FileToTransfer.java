package edu.Frostburg.COSC331.Project2;
import java.io.Serializable;

/**
 * 
 * @author aarondavis
 * @version 1.0
 * 
 */

/**
* The Serializable class allows an object to be represented by a sequence of bytes
* This sequence of bytes include the Object's data as well as the data type
*/
public class FileToTransfer implements Serializable {

	//Needed when using Serializable 
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor not used
	 */
	public FileToTransfer() {}
	
	private String destination;
	private String source;
	private String fileName;
	private long fileSize;
	private byte[] fileData;
	private String status;
	
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}
	
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	/**
	 * @return the fileData
	 */
	public byte[] getFileData() {
		return fileData;
	}
	
	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
