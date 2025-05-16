
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Lab3_3_InfixConverter {


	/* Translates the provided String "s" from infix notation to
		postfix notation.  Returns the postfix formatted string.

		This method assumes that "s" is a properly written infix expression.  It
		is not expected to detect all syntax errors in "s".  However, it may
		throw an exception for some improperly formatted inputs.
	 */
	public static String infixToPostfix(String s) throws InvalidExpressionException {
		Stack<Token> opstack = new Stack<Token>();
		String output = "";
		Tokenizer tokenizer = new Tokenizer(s);
		Token t;

		while(tokenizer.hasMoreTokens()){
			t = tokenizer.nextToken();
			if(t instanceof NumberToken){
				output+=((NumberToken)t).value + " ";
			}else if(t instanceof LeftParenToken){
				opstack.push((LeftParenToken)t);
			}else if(t instanceof RightParenToken){
				while(!(opstack.peek() instanceof LeftParenToken)){
					output+=opstack.pop() + " ";
				}
				opstack.pop();
			}else if(t instanceof OperatorToken){
				int p = ((OperatorToken) t).getPrecedence();
				while(!opstack.isEmpty() && !(opstack.peek() instanceof ParenToken) && p<=((OperatorToken)opstack.peek()).getPrecedence()){
						output+=opstack.pop() + " ";
				}
				opstack.push(t);
			}
		}
		while(!opstack.isEmpty()){
			output+=opstack.pop()+ " ";
		}
		return output;
	}

	/**
	 * Main opens the file 'infix.in'.  It then reads
	 * through the file one line at a time.  For each line, it prints the original
	 * line from the input, then the postfix equivalent, and then prints the
	 * simplified answer.
	 *
	 * If an exception occurs when evaluating a specific line, the exception is
	 * printed and then execution continues with the next line.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("/Users/annam/Downloads/AP Comp Sci/Unit_3/PostFixLab/infix.in"));

		while (in.hasNextLine()) {
			String line = in.nextLine();
			System.out.println("\n" + line);

			try {
				String postfix = infixToPostfix(line);
				System.out.println("\t Postfix: " + postfix);

				double answer = Lab3_2_PostfixCalculator.calculatePostfix(postfix);
				System.out.println("\t Answer: " + answer);
			}
			catch (InvalidExpressionException iex) {
				System.out.println(iex.getMessage());
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

