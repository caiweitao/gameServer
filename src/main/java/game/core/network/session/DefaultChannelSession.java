package game.core.network.session;

import io.netty.channel.Channel;

public class DefaultChannelSession extends ChannelSession<Integer> {

	public DefaultChannelSession(Channel parent) {
		super(parent);
	}

}
