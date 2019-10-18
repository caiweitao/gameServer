package game.core.example.network.socket;

import game.core.network.cmd.JsonServerCmdInitializer;

public class JsonCmdInitializer extends JsonServerCmdInitializer {

	@Override
	public void init() {
		load(GameCmdDefine.class);

	}

}
