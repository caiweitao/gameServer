package game.core.example.network.socket;

import game.core.network.session.ChannelSession;
import game.core.network.session.SessionFactory;
import io.netty.channel.Channel;

public class GameSessionFactory extends SessionFactory<Integer, Session> {
	public static final GameSessionFactory factory = new GameSessionFactory();

	private GameSessionFactory() {
	}

	@Override
	public ChannelSession<Integer> newChannelSession(Channel parent) {
		System.out.println(String.format("创建新的Session【%s】======", parent));
		return new Session(parent);
	}

}
