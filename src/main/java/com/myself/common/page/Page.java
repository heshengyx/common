package com.myself.common.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> {
	private int page = 1; // 页码，默认是第一页
	private int rows = 15; // 每页显示的记录数，默认是15
	private int totalRecord; // 总记录数
	private int totalPage; // 总页数
	private T entity;
	private List<T> results; // 对应的当前页记录
	private Map<String, Object> params = new HashMap<String, Object>(); // 其他的参数我们把它分装成一个Map对象

	public T getEntity() {
		return entity;
	}
	
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		int totalPage = totalRecord % rows == 0 ? totalRecord / rows
				: totalRecord / rows + 1;
		this.setTotalPage(totalPage);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
