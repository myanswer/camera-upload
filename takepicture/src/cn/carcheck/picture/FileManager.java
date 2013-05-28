package cn.carcheck.picture;

import java.io.File;

/**
 * 文件管理器
 * */
public class FileManager {

	private String m_url = "http://192.168.1.217:18080/take-picture-service/FileUpload";
	private File m_path;

	public FileManager(File path) {
		this.m_path = path;
	}

	public File getPath() {

		return m_path;
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
				String url = this.m_fm.m_url;
				File path = this.m_fm.m_path;
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
	}
}
