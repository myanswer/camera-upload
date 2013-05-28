package take.picture.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileUpload
 */
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		out.println(this + ".get ........ [ok]");
		out.println("save to path : " + this.getBaseDir());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String remotePath = request.getHeader("Remote-Path");
		File baseDir = this.getBaseDir();
		File file = new File(baseDir, remotePath);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(file);

		int cbTotal = 0;
		InputStream in = request.getInputStream();
		byte[] buff = new byte[128];
		for (int cb = in.read(buff); cb > 0; cb = in.read(buff)) {
			fos.write(buff, 0, cb);
			cbTotal += cb;
		}

		fos.flush();
		fos.close();

		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);

		PrintWriter out = response.getWriter();
		out.println(this + ".post ........ [ok]");
		out.println("save to " + file.getAbsolutePath());
		out.println("size(in byte) " + cbTotal);
	}

	private File getBaseDir() {

		try {
			URL url = this.getClass().getProtectionDomain().getCodeSource()
					.getLocation();
			File file = new File(url.toURI());

			String path = file.getAbsolutePath();
			int index = path.indexOf("WEB-INF");
			if (index > 0) {
				path = path.substring(0, index);
			}

			file = new File(path, "upload");
			return file;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

}
