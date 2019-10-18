package game.core.example.network.socket.handler.protobuf;

import game.core.example.network.cmd.Test.TestReq;
import game.core.example.network.cmd.Test.TestResp;
import game.core.example.network.socket.Session;
import game.core.example.network.socket.domain.Player;

public class ProtobufTestHandler extends ProtobufGameBaseHandler<TestReq> {


	@Override
	public boolean validate(int messageId,TestReq cmdData, Player owner, Session session) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void handler(int messageId,TestReq cmdData, Player player, Session session) throws Exception {
		
		System.out.println(String.format("messageId【%s】name【%s】age【%s】", messageId,cmdData.getName(),cmdData.getAge()));
		
		TestResp.Builder resp = TestResp.newBuilder();
		resp.setHint("I am server...");
		resp.setSucc(true);
		session.response(messageId,resp);
		
//		int playerId = cmdData.get("playerId").getAsInt();
//		GameSessionFactory.factory.bind(playerId, session);
//		System.out.println("testHandler:"+cmdData);
//		
//		System.out.println(GameSessionFactory.factory.getChannelSessions());
//		System.out.println(GameSessionFactory.factory.getOnlines());
//		
//		
//		JsonObject result = new JsonObject();
//		result.addProperty("nowTime", new Date().toString());
//		session.writeAndFlush(result);
	}


}
