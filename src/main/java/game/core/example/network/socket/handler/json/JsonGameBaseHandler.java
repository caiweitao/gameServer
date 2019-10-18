package game.core.example.network.socket.handler.json;

import game.core.example.network.socket.Session;
import game.core.example.network.socket.domain.Player;
import game.core.network.handler.JsonServerHandler;

public abstract class JsonGameBaseHandler extends JsonServerHandler<Player, Session> {

	@Override
	public Player getOwner(Session session) {
		if (session.getOwner() != null) {
			Player player = new Player();
			player.setId(session.getOwner());
			return player;
			// TODO :查询出player
		}
		return null;
	}
}
