import java.util.NoSuchElementException;

public class Tokenizer {
    private char[] tokenStr = null;
    private int pos=0;
    public Tokenizer(String s){
        tokenStr = s.toCharArray();
    }

    private void skipSpaces(){
        while(pos<tokenStr.length && Character.isSpaceChar(tokenStr[pos])){
            pos++;
        }
    }

    public boolean hasMoreTokens(){
        skipSpaces();
        return pos<tokenStr.length;
    }

    private NumberToken readNumberToken(){
        double val = 0;
        while(pos<tokenStr.length && Character.isDigit(tokenStr[pos])){
            int a = tokenStr[pos] - '0';
            val=val*10+a;

            pos++;
        }

        if(pos<tokenStr.length && tokenStr[pos]=='.'){
            pos++;
            double divisor = 10;
            while(pos<tokenStr.length && Character.isDigit(tokenStr[pos])){
                double a = tokenStr[pos]-'0';
                val=val+(a/divisor);
                divisor*=10;
                pos++;
            }
        }

        return new NumberToken(val);
    }

    private OperatorToken readOperatorToken() throws InvalidExpressionException{
        char op = tokenStr[pos];
        pos++;
        if(op=='+'){
            return new OpAddToken();
        }else if(op=='-'){
            return new OpSubToken();
        }else if(op=='*'){
            return new OpMultToken();
        }else if(op=='/'){
            return new OpDivToken();
        }else{
            throw new InvalidExpressionException("Found " + op + " expecting an operator character at position " + (pos) + ".");
        }
    }

    public Token nextToken() throws InvalidExpressionException{
        skipSpaces();
        if(pos>=tokenStr.length){
            throw new NoSuchElementException("No more tokens remaining.");
        }

        if(Character.isDigit(tokenStr[pos])){
            return readNumberToken();
        }else if(tokenStr[pos]=='('){
            pos++;
            return new LeftParenToken();
        }else if(tokenStr[pos]==')'){
            pos++;
            return new RightParenToken();
        }else{
            return readOperatorToken();
        }
    }
}
