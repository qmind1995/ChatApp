package com.networking.data;

import java.io.Serializable;

import com.networking.tags.Tags;

@SuppressWarnings("serial")
public class DataFile implements Serializable{

	private String openTags = Tags.FILE_DATA_OPEN_TAG;
	private String closeTags = Tags.FILE_DATA_CLOSE_TAG;
	public byte[] data;

	public DataFile(int size) {
		data = new byte[size];
	}
	
	public DataFile() {
		data = new byte[Tags.MAX_MSG_SIZE];
	}
}
/*
 * public void Copydatabase(InputStream inputStr, OutputStream outputStr)
			throws IOException {
		byte[] buffer = new byte[1024];
		int lenght;
		while ((lenght = inputStr.read(buffer)) > 0) {
			outputStr.write(buffer, 0, lenght);
		}
		inputStr.close();
		outputStr.close();
	}
 */
