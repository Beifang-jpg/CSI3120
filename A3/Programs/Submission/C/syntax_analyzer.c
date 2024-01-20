#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "syntax_analyzer.h"


bool isType(int code){
    return (code == TYPE_FLOAT || code == TYPE_INT || code == TYPE_STRING);
}

bool isLitteral(int code){
    return (code == FLOAT_LIT || code == INT_LIT || code == STR_LIT);
}

bool isOp(int code){
    return (code == ADD_OP || code == SUB_OP || code == MULT_OP || code == DIV_OP || code == LEFT_PAREN || code == RIGHT_PAREN);
}

void analyze_syntax() {
    struct Stack* statementStack = createStack(300);
    struct Node* symbolTableList = NULL;
    struct Symbol* identifier;
    Token token;
    while (1) {
        token = getNextToken(); // Get the next token from the lexical analyzer
        if (token.code == EOF) {
            break; // Exit the loop when the end of the input is reached
        }
        else {
            if (token.code == LEFT_BRACE) {
                pushScope(&symbolTableList);
            }
            else if (token.code == RIGHT_BRACE) {
                popScope(&symbolTableList);
            }
            else if (isType(token.code)) {
                Token variable = getNextToken();
                struct Symbol* foundSymbol = symbolExistsInCurrent(symbolTableList, variable.lexeme);
                if (foundSymbol != NULL){
                    printf("Variable %s already defined \n", variable.lexeme);
                    return;
                }
                insertSymbol(symbolTableList, token.lexeme, variable.lexeme, "0");
                // Skip semicolon
                getNextToken();
            }
            else if (token.code == PRINT) {
                //skip opening parenthesis
                getNextToken();
                Token variable = getNextToken();
                //skip closing parenthesis
                getNextToken();
                // Skip semicolon
                getNextToken();
                struct Symbol* foundSymbol = symbolExists(symbolTableList, variable.lexeme);
                if (foundSymbol == NULL){
                    printf("Variable %s undefined \n", token.lexeme);
                    return;
                }
                printf("Variable %s: %f", foundSymbol->name, strtod(foundSymbol->value, NULL));
            }
            
            else if (token.code == IDENT) {
                struct Symbol* foundSymbol = symbolExists(symbolTableList, token.lexeme);
                if (foundSymbol == NULL){
                    printf("Variable %s undefined \n", token.lexeme);
                    return;
                } 
                else {
                    Token nextToken = getNextToken();
                    if (nextToken.code != ASSIGN_OP)
                    {
                        push(statementStack, strtod(foundSymbol->value, NULL));
                        if (nextToken.code != SEMICOLON)
                        {
                            push(statementStack, nextToken.lexeme[0]);
                        }
                        else
                        {
                            double identifier_value = evaluateExpression(statementStack);
                            sprintf(identifier->value, "%f", identifier_value);
                        }    
                    }
                    else {
                        identifier = foundSymbol;
                    }
                    
                }
            } 

            else if (token.code != SEMICOLON)
            {
                if( isOp(token.code) ) {
                     push(statementStack, token.lexeme[0]);
                }else{
                    push(statementStack, strtod(token.lexeme, NULL));
                }
                
            }else {
                double identifier_value = evaluateExpression(statementStack);
                sprintf(identifier->value, "%f", identifier_value);
            }
            
                
        }
    }
    return ;

}

int main() {
    initLexicalAnalyzer();
    analyze_syntax();
    return 0;
}
