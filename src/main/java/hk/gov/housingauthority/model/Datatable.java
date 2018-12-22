package hk.gov.housingauthority.model;

public class Datatable {

	private String draw;
	private String recordsTotal;
	private String data;
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public String getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(String recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String toJsonStr(Datatable datatable) {
		return "{\"recordsFiltered\":\"" + datatable.getRecordsTotal() + "\",\"draw\":\"" + datatable.getDraw() + "\",\"data\":" + datatable.getData() + ",\"recordsTotal\":\"" + datatable.getRecordsTotal() + "\"}";
	}
}
