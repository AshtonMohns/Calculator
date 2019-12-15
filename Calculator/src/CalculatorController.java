import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Written as practice for SYSC 3110 Exam
 * @author Ashton
 *
 */
public class CalculatorController implements ActionListener{

	private CalculatorModel model;
	
	public CalculatorController(CalculatorModel model) {
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String calcFunction = ((JButton) arg0.getSource()).getText();
		System.out.println(calcFunction);
		char functionChar = calcFunction.charAt(0);
		
		if((functionChar >= '0' && functionChar <= '9') || functionChar == '\u03C0' || functionChar == 'e' ||
				functionChar == '.' || functionChar == '+' || functionChar == '\u2212' || functionChar == '\u00D7' ||
				functionChar == '\u00F7' || functionChar == '\u221A') model.addToEquation(functionChar);
		else if(functionChar == '=') model.checkEqality();
		else if(functionChar == '(') model.openBracket();
		else if(functionChar == ')') model.closeBracket();
		else if(calcFunction.equals("CE")) model.clearEquation();
		else if(calcFunction.equals("neg")) model.addToEquation('-');
		else if(calcFunction.equals("BS")) model.backSpace();
		else if(calcFunction.equals("y^x")) model.exponent();
		else if(calcFunction.equals("x^2")) model.square();
	}
}
