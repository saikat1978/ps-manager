package test;

import com.jezhumble.javasysmon.JavaSysMon;

public class BigProcess {

	public static void main(String[] args) {
		int pid = new JavaSysMon().currentPid();
		System.out.println(pid);
		while (true) {
			//System.out.println("running......... " + args[0]);
		}
	}

}
