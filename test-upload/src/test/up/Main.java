package test.up;

import java.io.File;

public class Main {

	public static void main(String[] arg) {

		try {

			String url = "http://localhost:18080/take-picture-service/FileUpload";
			FileUploadClient client = new FileUploadClient(url);
			File file = new File("/tmp/abc.txt");
			client.sand(file, file.getName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
