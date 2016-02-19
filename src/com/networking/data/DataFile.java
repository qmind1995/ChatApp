package com.networking.data;

import java.io.Serializable;

import com.networking.tags.Tags;

@SuppressWarnings("serial")
public class DataFile implements Serializable{

	private String openTags = com.networking.tags.Tags.FILE_OPEN_TAG;
	private String closeTags = com.networking.tags.Tags.FILE_END_TAG;
	public byte[] data;

	public DataFile(int size) {
		data = new byte[size];
	}
	
	public DataFile() {
		data = new byte[Tags.MAX_MSG_SIZE];
	}
}

