package game.core.network.code;
/**
* @author caiweitao
* @Date 2019年9月23日
* @Description 
*/
public class ByteUtilities {

	private static final int BASE_ZERO = 28;
	private static final int MAX_NUM = 99999999;

	public static byte[] int2bytes(int intData) {
		if (intData <= MAX_NUM) {
			byte[] resultByte = new byte[4];

			for (int i = 3; i >= 0; --i) {
				if (intData > 0) {
					resultByte[i] = (byte) (intData % 100 + BASE_ZERO);
					intData /= 100;
				} else {
					resultByte[i] = BASE_ZERO;
				}
			}

			return resultByte;
		} else {
			return null;
		}
	}

	public static int bytes2int(byte[] data) {
		int wei = 1000000;
		int result = 0;
		if (data.length != 4) {
			return -1;
		} else {
			for (int i = 0; i < 4; ++i) {
				if (data[i] > BASE_ZERO) {
					switch (i) {
						case 1 :
							wei = 10000;
							break;
						case 2 :
							wei = 100;
							break;
						case 3 :
							wei = 1;
					}

					result += (data[i] - BASE_ZERO) * wei;
				}
			}

			return result;
		}
	}

	public static void printBytesInt(byte... data) {
		int i = 0;

		for (int j = data.length; i < j; ++i) {
			int item = data[i] - BASE_ZERO;
			if (item < 10) {
				System.out.print(0);
			}

			System.out.print(item);
		}

	}

	public static String asHex(byte[] bytes) {
		return asHex(bytes, (String) null);
	}

	public static String asHex(byte[] bytes, String separator) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < bytes.length; ++i) {
			String code = Integer.toHexString(bytes[i] & 255);
			if ((bytes[i] & 255) < 16) {
				sb.append('0');
			}

			sb.append(code);
			if (separator != null && i < bytes.length - 1) {
				sb.append(separator);
			}
		}

		return sb.toString();
	}
}
