package qJob.qlocalJob;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import qJob.QJob;
import qJob.qlocalJob.qServers.QServer;
import qutils.IOOperation;
import qutils.StringCipher;

public class QServerDB {

	private final String dirLocation = "servers";
	private final String fileExtension = "SRV";
	private int largestId =0;
 
	 
	






	public boolean saveServer(QServer server) {
		QJob.getCurrentJob().getIoOperation().createDir(this.dirLocation);
		boolean output = false;
		if (server.isChanged()) {
			if(server.getServerID() == 0) server.setServerID(++this.largestId);
			String fileName = this.dirLocation + File.separator + Integer.toString(server.getServerID()) + "."+ this.fileExtension;

			if (QJob.getCurrentJob().getIoOperation().fileExits(fileName)) {
				QJob.getCurrentJob().getIoOperation().deleteFile(fileName);
				output = true;
			}
			if (!server.isDeleted()) {
				output = this.commitServerChanges(server);
				output = true;
				server.setChanged(false);
			}

		}

		return output;
	}
	
	public boolean deleteServer(int serverId)
	{
		QServer server = new QServer();
		server.setServerID(serverId);
		server.setChanged(true);
		server.setDeleted(true);
		return this.saveServer(server);
	}

	private boolean commitServerChanges(QServer server) {
		boolean output = true;
		String fileName = this.dirLocation + File.separator + Integer.toString(server.getServerID())+ "." +this.fileExtension;
	//	fileName = "hello.txt";
		 File file = new File(fileName);
		 try 
		 { file.createNewFile();
		 }
	 catch (IOException e) {
		// do something
		 
		e.printStackTrace();
		output = false;
	}
		 
		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

			writer.println("ID:" + Integer.toString(server.getServerID()));
			writer.println("SERVERNAME:" + server.getServerName());
			writer.println("SERVERIP:" + server.getServerIp());
			writer.println("USERNAME:" + server.getUserName());
			writer.println("PASSWORD:" + StringCipher.encrypt(server.getPassword()));
			writer.println("NAMING:" + server.getNaming());
			for (String lib : server.getLibl()) {
				if(lib != null && !lib.isEmpty()) writer.println("LIB:" + lib);
			}

		} catch (IOException e) {
			// do something
			 
			e.printStackTrace();
			output = false;
		}
		
 
		
		
		return output;

	}

	public int saveServer(List<QServer> serverList) {
		int outCount = 0;
		for (QServer server : serverList) {
			if (this.saveServer(server))
				outCount++;
		}

		return outCount;
	}

	public List<QServer> getServerList() {
		List<QServer> serverList = new ArrayList<QServer>();
		for (Path serverFile : QJob.getCurrentJob().getIoOperation().getFilesList(this.dirLocation,this.fileExtension)) {
			try (BufferedReader br = new BufferedReader(new FileReader(serverFile.toFile()))) {
				String line;
				QServer server = new QServer();

				mainloop: while ((line = br.readLine()) != null) {
					if (line.isEmpty())
						continue;

					// id:1
					// ServerName: RZKH.DE
					int divLocation = line.indexOf(":"); // if : not found
					if (divLocation < 0)	continue;
					

					String key = line.substring(0, divLocation);
					String value = line.substring(divLocation + 1);
					
					if(value==null || value.isEmpty()) continue mainloop;
					
					switch (key.toUpperCase().replaceAll(" ", "")) {
					case "ID": // Server ID
						if (!line.isEmpty()) {
							try {
								int id = Integer.parseInt(value.trim());
								server.setServerID(id);
							} catch (NumberFormatException exp) {
								break mainloop;
							}
						}
						break;
					case "SERVERNAME": // Server name
						server.setServerName(value.trim());
						break;
					case "SERVERIP": // Server Ip
						server.setServerIp(value.trim());
						break;
					case "USERNAME": // server user Name
						server.setUserName(value.trim());
						break;
					case "PASSWORD": // server password
						server.setPassword(StringCipher.decrypt(value.trim()));
						break;
					case "NAMING": // naming
						server.setNaming(value.trim());
						break;
					case "LIB": // libl
						server.getLibl().add(value);
						break;

					}

				} // end of while loop

				if (server.getServerID() > 0) 
					if(this.largestId < server.getServerID()) this.largestId = server.getServerID();
					serverList.add(server);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return serverList;
	}

	
 
}
