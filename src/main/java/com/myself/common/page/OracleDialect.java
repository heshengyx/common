package com.myself.common.page;

import com.myself.common.page.Dialect;
import com.myself.common.page.Page;

public class OracleDialect implements Dialect {

	public String getPageSql(String sql, int offset, int limit) {
		return null;
	}

	public String getPageSql(String sql, Page<?> page) {
		return null;
	}

}
