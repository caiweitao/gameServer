package game.core.example.network.socket.handler.protobuf;

import com.google.protobuf.MessageLite;

import game.core.example.network.socket.Session;
import game.core.example.network.socket.domain.Player;
import game.core.network.handler.ProtobufServerHandler;

public abstract class ProtobufGameBaseHandler<Req extends MessageLite> extends ProtobufServerHandler<Player, Session,Req> {

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
