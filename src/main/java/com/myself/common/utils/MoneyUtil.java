package com.myself.common.utils;

import java.math.BigDecimal;

public class MoneyUtil {

	public static long toLong(String strAmt) {
		long amount = 0L;
		if (strAmt != null && !"".equals(strAmt)) {
			BigDecimal amt = new BigDecimal(strAmt);
			BigDecimal hundred = new BigDecimal("100");
			BigDecimal result = amt.multiply(hundred);
			amount = result.longValue();
		}
		return amount;
	}
}
