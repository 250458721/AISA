package com.icbc.zoro.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSONObject;

public class DataFormatJob {
	private static Logger logger = LogManager.getLogger(DataFormatJob.class.getName());
	private static ArrayList<String> filelist;
	private static int fileCount;

	public static void main(String[] args) throws Exception {

		String filePath = "D:\\zoro_data\\neg";
		String targetPath = "D:\\zoro_data\\neg\\total.json";
		File targetFile = new File(targetPath);
		targetFile.delete();
		getFiles(filePath, targetPath);
		
		filePath = "D:\\zoro_data\\pos";
		targetPath = "D:\\zoro_data\\pos\\total.json";
		targetFile = new File(targetPath);
		targetFile.delete();
		getFiles(filePath, targetPath);
		
	}

	/*
	 * 通过递归得到某一路径下所有的目录及其文件
	 */
	static void getFiles(String filePath, String targetPath) throws IOException {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				/*
				 * 递归调用
				 */
				getFiles(file.getAbsolutePath(), targetPath);
				filelist.add(file.getAbsolutePath());
				// System.out.println("显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath());
			} else {
				if (file.getName().toLowerCase().endsWith(".json")) {
					fileCount++;
					System.out.println("" + fileCount + file.getName()+ " indexed,json=" + readToString(file.getAbsolutePath()));
					JSONObject temp = JSONObject.parseObject(readToString(file.getAbsolutePath()));
					String content = ZoroUtil.getMainContent(temp.getString("content"), "英镑");
					if(null!=content && !"".equals(content)){
						writeFile(temp.getString("content"), targetPath);
					}
				}
			}
		}
	}

	public static void writeFile(String content, String targetPath) throws IOException {

		try {
			// 替换英文字符
			content = content.replaceAll("[^\\u4e00-\\u9fa5]", "");
			// 替换中文 
			content = content.replaceAll("宋体", "")
						.replaceAll("图片", "")
						.replaceAll("图表来源", "");

			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(targetPath, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.write((content+"\r\n").getBytes("utf-8"));
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 根据文件名获取文件内容
	 */
	public static String readToString(String fileName) {
		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (Exception e) {
			logger.error("DatamineJob->readToString文件读取失败 ", e);
		}
		try {
			return new String(filecontent, encoding);
		} catch (Exception e) {
			logger.error("DatamineJob->readToString结果创建失败 ", e);
			return null;
		}
	}
}
