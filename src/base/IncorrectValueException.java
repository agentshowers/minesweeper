package base;

public class IncorrectValueException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private int correctValue;
	
	public IncorrectValueException (String s, int value){
		super(s);
		correctValue = value;
	}
	
	public int getCorrectValue (){
		return correctValue;
	}
}
