package hk.gov.housingauthority.editor;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SqlTimestampPropertyEditor extends PropertyEditorSupport{
	
	public static final String DEFAULT_BATCH_PATTERN = "dd-MM-yyyy HH:mm:ss.SSS";
	
	private SimpleDateFormat sdf;
	
	public SqlTimestampPropertyEditor() {
		sdf = new SimpleDateFormat(SqlTimestampPropertyEditor.DEFAULT_BATCH_PATTERN);
	}
	
	public SqlTimestampPropertyEditor(String pattern) {
		this.sdf = new SimpleDateFormat(pattern);
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(new Timestamp(this.sdf.parse(text).getTime()));
		} catch (ParseException ex) {
			//throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
		}
	}
	
	@Override
	public String getAsText() {
		try {
		Timestamp value = (Timestamp) getValue();
			return (value != null ? this.sdf.format(value) : "");
		}catch(Exception ex){
			return null;
		}
	}
}
