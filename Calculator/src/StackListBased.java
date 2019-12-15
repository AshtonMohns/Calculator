import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This program acts as a Stack built using java's LinkedList. I decided that
 * the Stack would be a Stack of Strings since this would allow me to avoid
 * unnecessary upcasting and downcasting, though I could make a more universal
 * Stack of Objects instead of Strings.
 * 
 * @author ashtonmohns
 * @version 1.0
 */
public class StackListBased {
    private String top;
    private LinkedList<String> items;
    
    /**
     * Creates a new Stack by creating a new LinkedList and setting top to null.
     */
    public void createStack(){
        items = new LinkedList<>();
        top = null;
    }
    
    /**
     * Clears all values on the stack and sets top to null.
     */
    public void popAll(){
        items.clear();
        top = null;
    }
    
    /**
     * Checks if the stack is empty.
     * 
     * @return true if the array is empty.
     */
    public boolean isEmpty(){
        return items.isEmpty();
    }
    
    /**
     * Add a new item to the top of the Stack.
     * 
     * @param s the String to add to the Stack.
     */
    public void push(String s){
        items.add(0, s);
        top = items.getFirst();
    }
    
    /**
     * Removes and returns the top value of the Stack, throws an exception if
     * the Stack is empty.
     * 
     * @return the value being removed.
     * @throws StackException when the Stack is empty.
     */
    public String pop() throws StackException{
        if(top == null){
            throw new StackException();
        }
        String temp = null;
        try{
            temp = items.pop();          
            top = items.getFirst();
        }
        catch(NoSuchElementException e){
            top = null;
        }
        return temp;
    }
    
    /**
     * Checks the top value of the Stack without removing it.
     * 
     * @return the top value of the Stack.
     * @throws StackException when the Stack is empty.
     */
    public String peek() throws StackException{
        if(top == null){
            throw new StackException();
        }
        return top;
    }
}

/**
 * Creates an Exception that states the Stack is empty when trying to remove 
 * a value.
 * 
 * @author ashtonmohns
 * @version 1.0
 */
class StackException extends Exception {
    private String s;
    
    /**
     * Default constructor.
     */
    public StackException(){
        s = "Stack was empty.";
    }
    
    public String toString(){
        return s;
    }
}