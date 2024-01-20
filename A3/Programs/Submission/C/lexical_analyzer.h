#include <stdbool.h>
#ifndef LEXICAL_ANALYZER_H
#define LEXICAL_ANALYZER_H



/* Token codes */
#define INT_LIT 10
#define FLOAT_LIT 11
#define IDENT 12
#define STR_LIT 13
#define ASSIGN_OP 20
#define ADD_OP 21
#define SUB_OP 22
#define MULT_OP 23
#define DIV_OP 24
#define LEFT_PAREN 25
#define RIGHT_PAREN 26
#define LEFT_BRACE 27
#define RIGHT_BRACE 28
#define SEMICOLON 29
#define LESS_THAN 30
#define GREATER_THAN 31
#define EQUALS 32
#define NOT_EQUALS 33
#define AND_OP 34
#define OR_OP 35
#define IF 36
#define ELSE 37
#define FOR 38
#define WHILE 39
#define COMMENT 40
#define QUESTION_MARK 41
#define COLON 42
#define TYPE_INT 43
#define TYPE_FLOAT 44
#define TYPE_STRING 45
#define PRINT 46

typedef struct {
    int code;
    int line;
    char lexeme[100];
} Token;

/* Function prototypes for the Lexical Analyzer */
void initLexicalAnalyzer();
Token getNextToken();


#endif

