package game.core.example.network;

import game.core.example.network.socket.GameSessionFactory;
import game.core.example.network.socket.ProtobufCmdInitializer;
import game.core.example.network.socket.ProtobufGameSystemHandler;
import game.core.network.server.ProtocolServer;

public class ServerMain {

	public static void main(String[] args) {
		//Json
//		ProtocolServer server = new ProtocolServer();
//		server.setChannelInitializer(new JsonProtocolServerInitializer());
//		server.setWarningTime(1);
//		server.setFrequencyLimit(1);
//		server.addCmdInitializer(new JsonCmdInitializer());
//		server.setSystemHandler(new JsonGameSystemHandler());
//		server.setSessionFactory(GameSessionFactory.factory);
//		server.setMessageIdName("cmdId");
//		server.start(3060);
		
		//http
//		HttpServer http = new HttpServer();
//		http.setHttpDefine(GameHttpDefine.class);
//		http.setMessageIdName("code");
//		http.start(3070);
		
		//protobuf
		ProtocolServer server = new ProtocolServer();
		server.setWarningTime(1);
		server.setFrequencyLimit(1);
		server.setIdleReaderSeconds(600);
		server.addCmdInitializer(new ProtobufCmdInitializer());
		server.setSystemHandler(new ProtobufGameSystemHandler());
		server.setSessionFactory(GameSessionFactory.factory);
		server.start(3060);
		
	}
}
