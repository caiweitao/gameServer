package game.core.code;

import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
* @author caiweitao
* @Date 2019年9月23日
* @Description protobuf解码器，将客户端传过来的ByteBuf去掉头部后剩下的数据往下传递给ProtobufDecoder
*/
public class ProtobufFrameDecoder extends ByteToMessageDecoder {

	private final int headLen = 4;//消息头部长度（字节数），头部记录了消息的总长度
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		if (in.isReadable() && in.readableBytes() > headLen) {
			byte[] headBytes = new byte[headLen];
			in.readBytes(headBytes);//假如原本readerIndex=0,那此操作后readerIndex=headLen
			int length = ByteUtilities.bytes2int(headBytes) - headLen;//实际数据长度=头部记录的值-头部长度
			if (in.readableBytes() < length) {//去掉头部后的数据 < length
				in.resetReaderIndex();//重置读取位置
				return;
			}

			ByteBuf byteBuf = in.readRetainedSlice(length);//将剩下的数据(此时in的数据已经去掉头部)放到buteBuf
			out.add(byteBuf);
		} else {
			in.resetReaderIndex();
		}

	}
	
	public static void main(String[] args) {
		Charset c = Charset.forName("UTF-8");
		ByteBuf bb = Unpooled.copiedBuffer("netty in action rocks!", c);
		System.out.println(bb.toString(c));
		System.out.println(String.format("readerInder【%s】writerInder【%s】", bb.readerIndex(),bb.writerIndex()));
		
		bb.markReaderIndex();
		byte[] headBytes = new byte[4];
		bb.readBytes(headBytes);
		System.out.println(String.valueOf(headBytes));
		
		System.out.println(bb.toString(c));
		System.out.println(String.format("readerInder【%s】writerInder【%s】", bb.readerIndex(),bb.writerIndex()));
	
	
		ByteBuf byteBuf = bb.readRetainedSlice(18);
		System.out.println(byteBuf.toString(c));
	}

}
