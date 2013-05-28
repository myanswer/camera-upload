package cn.carcheck.picture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 文件管理器
 * */
public class FileManager {

	// private String m_url =
	// "http://192.168.1.217:18080/take-picture-service/FileUpload";
	private final Properties mProp = new Properties();

	public FileManager() {
	}

	public File getPath() {
		String path = this.mProp.getProperty(Const.car_dir_path);
		File file = new File(path);
		return file;
	}

	public Task doUpload() {
		Task task = new MyUploadTask(this);
		(new Thread(task)).start();
		return task;
	}

	class MyUploadTask implements Task {

		private final FileManager m_fm;
		private Exception mException;
		private boolean mIsFinish;

		public MyUploadTask(FileManager fileManager) {
			this.m_fm = fileManager;
		}

		@Override
		public void run() {
			try {

				String url = this.m_fm.getProperties().getProperty(
						Const.upload_url);

				File path = this.m_fm.getPath();
				File[] list = path.listFiles();
				for (File file : list) {
					FileUploadClient client = new FileUploadClient(url);
					client.sand(file, path.getName() + "/" + file.getName());
					file.delete();
				}
				path.delete();
				this._setSuccess();
			} catch (Exception e) {
				e.printStackTrace();
				this._setException(e);
			}
		}

		private void _setSuccess() {
			this._setFinish();

		}

		private void _setException(Exception e) {
			this.mException = e;
			this._setFinish();
		}

		private void _setFinish() {
			this.mIsFinish = true;
		}

		@Override
		public boolean isFinish() {
			return this.mIsFinish;
		}

		@Override
		public Exception getException() {
			return this.mException;
		}

		@Override
		public String getOutput() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public Properties getProperties() {
		return this.mProp;
	}

	public void save() {
		try {
			File file = this.getPropertiesFile();
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			OutputStream out = new FileOutputStream(file);
			this.mProp.store(out, "");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try {
			File file = this.getPropertiesFile();
			InputStream in = new FileInputStream(file);
			this.mProp.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getPropertiesFile() {
		File file = android.os.Environment.getExternalStorageDirectory();
		String app = this.getClass().getName();
		file = new File(file, "app/" + app + "/config.prop");
		return file;
	}

}
