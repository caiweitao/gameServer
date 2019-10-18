package game.core.example.network.http;

import game.core.example.network.http.handler.ShutdownGmHandler;
import game.core.example.network.http.handler.Test2Handler;
import game.core.example.network.http.handler.TestHttpHandler;
import game.core.network.server.http.HttpDefine;

public class GameHttpDefine {
	
	@HttpDefine(desc = "test", handler = TestHttpHandler.class)
	public static final int TEST = 100;
	@HttpDefine(desc = "test2", handler = Test2Handler.class)
	public static final int TEST2 = 101;
	@HttpDefine(desc = "关服", handler = ShutdownGmHandler.class)
	public static final int SHUTDOWN = 105;

}
