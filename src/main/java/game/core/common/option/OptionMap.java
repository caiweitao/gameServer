package game.core.common.option;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelOption;

public class OptionMap {

	public static Map<ChannelOption<?>, OptionDescription> optionMap = new HashMap<>();

	static {
		add(new OptionDescription(ChannelOption.SO_BACKLOG, "排队等待的最大连接个数"));
		add(new OptionDescription(ChannelOption.ALLOCATOR, "字节缓存区分配器"),
				new String[]{"PooledByteBufAllocator", "缓存方式的ByteBuf生成工具类(不依赖GC,jemalloc方式)"},
				new String[]{"UnpooledByteBufAllocator", "非缓存方式ByteBuf生成工具类"});
		add(new OptionDescription(ChannelOption.CONNECT_TIMEOUT_MILLIS, "连接超时时间长度(单位毫秒)"));
		add(new OptionDescription(ChannelOption.MESSAGE_SIZE_ESTIMATOR, "传输数据包大小估计器"));
		add(new OptionDescription(ChannelOption.RCVBUF_ALLOCATOR, "接收缓冲区分配器"),
				new String[]{"FixedRecvByteBufAllocator", "固定长度的接收缓冲区分配器"},
				new String[]{"AdaptiveRecvByteBufAllocator", "容量动态调整的接收缓冲区分配器"});
		add(new OptionDescription(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, "写缓冲高水位标记"));
		add(new OptionDescription(ChannelOption.SO_RCVBUF, "接收缓存区大小"));
		add(new OptionDescription(ChannelOption.SO_REUSEADDR, "端口释放后立即就可以被再次使用"));
		add(new OptionDescription(ChannelOption.MAX_MESSAGES_PER_READ, "每个读数据包大小"));
	}

	private static void add(OptionDescription item, String[]... values) {
		if (values != null && values.length > 0) {
			String[][] var5 = values;
			int var4 = values.length;

			for (int var3 = 0; var3 < var4; ++var3) {
				String[] value = var5[var3];
				item.addOptionDescription(value[0], value[1]);
			}
		}

		optionMap.put(item.getOption(), item);
	}
}
