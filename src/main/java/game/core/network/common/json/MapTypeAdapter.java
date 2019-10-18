package game.core.network.common.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * 自定义Gson类型适配器
 * @author caiweitao
 * 2019年9月19日
 */
public class MapTypeAdapter extends TypeAdapter<Object> {

	@Override
	public void write(JsonWriter out, Object value) throws IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * Gson转map时 重写类型转换
	 */
	@Override
	public Object read(JsonReader in) throws IOException {
		JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY:
                List<Object> list = new ArrayList<Object>();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(read(in));
                }
                in.endArray();
                return list;

            case BEGIN_OBJECT:
                Map<String, Object> map = new TreeMap<String, Object>();
                in.beginObject();
                while (in.hasNext()) {
                    map.put(in.nextName(), read(in));
                }
                in.endObject();
                return map;

            case STRING:
                return in.nextString();
            case NUMBER://改写数字的处理逻辑。
                double dbNum = in.nextDouble();
                if (dbNum > Long.MAX_VALUE) {
                    return dbNum;
                }

                // 判断数字是否为整数值
                long lngNum = (long) dbNum;
                if (dbNum == lngNum) {
                    return lngNum;
                } else {
                    return dbNum;
                }

            case BOOLEAN:
                return in.nextBoolean();

            case NULL:
                in.nextNull();
                return null;

            default:
                throw new IllegalStateException();
        }
	}

}
