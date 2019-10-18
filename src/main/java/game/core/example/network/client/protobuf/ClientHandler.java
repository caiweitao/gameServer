package game.core.example.network.client.protobuf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;

import game.core.network.cmd.Cmd.ServerCmdData;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
* @author caiweitao
* @Date 2019年9月24日
* @Description 
*/
public abstract class ClientHandler<M extends MessageLite> {

	private MessageLite prototype;

	public ClientHandler() {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<M> paraType = (Class) parameterizedType.getActualTypeArguments()[0];
		Method method = null;

		try {
			method = paraType.getMethod("getDefaultInstance");
			this.prototype = (MessageLite) method.invoke(paraType);
		} catch (NoSuchMethodException var5) {
			var5.printStackTrace();
		} catch (SecurityException var6) {
			var6.printStackTrace();
		} catch (IllegalAccessException var7) {
			var7.printStackTrace();
		} catch (IllegalArgumentException var8) {
			var8.printStackTrace();
		} catch (InvocationTargetException var9) {
			var9.printStackTrace();
		}

	}

	public MessageLite getPrototype() {
		return this.prototype;
	}

	public void dispatch(ChannelHandlerContext ctx, ServerCmdData cmdData) throws Exception {
		ByteString data = cmdData.getData();
		M m = (M) (!this.prototype.isInitialized() && data.isEmpty() ? null:(MessageLite) this.prototype.getParserForType().parseFrom(data));
		this.handle(ctx.channel(), m);
	}

	public abstract void handle(Channel var1, M var2);
}
