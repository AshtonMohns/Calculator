
/**
 * This class acts as an infix calculator that can determine the value of an
 * infix expression by converting it to a postfix expression using a stack then
 * calculating the result using a stack.
 * 
 * @author ashtonmohns
 * @version 1.0
 */
public class InfixCalculator {
    private StackListBased stack;
    private String infix;
    
    /**
     * Default constructor. Creating a new stack to be used by the methods, and
     * initiating to infix notation to an input String s.
     * 
     * @param s the infix expression to be evaluated.
     * Note: The assignment specification said to assume the infix expression is
     * syntactically correct, so s does not need to be checked for correctness.
     */
    public InfixCalculator(String s){
        this.stack = new StackListBased();
        this.stack.createStack();
        this.infix = s;
    }
    
    /**
     * This method will first print the infix notation, then it will call and
     * print the convertPostfix() method to print to postfix value of the infix
     * expression. Finally, it will call the getPostfix() method to find the
     * value of the postfix expression that it created.
     */
    public String evaluateInfix(){
        System.out.println("infix: " + infix);
        String postfix = this.convertPostfix();
        System.out.println("postfix: " + postfix);
        System.out.println("result: " + this.getPostfix(postfix));
        return this.getPostfix(postfix);
    }
    
    /**
     * This method converts the String infix into a postfix expression using a
     * stack, then returns the postfix version of that expression.
     * 
     * @return the postfix version of the given infix expression.
     */
    public String convertPostfix(){
        String postfixExp = "";
        boolean sameNumber = true;          //Used to ensure there is no space
        //between characters in any double digit numbers.
        for(char ch : infix.toCharArray()){
            
            if (ch == '\u00D7' || ch == '+' || ch == '\u2212' || ch == '\u00F7'){
                //An operator means that the number is finished, and the next
                //number has yet to start.
                sameNumber = false;
                try{
                    //Written more clearly, the condition of the while statement
                    //is as follows:
                    //while(stack is not empty and top of the stack is not '('
                    //      and presidence(ch) <= precedence(top of stack))
                    while(!(this.stack.isEmpty()) && 
                            !(this.stack.peek().equals("(")) &&
                            ((ch != '\u00D7' && ch != '\u00F7') || 
                            (!(this.stack.peek().equals("+")) && 
                            !(this.stack.peek().equals("\u2212"))))){
                        postfixExp += " ";
                        postfixExp += this.stack.pop();
                    }
                }
                catch(StackException e){
                    return "Syntax Error";
                }
                //Save the new operator onto the stack.
                this.stack.push(String.valueOf(ch));
            }
            else if(ch == '('){
                //A '(' means that the number is finished, and the next number 
                //has yet to start. Therefore, sameNumber flag can be set false.
                sameNumber = false;
                this.stack.push(String.valueOf(ch));
            }
            else if(ch == ')'){
                //A '(' means that the number is finished, and the next number 
                //has yet to start. Therefore, sameNumber flag can be set false.
                sameNumber = false;
                try{
                    while(!this.stack.peek().equals("(")){
                        postfixExp += " ";
                        postfixExp += this.stack.pop();
                    }
                    this.stack.pop();       //Remove the open parenthesis.
                }
                catch(StackException e){
                    return "Syntax Error";
                }
            }
            else if(ch != ' '){
                //This is the case where an operand is the next character.
                if(sameNumber == false){
                   //Place a space because it is not the same number.
                   postfixExp += " "; 
                }
                //Until told otherwise, it is assumed the number continues.
                sameNumber = true;
                postfixExp += ch;
            }
        }
        while(!this.stack.isEmpty()){
            try{
                //Empty the operators from the stack into the postfix expression.
                postfixExp += " ";
                postfixExp += this.stack.pop();
            }
            catch(StackException e){
                return "Syntax Error";
            }
        }
        return postfixExp;
    }
    
    /**
     * This method calculates the value of a given postfix expression.
     * 
     * @param postfix the expression to be calculated.
     * @return the value of the input expression.
     */
    public String getPostfix(String postfix){
        String currentNumber = "";
        for(char ch : postfix.toCharArray()){
            switch (ch) {
                case '\u00D7':
                    try{
                        //Multiply the first operands on the stack, then return
                        //the result to the stack.
                        int operand2 = Integer.parseInt(this.stack.pop());
                        int operand1 = Integer.parseInt(this.stack.pop());
                        String result = Integer.toString(operand1 * operand2);
                        this.stack.push(result);
                    }
                    catch(StackException e){
                        return "Syntax Error";
                    }
                    catch(NumberFormatException e) {
                    	return "Syntax Error";
                    }
                    break;
                case '\u00F7':
                    try{
                        //Divide the first operands on the stack, then return
                        //the result to the stack.
                        int operand2 = Integer.parseInt(this.stack.pop());
                        int operand1 = Integer.parseInt(this.stack.pop());
                        String result = Integer.toString(operand1 / operand2);
                        this.stack.push(result);
                    }
                    catch(StackException e){
                        return "Syntax Error";
                    }
                    catch(NumberFormatException e) {
                    	return "Syntax Error";
                    }
                    break;
                case '+':
                    try{
                        //Add the first operands on the stack, then return
                        //the result to the stack.
                        int operand2 = Integer.parseInt(this.stack.pop());
                        int operand1 = Integer.parseInt(this.stack.pop());
                        String result = Integer.toString(operand1 + operand2);
                        this.stack.push(result);
                    }
                    catch(StackException e){
                        return "Syntax Error";
                    }
                    catch(NumberFormatException e) {
                    	return "Syntax Error";
                    }
                    break;
                case '\u2212':
                    try{
                        //Subtract the first operands on the stack, then return
                        //the result to the stack.
                        int operand2 = Integer.parseInt(this.stack.pop());
                        int operand1 = Integer.parseInt(this.stack.pop());
                        String result = Integer.toString(operand1 - operand2);
                        this.stack.push(result);
                    }
                    catch(StackException e){
                        return "Syntax Error";
                    }
                    catch(NumberFormatException e) {
                    	return "Syntax Error";
                    }
                    break;
                case ' ':
                    if(!(currentNumber.equals(""))){
                        //Add numbers to the stack whenever a space occurs in
                        //the postfix expression.
                        currentNumber = "";
                    }   break;
                default:
                    if(!(currentNumber.equals(""))) {
						try {
							this.stack.pop();
						} catch (StackException e) {}
                    }
                	//Append the current char (which is part of an operand) to
                    //the currentNumber String. This will be placed on the stack
                    //when the number is completed.
                    currentNumber += ch;
                    this.stack.push(currentNumber);
                    break;
            }
        }
        try{
            //Returns the value of the postfix expression.
            return this.stack.pop();
        }
        catch(StackException e){
        	return "Syntax Error";
        }
    }
}
