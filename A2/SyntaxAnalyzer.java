import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.SyncFailedException;

public class SyntaxAnalyzer {
    public static void main(String[] args) throws SyncFailedException {
        try {
            String f = "input15.txt";
            LexicalAnalyzer.in_fp = new BufferedReader(new FileReader(f));
            LexicalAnalyzer.getChar();
            LexicalAnalyzer.lex(); // get the first one
            statement_list();
            while (LexicalAnalyzer.nextToken != LexicalAnalyzer.EOF){ // mian function here
                LexicalAnalyzer.lex();
                statement_list();
            }
            System.out.println("Syntax analysis succeed");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SyncFailedException e) {
            System.out.println("Syntax analysis failed");
            System.out.println("syntax_analyzer_error -" + e.getMessage() + "at line " + LexicalAnalyzer.count);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage()+"at line " + LexicalAnalyzer.count);
        }finally {
            System.out.println("Program Finished");
        }
    }


    // need to use with statement
    public static void statement_list() throws IOException{
        Boolean flag = true;
        statement();
        while (flag == true){
            switch (LexicalAnalyzer.nextToken) {
                case LexicalAnalyzer.IDENT:
                case LexicalAnalyzer.IF:
                case LexicalAnalyzer.WHILE:
                case LexicalAnalyzer.FOR:
                    statement();
                    break;
                default:
                    flag = false;
                    break;
            }
        }
    }


    // could be if, for, while, or just assign
    public static void statement() throws IOException{
        // check which one statement is from four of them

        switch (LexicalAnalyzer.nextToken) {
            case LexicalAnalyzer.IF:
                LexicalAnalyzer.lex();
                statement_if();
                break;
            case LexicalAnalyzer.IDENT:
                LexicalAnalyzer.lex();
                statement_assign();
                break;
            case LexicalAnalyzer.WHILE:
                LexicalAnalyzer.lex();
                statement_while();
                break;
            case LexicalAnalyzer.FOR:
                //System.out.println("enter for");
                LexicalAnalyzer.lex();
                statement_for();
                break;
        
            default:
                //System.err.println("Unexpected token " + LexicalAnalyzer.nextToken);
                break;
        }
    }


    // four kinds of situations of statement
    public static void statement_if() throws IOException{
        //System.out.println("enter if");
        if(LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_PAREN){
            LexicalAnalyzer.lex();
            boolean_expression();
            if(LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_PAREN){
                LexicalAnalyzer.lex();
                if(LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_BRACE){
                    LexicalAnalyzer.lex();
                    // System.out.println("enter statement list in if");
                    statement_list();
                    // System.out.println("exit statement list");
                    if(LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_BRACE){
                        LexicalAnalyzer.lex();
                        return;
                    }else{
                        //System.out.println("next is " + LexicalAnalyzer.nextToken);
                        throw new SyncFailedException("Unmatch closing symbol }1.");
                    }
                }else{
                    throw new SyncFailedException("Unmatch closing symbol {.");
                }
            }else{
                throw new SyncFailedException("Unmatch closing symbol).");
            }
        }else{
            throw new SyncFailedException("Unmatch closing symbol (.");
        }
    }

    public static void statement_while() throws IOException{
        if (LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_PAREN){
            LexicalAnalyzer.lex();
            function_calling();
            if (LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_PAREN){
                LexicalAnalyzer.lex();
                if (LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_BRACE){
                    LexicalAnalyzer.lex();
                    function_calling();
                    if (LexicalAnalyzer.nextToken == LexicalAnalyzer.SEMICOLON){
                        LexicalAnalyzer.lex();
                    }else{
                        throw new IOException("syntax_analyzer_error - Missing semi colon."); 
                    }

                    if (LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_BRACE){
                        LexicalAnalyzer.lex();
                        return;
                    }else{
                       throw new SyncFailedException("Unmatch closing symbol }2."); 
                    }
                }else{
                    throw new SyncFailedException("Unmatch closing symbol {.");
                }
            }else{
                throw new SyncFailedException("Unmatch closing symbol).");
            }
        }else{
            throw new SyncFailedException("Unmatch closing symbol (.");
        }
    }

    public static void statement_for() throws IOException{
        //System.out.println("enter for");
        if (LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_PAREN){
            LexicalAnalyzer.lex();
            for (int i = 0; i <4 ; i++){
                if(LexicalAnalyzer.nextToken == LexicalAnalyzer.IDENT){
                    LexicalAnalyzer.lex();
                }else{
                    throw new IOException("Using non-ident in for loop.");
                }
            }

            if (LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_PAREN){
                LexicalAnalyzer.lex();
                if (LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_BRACE){
                    LexicalAnalyzer.lex();
                    statement_list();
                    // LexicalAnalyzer.lex();
                    if(LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_BRACE){
                        LexicalAnalyzer.lex();
                    }else{
                        throw new SyncFailedException("Unmatch closing symbol }3.");
                    }
                }else{
                    throw new SyncFailedException("Unmatch closing symbol {.");   
                }
            }else{
                throw new SyncFailedException("Unmatch closing symbol).");
            }
        }else{
            throw new SyncFailedException("Unmatch closing symbol(.");
        }
    }

    public static void statement_assign() throws IOException{
        if(LexicalAnalyzer.nextToken == LexicalAnalyzer.ASSIGN_OP){ // the only correct way to assign things
            LexicalAnalyzer.lex();
            expression();
            if (LexicalAnalyzer.nextToken != LexicalAnalyzer.SEMICOLON){
                throw new SyncFailedException("syntax_analyzer_error - Missing semi colon");
            }
            LexicalAnalyzer.lex();
        }else{
            System.out.println("next is " + LexicalAnalyzer.nextToken);
            throw new IOException("String assignment error .");
        }


    }

    // will be use for single variable and +-*/ them
    public static void expression() throws IOException{
        if (LexicalAnalyzer.nextToken == LexicalAnalyzer.INT_LIT || LexicalAnalyzer.nextToken == LexicalAnalyzer.FLOAT_LIT){
            //normal math +-*/
            LexicalAnalyzer.lex();
            if (LexicalAnalyzer.nextToken == LexicalAnalyzer.ADD_OP || LexicalAnalyzer.nextToken == LexicalAnalyzer.SUB_OP 
                || LexicalAnalyzer.nextToken == LexicalAnalyzer.MULT_OP || LexicalAnalyzer.nextToken == LexicalAnalyzer.DIV_OP){
                LexicalAnalyzer.lex();
                if(LexicalAnalyzer.nextToken == LexicalAnalyzer.INT_LIT || LexicalAnalyzer.nextToken == LexicalAnalyzer.FLOAT_LIT){
                    LexicalAnalyzer.lex();
                }else{
                    throw new IOException("Missing operand before operator.");
                }
            }else{
                //throw new IOException("Missing operand before operator");
                //just number
            }
        }else if (LexicalAnalyzer.nextToken == LexicalAnalyzer.STR_LIT) {
            LexicalAnalyzer.lex();
            if (LexicalAnalyzer.nextToken == LexicalAnalyzer.ADD_OP){
                LexicalAnalyzer.lex();
                if (LexicalAnalyzer.nextToken == LexicalAnalyzer.STR_LIT){
                    // + string good
                    LexicalAnalyzer.lex();
                }else{
                    throw new IOException("Missing operand before operator, add string and non-string.");
                }
            }else{
                // just string
            }
        }else{
            throw new IOException("The expression has some wrong.");
        }
    }


    // will be us in if
    public static void boolean_expression() throws IOException{
        literal();
        
        if (LexicalAnalyzer.nextToken == LexicalAnalyzer.NOT_EQUALS || LexicalAnalyzer.nextToken == LexicalAnalyzer.GREATER_THAN ||
        LexicalAnalyzer.nextToken == LexicalAnalyzer.LESS_THAN || LexicalAnalyzer.nextToken == LexicalAnalyzer.EQUALS){
            LexicalAnalyzer.lex();
            literal();
        }else{
            throw new IOException("The boolean has using wrong things.");
        }
    }

    //will be use in bool
    public static void literal() throws IOException{
        if (LexicalAnalyzer.nextToken == LexicalAnalyzer.INT_LIT || LexicalAnalyzer.nextToken == LexicalAnalyzer.IDENT 
            || LexicalAnalyzer.nextToken == LexicalAnalyzer.FLOAT_LIT || LexicalAnalyzer.nextToken == LexicalAnalyzer.STR_LIT){
                LexicalAnalyzer.lex();
                return;
            }else{
                throw new IOException("wrong literal");
            }
    }

    // just use function
    public static void function_calling() throws IOException{
        if (LexicalAnalyzer.nextToken == LexicalAnalyzer.IDENT){
            LexicalAnalyzer.lex();
            if (LexicalAnalyzer.nextToken == LexicalAnalyzer.LEFT_PAREN){
                LexicalAnalyzer.lex();
                if (LexicalAnalyzer.nextToken == LexicalAnalyzer.RIGHT_PAREN){
                    LexicalAnalyzer.lex();
                    
                }else{
                    throw new SyncFailedException("Unmatch closing symbol ).");
                }
            }else{
                throw new SyncFailedException("Unmatch closing symbol(.");
            }
        }else{
            throw new IOException("Unmatch closing symbol, The expression has some wrong.");
        }
    }
}
