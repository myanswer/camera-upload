package cn.carcheck.picture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadClient {

	private String mURL;

	public FileUploadClient(String url) {
		this.mURL = url;
	}

	public void sand(File localFile, String remotePath) {

		try {
			URL url = new URL(this.mURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Remote-Path", remotePath);

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			OutputStream out = conn.getOutputStream();
			FileInputStream fis = new FileInputStream(localFile);
			byte[] buff = new byte[128];
			for (int cb = fis.read(buff); cb > 0; cb = fis.read(buff)) {
				out.write(buff, 0, cb);
			}
			out.flush();

			int code = conn.getResponseCode();
			String msg = conn.getResponseMessage();
			System.out.println("http " + code + " " + msg);

			InputStream in = conn.getInputStream();
			for (int cb = in.read(buff); cb > 0; cb = in.read(buff)) {
				System.out.write(buff, 0, cb);
			}
			System.out.println();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
