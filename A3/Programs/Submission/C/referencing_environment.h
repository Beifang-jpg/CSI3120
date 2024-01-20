#ifndef REFERENCING_ENVIRONMENT_H
#define REFERENCING_ENVIRONMENT_H

#define MAX_SYMBOLS 100

// Define a symbol structure
struct Symbol {
    char type[20];
    char name[20];
    char value[100];
};

// Define a symbol table structure
struct SymbolTable {
    struct Symbol symbols[MAX_SYMBOLS];
};

// Define a node for the linked list
struct Node {
    struct SymbolTable symbolTable;
    int count;
    struct Node* next;
};

void insertSymbol(struct Node* node, const char* type, const char* name, const char* value);

void pushScope(struct Node** head);

void popScope(struct Node** head);

struct Symbol* symbolExists(struct Node* head, const char* name);

void printCurrentScope(struct Node* head);

void printAllScopes(struct Node* head);

struct Symbol* symbolExistsInCurrent(struct Node* head, const char* name);

#endif