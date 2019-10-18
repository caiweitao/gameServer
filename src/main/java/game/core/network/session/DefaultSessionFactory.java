package game.core.network.session;

import io.netty.channel.Channel;

public class DefaultSessionFactory extends SessionFactory<Integer, DefaultChannelSession> {

	@Override
	public ChannelSession<Integer> newChannelSession(Channel channel) {
		System.out.println("DefaultSessionFactory.newChannelSession====================");
		return new DefaultChannelSession(channel);
	}

}
