package game.core.example.network.socket;

import game.core.example.network.socket.handler.protobuf.ProtobufTestHandler;
import game.core.network.cmd.CmdDefine;

public class GameCmdDefine {
	
	@CmdDefine(desc = "test", handler = ProtobufTestHandler.class)
	public static final int TEST = 1001;
	
//	@CmdDefine(desc = "test", handler = JsonTestHandler.class)
//	public static final int TEST2 = 1001;

}
