package analyzer;


import java.io.IOException;



public class SyntaxAnalyzer {

    public static void main(String[] args) {
        LexicalAnalyzer.initLexicalAnalyzer();
        try {
            analyzeSyntax();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isType(int code) {
        return code == LexicalAnalyzer.TYPE_FLOAT || code == LexicalAnalyzer.TYPE_INT || code == LexicalAnalyzer.TYPE_STRING;
    }

    public static boolean isLiteral(int code) {
        return code == LexicalAnalyzer.FLOAT_LIT || code == LexicalAnalyzer.INT_LIT || code == LexicalAnalyzer.STR_LIT;
    }

    public static boolean isOp(int code) {
        return code == LexicalAnalyzer.ADD_OP || code == LexicalAnalyzer.SUB_OP || code == LexicalAnalyzer.MULT_OP || code == LexicalAnalyzer.DIV_OP || code == LexicalAnalyzer.LEFT_PAREN || code == LexicalAnalyzer.RIGHT_PAREN;
    }


    

    private static void analyzeSyntax() throws IOException {
        Stack statementStack = new Stack(300);
        // Node symbolTableList = new Node();
        Symbol identifier = new Symbol();

        Token token;
        while (true) {
            token = LexicalAnalyzer.getNextToken(); 

            if (token.code == LexicalAnalyzer.EOF) {
                break; // Exit the loop when the end of the input is reached
            } else {
                if (token.code == LexicalAnalyzer.LEFT_BRACE) {
                    Node.pushScope();
                } else if (token.code == LexicalAnalyzer.RIGHT_BRACE) {
                    //symbolTableList.printAllScopes();
                    Node.popScope();
                } else if (isType(token.code)) {
                    Token variable = LexicalAnalyzer.getNextToken();
                    Symbol foundSymbol = Node.symbolExistsInCurrent(variable.lexeme);

                    if (foundSymbol != null) {
                        System.out.printf("Variable %s already defined %n", variable.lexeme);
                        break;
                    }
                    Node.insertSymbol(token.lexeme, variable.lexeme, "0");
                    // Skip semicolon
                    LexicalAnalyzer.getNextToken();
                } else if (token.code == LexicalAnalyzer.PRINT) {
                    // skip opening parenthesis
                    LexicalAnalyzer.getNextToken();
                    Token variable = LexicalAnalyzer.getNextToken();
                    // skip closing parenthesis
                    LexicalAnalyzer.getNextToken();
                    // Skip semicolon
                    LexicalAnalyzer.getNextToken();
                    Symbol foundSymbol = Node.symbolExists(variable.lexeme);
                    if (foundSymbol == null) {
                        System.out.printf("Variable %s undefined %n", variable.lexeme);
                        break;
                    }

                    System.out.printf("Variable %s: %f%n", foundSymbol.name, Double.parseDouble(foundSymbol.value));
                } else if (token.code == LexicalAnalyzer.IDENT) {
                    Symbol foundSymbol = Node.symbolExists(token.lexeme);

                    if (foundSymbol == null) {
                        System.out.printf("Variable %s undefined %n", token.lexeme);
                        break;
                    } else {
                        Token nextToken = LexicalAnalyzer.getNextToken();
                        if (nextToken.code != LexicalAnalyzer.ASSIGN_OP) {
                            statementStack.push(Double.parseDouble(foundSymbol.value));
                            if (nextToken.code != LexicalAnalyzer.SEMICOLON){
                                statementStack.push(nextToken.lexeme.charAt(0));
                            }
                            else{
                                double identifierValue = ExpressionEvaluator.evaluateExpression(statementStack);
                                identifier.value = Double.toString(identifierValue);
                            }
                        } else  {
                            identifier = foundSymbol;
                        }
                    }
                } else if (token.code != LexicalAnalyzer.SEMICOLON) {
                    if (isOp(token.code)) {
                        statementStack.push(token.lexeme.charAt(0));
                    } else {
                        
                        statementStack.push(Double.parseDouble(token.lexeme));
                    }
                } else {
                    double identifierValue = ExpressionEvaluator.evaluateExpression(statementStack);
                    identifier.value = Double.toString(identifierValue);
                }
            }
        }
    }

}



