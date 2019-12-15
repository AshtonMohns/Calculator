import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * Written as practice for SYSC 3110 Exam
 * @author Ashton
 *
 */
public class CalculatorView implements CalculatorModelListener{
	
	//0-9, decimal, neg, subtract, add, multiply, divide,(, ), bkspace, M+, MR, MC, x->M, CE, equals, x^y, x^2, pi, e, sqrt
	private static final String[] CALC_FUNCTIONS = new String[] {
			"CE", "BS", "\u03C0", "e", "\u221A",
			"(", ")", "x^2", "y^x", "\u00F7",
			"MR", "1", "2", "3", "\u00D7",
			"MC", "4", "5", "6", "+",
			"M+", "7", "8", "9", "\u2212",
			"x->M", "0", ".", "neg", "="};
	
	public JTextField textField;
	public boolean syntaxErrorOccurred;
	
	public CalculatorView() {
		CalculatorModel model = new CalculatorModel();
		CalculatorController controller = new CalculatorController(model);
		
		JFrame frame = new JFrame("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel1 = new JPanel(new BorderLayout());
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText("0");
		
		textField.setFont(new Font("TimesNewRoman", Font.PLAIN, 35));
		
		panel1.add(textField, BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new GridLayout(6, 5));
		
		panel1.add(panel, BorderLayout.CENTER);
		
		for(String string : CALC_FUNCTIONS) {
			JButton button = new JButton(string);
			panel.add(button);
			button.addActionListener(controller);
		}
		
		model.addListener(this);
		
		frame.add(panel1);
		frame.setVisible(true);
		frame.setSize(400, 600);
	}

	@Override
	public void handleCalculatorEvent(char calculatorEvent) {
		if(syntaxErrorOccurred) {
			//
			this.textField.setText("0");
			syntaxErrorOccurred = false;
		}
		if(calculatorEvent == '\0') {
			//'\0' is the null character. I am using this when the calculator is being cleared.
			this.textField.setText("0");
			return;
		}
		else if(calculatorEvent == '\b') {
			//'\b' is the backspace character.
			String newText = this.textField.getText().substring(0, this.textField.getText().length() - 1);
			if(newText.length() == 0) newText = "0";
			this.textField.setText(newText);
			return;
		}
		
		String currText = this.textField.getText();
		
		//Overwrite text if it is only the initial '0', otherwise, continue writing.
		if(currText.equals("0")) this.textField.setText(String.valueOf(calculatorEvent));
		else this.textField.setText(currText + calculatorEvent);
	}
	
	@Override
	public void handleEqualityEvent(String s) {
		this.textField.setText(s);
		if(s.equals("Syntax Error")) syntaxErrorOccurred =  true;
	}
	
	public static void main(String[] args) {
		CalculatorView c = new CalculatorView();
	}
	
}