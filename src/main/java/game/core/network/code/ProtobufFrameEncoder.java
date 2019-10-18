package game.core.network.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
* @author caiweitao
* @Date 2019年9月23日
* @Description protobuf 编码器 将要发送的数据加上头部传递出去
*/
public class ProtobufFrameEncoder extends MessageToByteEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int bodyLen = msg.readableBytes();
		byte[] lengthBytes = ByteUtilities.int2bytes(bodyLen + 4);//头部数据
		out.ensureWritable(lengthBytes.length + bodyLen);//确保此缓冲区中有足够的可写字节（head+body）
		out.writeBytes(lengthBytes);//将head写入out
		out.writeBytes(msg, msg.readerIndex(), bodyLen);//将body写入out
	}

}
