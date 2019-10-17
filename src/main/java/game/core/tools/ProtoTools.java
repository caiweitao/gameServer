package game.core.tools;

import java.io.IOException;

/**
* @author caiweitao
* @Date 2019年9月25日
* @Description proto生成Java
*/
public class ProtoTools {

	public static void main(String[] args) {
		String protoBasePath = System.getProperty("user.dir") + "/protobuf/";
		
//		String cmd = String.format("cmd /c %s: && cd \"%s\" && protoc_all_copy.bat",
//				protoBasePath.split(":")[0], protoBasePath);
		protoToJava(protoBasePath);
	}
	
	public static void protoToJava (String protoBasePath) {
		String cmd = String.format("cmd /c cd \"%s\" && protoc_all_copy.bat", protoBasePath);
		executeCommand(cmd);
	}
	
	public static void executeCommand(String command) {
		try {
			Process	p = Runtime.getRuntime().exec(command);
			p.waitFor();
			p.destroy();
		} catch (IOException var5) {
			var5.printStackTrace();
		} catch (InterruptedException var6) {
			var6.printStackTrace();
		}

	}
}
