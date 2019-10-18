package game.core.example.network.socket.handler.json;

import java.util.Date;

import com.google.gson.JsonObject;

import game.core.example.network.socket.GameSessionFactory;
import game.core.example.network.socket.Session;
import game.core.example.network.socket.domain.Player;

public class JsonTestHandler extends JsonGameBaseHandler {


	@Override
	public boolean validate(int messageId,JsonObject cmdData, Player owner, Session session) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void handler(int messageId,JsonObject cmdData, Player player, Session session) throws Exception {
		int playerId = cmdData.get("playerId").getAsInt();
		GameSessionFactory.factory.bind(playerId, session);
		System.out.println("testHandler:"+cmdData);
		
		System.out.println(GameSessionFactory.factory.getChannelSessions());
		System.out.println(GameSessionFactory.factory.getOnlines());
		
		
		JsonObject result = new JsonObject();
		result.addProperty("nowTime", new Date().toString());
		session.response(result);
	}


}
