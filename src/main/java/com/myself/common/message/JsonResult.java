package com.myself.common.message;

import java.util.List;

public class JsonResult<T> {

	private String draw;
	private int recordsTotal;
	private int recordsFiltered;
	
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	private List<T> data;
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
