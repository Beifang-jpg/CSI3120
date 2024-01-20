#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "referencing_environment.h"



// Function to create a new symbol table node
struct Node* createNode() {
    // To be completed
}

// Function to insert a new symbol into the current symbol table
void insertSymbol(struct Node* node, const char* type, const char* name, const char* value) {
    // To be completed
}

// Function to push a new symbol table (scope) onto the linked list
void pushScope(struct Node** head) {
    // To be completed
}

// Function to pop the top symbol table (scope) from the linked list
void popScope(struct Node** head) {
    // To be completed
}

// Function to check if a symbol exists in the current or outer scopes
struct Symbol* symbolExists(struct Node* head, const char* name) {
    // To be completed
}

// Function to check if a symbol exists in the current scope
struct Symbol* symbolExistsInCurrent(struct Node* head, const char* name) {
    // To be completed
}

// Function to print all symbols in the current scope
void printCurrentScope(struct Node* head) {
    // To be completed
}

// Function to print all symbols in all scopes
void printAllScopes(struct Node* head) {
    // To be completed
}

// Function to free the memory allocated for the linked list
void freeEnvironment(struct Node** head) {
    // To be completed
}
