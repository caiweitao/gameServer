package game.core.example.network.socket;

import game.core.network.session.ChannelSession;
import io.netty.channel.Channel;

public class Session extends ChannelSession<Integer> {

	public Session(Channel parent) {
		super(parent);
	}

}
