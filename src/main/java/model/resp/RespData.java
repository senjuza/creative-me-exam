package model.resp;

import java.util.ArrayList;
import java.util.List;

public class RespData {

	private int recordsTotal;
	private int recordsFiltered;
	//private List<StrData> data = new ArrayList<StrData>();
	private List<List<String>> data = new ArrayList<List<String>>();
	
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
	
	
	public List<List<String>> getData() {
		return data;
	}
	public void setData(List<List<String>> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "RespData [recordsTotal=" + recordsTotal + ", recordsFiltered=" + recordsFiltered + ", data=" + data
				+ "]";
	}
	
	
}
