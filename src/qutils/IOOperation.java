package qutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import qJob.QJob;

public class IOOperation {

	public boolean dirExits(String dirPath) {
		if (dirPath == null || dirPath.isEmpty())
			return false;
		File dir = new File(dirPath);
		return dir.exists() && dir.isDirectory();
	}
	public boolean createDir(String dirPath) {
 		File dir = new File(dirPath);
 		if(dir.exists() && dir.isDirectory()) return false;
		return dir.mkdir();
	}


	public boolean fileExits(String filePath) {
		if (filePath == null || filePath.isEmpty())
			return false;
		File file = new File(filePath);
		return file.exists() && (!file.isDirectory());
	}

	public boolean deleteFile(String filePath) {
		if (filePath == null || filePath.isEmpty())
			return false;
		File file = new File(filePath);
		return file.delete();
	}
	
	public String getFileExtension(Path file) {
		String fileName = file.toString();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
		else
			return "";
	}
	
	public ArrayList<Path> getFilesList(String dirPath) 
	{
		return this.getFilesList(dirPath,"*ALL");
	}
	

	public ArrayList<Path> getFilesList(String dirPath, String fileExtension) {
		ArrayList<Path> fileList = new ArrayList<Path>();
		if (this.dirExits(dirPath)) {
			Path path = Paths.get(dirPath);
			try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
				for (Path file2 : dir) {
					if (!Files.isDirectory(file2)) {
						if (this.getFileExtension(file2).equalsIgnoreCase(fileExtension) || fileExtension.equalsIgnoreCase("*ALL")) {
							fileList.add(file2);
						}
					}
				}
			} catch (IOException x) {
				 
				x.printStackTrace();
			}
		}

		return fileList;
	}
}
