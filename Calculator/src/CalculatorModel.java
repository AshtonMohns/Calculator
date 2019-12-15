import java.util.ArrayList;

/**
 * Written as practice for SYSC 3110 Exam
 * @author Ashton
 *
 */
public class CalculatorModel {
	
	private String equation;
	private ArrayList<CalculatorModelListener> listeners;
	private int countBrackets;
	
	public CalculatorModel() {
		this.equation = new String();
		this.listeners = new ArrayList<>();
		countBrackets = 0;
	}
	
	public String getEquation() {
		return this.equation;
	}
	
	public void addToEquation(char c) {
		this.equation += c;
		notifyListeners(c);
	}
	
	public void clearEquation() {
		this.equation = "";
		this.countBrackets = 0;
		notifyListeners('\0');
	}
	
	public void backSpace() {
		int newEquationLength = equation.length() - 1;
		if(newEquationLength >= 0) {
			equation = equation.substring(0, equation.length() - 1);
			notifyListeners('\b');
			//'\b' is the backspace character.
		}
	}
	
	public void openBracket() {
		countBrackets++;
		addToEquation('(');
	}
	
	public void closeBracket() {
		if(countBrackets > 0) {
			countBrackets--;
			addToEquation(')');
		}
	}
	
	public void exponent() {
		addToEquation('^');
		openBracket();
	}
	
	public void square() {
		exponent();
		addToEquation('2');
		closeBracket();
	}
	
	public void checkEqality() {
		if(countBrackets != 0) {
			this.clearEquation();
			notifyListenersEquality("Syntax Error");
		}
		InfixCalculator infixCalc = new InfixCalculator(equation);
		String s = infixCalc.evaluateInfix();
		equation = s.equals("Syntax Error") ? "" : s;
		notifyListenersEquality(s);
	}
	
	public void addListener(CalculatorModelListener c) {
		listeners.add(c);
	}
	
	private void notifyListeners(char c) {
		for(CalculatorModelListener listener : listeners) {
			listener.handleCalculatorEvent(c);
		}
	}
	
	public void notifyListenersEquality(String s) {
		for(CalculatorModelListener listener : listeners) {
			listener.handleEqualityEvent(s);
		}
	}
}
