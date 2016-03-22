package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileCompressUtils {

	/**
	 * 方法说明：解压缩
	 *
	 * @param zipFileName： 需要解压缩的文件路径
	 * @param outputDirectory： 解压缩后文件所放入的文件路径
	 * @throws IOException
	 */
	public static void unzip(String zipFileName, String outputDirectory)
			throws IOException {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFileName);
			
			@SuppressWarnings("rawtypes")
			Enumeration e = zipFile.entries();
			ZipEntry zipEntry = null;
			File dest = new File(outputDirectory);
			dest.mkdirs();
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				String entryName = zipEntry.getName();
				InputStream in = null;
				FileOutputStream out = null;
				try {
					if (zipEntry.isDirectory()) {
						String name = zipEntry.getName();
						name = name.substring(0, name.length() - 1);

						File f = new File(outputDirectory + File.separator
								+ name);
						f.mkdirs();
					} else {
						int index = entryName.lastIndexOf("\\");
						if (index != -1) {
							File df = new File(outputDirectory + File.separator
									+ entryName.substring(0, index));
							df.mkdirs();
						}
						index = entryName.lastIndexOf("/");
						if (index != -1) {
							File df = new File(outputDirectory + File.separator
									+ entryName.substring(0, index));
							df.mkdirs();
						}
						File f = new File(outputDirectory + File.separator
								+ zipEntry.getName());
						// f.createNewFile();
						in = zipFile.getInputStream(zipEntry);
						out = new FileOutputStream(f);

						int c;
						byte[] by = new byte[1024];

						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
						out.flush();
					}
//					
				} catch (IOException ex) {
					ex.printStackTrace();
					throw new IOException("解压失败：" + ex.toString());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException ex) {
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException ex) {
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new IOException("解压失败：" + ex.toString());
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
					File file=new File(zipFileName);
					file.delete();
				} catch (IOException ex) {
				}
			}
		}
	}
}
