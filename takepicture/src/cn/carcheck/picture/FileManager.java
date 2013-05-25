package cn.carcheck.picture;

import java.io.File;

public class FileManager {
	private File m_path;

	public FileManager(File path) {
		this.m_path = path;
	}

	public File getPath() {

		return m_path;
	}
}
