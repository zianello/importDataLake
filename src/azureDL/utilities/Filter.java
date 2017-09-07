package azureDL.utilities;

import azureDL.db.util.GenericUtilImpl.Operator;

public class Filter {

	private String field;
	private Operator operator;
	private String value;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Filter(){
		
	}
	
	public Filter(String field, Operator operator, String value){
		this.field = field;
		this.operator = operator;
		this.value = value;
	}
}
