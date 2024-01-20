#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>
#include "lexical_analyzer.h"


/* Global declarations */
/* Variables */
int charClass;
char lexeme[100];
char error[100];
char nextChar;
int lexLen;
int nextToken;
FILE *in_fp, *fopen();
int currentLine = 1;

/* Function declarations */
void addChar();
void getChar();
void getNonBlank();
int lex();
bool isComment();


/* Character classes */
#define LETTER 0
#define DIGIT 1
#define UNDERSCORE 2
#define UNKNOWN 99




void initLexicalAnalyzer() {
    if ((in_fp = fopen("input.txt", "r")) == NULL)
        printf("ERROR - cannot open input.txt\n");
    getChar(); // Initialize the first character
}

/*******************/
/* lookup - a function to lookup operators and keywords and return the token */
int lookup(char ch) {
    switch (ch) {
        case '(':
            addChar();
            nextToken = LEFT_PAREN;
            break;
        case ')':
            addChar();
            nextToken = RIGHT_PAREN;
            break;
        case '{':
            addChar();
            nextToken = LEFT_BRACE;
            break;
        case '}':
            addChar();
            nextToken = RIGHT_BRACE;
            break;
        case '+':
            addChar();
            nextToken = ADD_OP;
            break;
        case '-':
            addChar();
            nextToken = SUB_OP;
            break;
        case '*':
            addChar();
            nextToken = MULT_OP;
            break;
        case '/':
            addChar();
            getChar();
            nextToken = isComment()? COMMENT : DIV_OP;
            break;
        case '=':
            addChar();
            getChar();
            if (nextChar == '=') {
                addChar();
                EQUALS;
            }else{
                nextToken = ASSIGN_OP;
            }
            break;
        case ';':
            addChar();
            nextToken = SEMICOLON;
            break;
        case '<':
            addChar();
            getChar();
            if (nextChar == '=') {
                addChar();
                nextToken = LESS_THAN;
            } else {
                nextToken = LESS_THAN;
            }
            break;
        case '>':
            addChar();
            getChar();
            if (nextChar == '=') {
                addChar();
                nextToken = GREATER_THAN;
            } else {
                nextToken = GREATER_THAN;
            }
            break;
        case '!':
            addChar();
            getChar();
            if (nextChar == '=') {
                addChar();
                nextToken = NOT_EQUALS;
            } else {
                nextToken = UNKNOWN;
            }
            break;
        case '&':
            addChar();
            getChar();
            if (nextChar == '&') {
                addChar();
                nextToken = AND_OP;
            } else {
                nextToken = UNKNOWN;
            }
            break;
        case '|':
            addChar();
            getChar();
            if (nextChar == '|') {
                addChar();
                nextToken = OR_OP;
            } else {
                nextToken = UNKNOWN;
            }
            break;
        case '?':
            addChar();
            nextToken = QUESTION_MARK;
            break;
        case ':':
            addChar();
            nextToken = COLON;
            break;
        case '\"':
            addChar();
            getChar();
            while (nextChar != '\"' && nextChar != EOF) {
                addChar();
                getChar();
            }
            if (nextChar == '\"') {
                addChar(); // Include the closing double quote
//                getChar();
                nextToken = STR_LIT;
            } else {
                strncpy(error, "Error - unclosed string literal", 100);
                nextToken = EOF;
            }
            break;
        default:
            addChar();
            nextToken = EOF;
            break;
    }
    return nextToken;
}

bool isComment(){
    if (nextChar == '/') {
        // This is the beginning of a single-line comment
        while (nextChar != '\n' && nextChar != EOF)
            getChar(); // Ignore characters in the comment
        nextToken = COMMENT;
        strncpy(lexeme, "a single line comment", 100);
    } else if (nextChar == '*') {
        // This is the beginning of a block comment
        addChar();
        getChar();
        while (!(nextChar == '*' && getc(in_fp) == '/')) {
            if (nextChar == EOF) {
                strncpy(error, "Error - unclosed block comment", 100);
                nextToken = EOF;
                break;
            }
            getChar();
        }
        getChar(); // Consume the '/'
        nextToken = COMMENT;
        strncpy(lexeme, "a block comment", 100);
    } else {
        return false;
    }
    return true;
}

/*******************/
/* addChar - a function to add nextChar to lexeme */
void addChar() {
    if (lexLen <= 98) {
        lexeme[lexLen++] = nextChar;
        lexeme[lexLen] = '\0'; // Null-terminate the lexeme string
    } else
        printf("Error - lexeme is too long\n");
}

/*******************/
/* getChar - a function to get the next character of input and determine its character class */
void getChar() {
    if ((nextChar = getc(in_fp)) != EOF) {
        if (nextChar == '\n'){
            currentLine ++;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
        }
        if (isalpha(nextChar))
            charClass = LETTER;
        else if(nextChar == '_')
            charClass = UNDERSCORE;
        else if (isdigit(nextChar))
            charClass = DIGIT;
        else
            charClass = UNKNOWN;
    } else
        charClass = EOF;
}

/*******************/
/* getNonBlank - a function to call getChar until it returns a non-whitespace character */
void getNonBlank() {
    while (isspace(nextChar))
        getChar();
}


Token getNextToken() {
    lexLen = 0;
    getNonBlank();

    switch (charClass) {
        /* Parse identifiers or keywords */
        case LETTER :
        case UNDERSCORE:
            addChar();
            getChar();
            while (charClass == LETTER || charClass == DIGIT || charClass == UNDERSCORE) {
                addChar();
                getChar();
            }
            // Check if the lexeme is a type
            if (strcmp(lexeme, "int") == 0)
                nextToken = TYPE_INT;
            else if (strcmp(lexeme, "float") == 0)
                nextToken = TYPE_FLOAT;
            else if (strcmp(lexeme, "string") == 0)
                nextToken = TYPE_STRING;
            else if (strcmp(lexeme, "print") == 0)
                nextToken = PRINT;
            else if (charClass == UNKNOWN && !isspace(nextChar) && !strchr("(*+/=?><)", nextChar)){
                addChar();
                strncpy(error, "Error - illegal identifier1", 100);
                nextToken = EOF;
            }
            else
                nextToken = IDENT;
            break;
            /* Parse integer literals S*/
        case DIGIT:
            addChar();
            getChar();
            while (charClass == DIGIT) {
                addChar();
                getChar();
            }
            if (nextChar == '.') {
                addChar(); // Include the decimal point
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = FLOAT_LIT;
            } else if (charClass == LETTER || nextChar == '_') {
                while (charClass == LETTER || charClass == DIGIT || nextChar == '_') {
                    addChar();
                    getChar();
                }
                strncpy(error, "Error - illegal identifier2", 100);
                nextToken = EOF;
            } else {
                nextToken = INT_LIT;
            }
            break;
        case '\"':
            lookup(nextChar);
            getChar();
            break;
            /* Operators and punctuation */
        case UNKNOWN:
            lookup(nextChar);
            getChar();
            break;
            /* EOF */
        case EOF:
            nextToken = EOF;
            lexeme[0] = 'E';
            lexeme[1] = 'O';
            lexeme[2] = 'F';
            lexeme[3] = '\0';
            break;
    }

    /* End of switch */
    printf("\t%s\n", error);
    //printf("lexeme %s\n", lexeme);


    Token token;
    token.code = nextToken;
    token.line = currentLine;
    strcpy(token.lexeme, lexeme);
    return token;
}
