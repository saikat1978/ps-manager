package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jezhumble.javasysmon.JavaSysMon;
import com.jezhumble.javasysmon.ProcessInfo;

public class ProcessTest {

	public static void main(String[] args) throws IOException {
		
		String command = "java -jar F:\\workspace-sts-3.9.3.RELEASE\\gs-serving-web-content-complete\\target\\gs-serving-web-content-0.1.0.jar";
		JavaSysMon sysMon = new JavaSysMon();
		Map<Integer, String> existingProcessMap = new HashMap<>();
		
		for (ProcessInfo pInfo : sysMon.processTable()) {
			if (pInfo.getCommand().contains("java") || pInfo.getCommand().contains("javaw")) {
				existingProcessMap.put(pInfo.getPid(), pInfo.getCommand());
				System.out.println("PID = " + pInfo.getPid() + ", CMD = " + pInfo.getCommand());
			}
			
		}
		
		
		System.out.println("-----------------------------------------------------------------------------------------------");
		
		ProcessBuilder builder = new ProcessBuilder(command.split(" "));
		builder.redirectError(Redirect.appendTo(new File("F:\\err1.txt")));
		builder.redirectOutput(Redirect.appendTo(new File("F:\\input1.txt")));
		builder.start();
		
		
	    
		int newPid = 0;
		for (ProcessInfo pInfo : sysMon.processTable()) {
			if (pInfo.getCommand().contains("java") || pInfo.getCommand().contains("javaw")) {
				if (!existingProcessMap.containsKey(pInfo.getPid())) {
					if (pInfo.getCommand().equalsIgnoreCase(command)) {
						existingProcessMap.put(pInfo.getPid(), pInfo.getCommand());
						System.out.println(pInfo.getPid() + ", command = " + pInfo.getCommand()); // process id of the server program
						newPid = pInfo.getPid();
					}
					
				}
			}
			
		}
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sysMon.killProcess(newPid); //un-comment it to kill the java server program. Comment the rest of the code before running it. 
	}

}
