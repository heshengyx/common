package com.myself.common.utils;

public class UIDGeneratorUtil {
	private static long lastTime = System.currentTimeMillis(); //��ǰϵͳʱ��
	private static short lastCount = -32768;
	private static Object mutex = new Object();
	private static long ONE_SECOND = 1000L;

	public UIDGeneratorUtil() {
	}

	public static String getUID() {
		long l = 0L;
		short word0 = 0;
		int i = 0;
		synchronized (mutex) {
			if (lastCount == 32767) {
				for (boolean flag = false; !flag;) {
					l = System.currentTimeMillis();
					if (l < lastTime + ONE_SECOND) {
						try {
							Thread.currentThread();
							Thread.sleep(ONE_SECOND);
						} catch (InterruptedException interruptedexception) {
						}
					} else {
						lastTime = l;
						lastCount = -32768;
						flag = true;
					}
				}
			} else {
				l = lastTime;
			}
			word0 = lastCount++;
			i = getHostUniqueNum();
		}
		String s = Integer.toString(i, 16) + Long.toString(l, 16)
				+ Integer.toString(word0, 16).substring(1);
		if (s.length() > 21)
			s = s.substring(s.length() - 21);
		else
			for (int j = 0; j < 21 - s.length(); j++) {
				s += "0";
			}
		return s;
	}

	private static int getHostUniqueNum() {
		return (new Object()).hashCode();
	}
	
	public static void main(String[] args) {
		System.out.println(getUID());
	}
}
