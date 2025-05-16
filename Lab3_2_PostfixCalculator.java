
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;


public class Lab3_2_PostfixCalculator {

    /**
     * calculatePostfix calculates and returns the value for the postfix
     * expression "postfixExp".  This method throws exceptions in the following
     * cases:
     * 1) If there are illegal characters in the input.
     * 2) If there are not enough values for a given operator.
     * 3) If there is more than 1 value remaining on the stack after the calculation completes.
     */
    public static double calculatePostfix(String postfixExp) throws InvalidExpressionException {
        Stack<NumberToken> s = new Stack<>();
        Tokenizer t = new Tokenizer(postfixExp);
        Token token;

        while(t.hasMoreTokens()){
            token=t.nextToken();
            if(token instanceof NumberToken){
                s.push( (NumberToken) token);
            }else{//token is operator
                if(s.size()<2){
                    throw new InvalidExpressionException("Too few values on stack to perform operator " + token);
                }
                double val2 = s.pop().value;
                double val1 = s.pop().value;
                double finalval = ((OperatorToken) token).eval(val1,val2);
                s.push(new NumberToken(finalval));

            }
        }
        if(s.size()==1){
            return s.pop().value;
        }else{
            throw new InvalidExpressionException("Too few operators in the expression. Stack has " + s.size() + " elements at the end of the expression");
        }
        
    }

    /**
     * Main opens the file specified postfix.in.  It then reads
     * through the file one line at a time.  For each line, it prints the original
     * line from the input and then prints the result.  If an exception occurs when
     * evaluating a specific link, print the message for the exception (using getMessage)
     * and then continue to the next line.
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("/Users/annam/Downloads/AP Comp Sci/Unit_3/PostFixLab/postfix.in"));

        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println("\n" + line);

            try {
                double answer = calculatePostfix(line);
                System.out.println(answer);
            }
			catch (InvalidExpressionException iex) {
				System.out.println(iex.getMessage());
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
        }
        in.close();
    }
}
