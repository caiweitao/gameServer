package game.core.example.network.socket;

import game.core.network.cmd.ProtobufServerCmdInitializer;

public class ProtobufCmdInitializer extends ProtobufServerCmdInitializer {

	@Override
	public void init() {
		load(GameCmdDefine.class);

	}

}
