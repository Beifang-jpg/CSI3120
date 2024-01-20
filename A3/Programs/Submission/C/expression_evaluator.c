#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include "expression_evaluator.h"




// Function to create a stack with a given capacity
struct Stack* createStack(unsigned capacity) {
    struct Stack* stack = (struct Stack*)malloc(sizeof(struct Stack));
    if (stack == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        exit(EXIT_FAILURE);
    }
    stack->top = -1;
    stack->capacity = capacity;
    stack->array = (double*)malloc(stack->capacity * sizeof(double));
    if (stack->array == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        exit(EXIT_FAILURE);
    }
    return stack;
}

// Function to check if the stack is empty
int isEmpty(struct Stack* stack) {
    return stack->top == -1;
}

// Function to push an element onto the stack
void push(struct Stack* stack, double item) {
    if (stack->top == stack->capacity - 1) {
        fprintf(stderr, "Stack overflow\n");
        exit(EXIT_FAILURE);
    }
    stack->array[++stack->top] = item;
}

// Function to pop an element from the stack
double pop(struct Stack* stack) {
    if (isEmpty(stack)) {
        fprintf(stderr, "Stack underflow\n");
        exit(EXIT_FAILURE);
    }
    return stack->array[stack->top--];
}

double peek(struct Stack* stack) {
    if (isEmpty(stack)) {
        fprintf(stderr, "Stack underflow\n");
        exit(EXIT_FAILURE);
    }
    return stack->array[stack->top];
}

// Function to get the precedence of an operator
int precedence(char op) {
    switch (op) {
        case '+':
        case '-':
            return 1;
        case '*':
        case '/':
            return 2;
        default:
            return -1;  // Invalid operator
    }
}

// Function to evaluate a given arithmetic expression
double evaluateExpression(struct Stack* expressionStack) {
    struct Stack* operandStack = createStack(200);
    struct Stack* operatorStack = createStack(200);

    while (!isEmpty(expressionStack)) {
        double token = pop(expressionStack);
        if (token == '(') {
            // If the character is an opening parenthesis, push it onto the operator stack
            push(operatorStack, token);
        } else if (token == ')') {
            // If the character is a closing parenthesis, evaluate the expression in the parentheses
            while (!isEmpty(operatorStack) && operatorStack->array[operatorStack->top] != '(') {
                double operand2 = pop(operandStack);
                double operand1 = pop(operandStack);
                char op = pop(operatorStack);

                // Perform the operation and push the result onto the operand stack
                switch (op) {
                    case '+':
                        push(operandStack, operand1 + operand2);
                        break;
                    case '-':
                        push(operandStack, operand1 - operand2);
                        break;
                    case '*':
                        push(operandStack, operand1 * operand2);
                        break;
                    case '/':
                        push(operandStack, operand1 / operand2);
                        break;
                }
            }
            // Pop the opening parenthesis from the operator stack
            pop(operatorStack);
        } else if (token == '+' || token == '-' || token == '*' || token == '/') {
            // If the character is an operator, evaluate any higher or equal precedence operators
            while (!isEmpty(operatorStack) && precedence(operatorStack->array[operatorStack->top]) >= precedence(token)) {
                double operand2 = pop(operandStack);
                double operand1 = pop(operandStack);
                char op = pop(operatorStack);

                // Perform the operation and push the result onto the operand stack
                switch (op) {
                    case '+':
                        push(operandStack, operand1 + operand2);
                        break;
                    case '-':
                        push(operandStack, operand1 - operand2);
                        break;
                    case '*':
                        push(operandStack, operand1 * operand2);
                        break;
                    case '/':
                        push(operandStack, operand1 / operand2);
                        break;
                }
            }
            // Push the current operator onto the operator stack
            push(operatorStack, token);
        } 
        else {
            double operand = token;
            push(operandStack, operand);
        }
    }

    // Evaluate any remaining operators
    while (!isEmpty(operatorStack)) {
        double operand2 = pop(operandStack);
        double operand1 = pop(operandStack);
        char op = pop(operatorStack);

        // Perform the operation and push the result onto the operand stack
        switch (op) {
            case '+':
                push(operandStack, operand1 + operand2);
                break;
            case '-':
                push(operandStack, operand1 - operand2);
                break;
            case '*':
                push(operandStack, operand1 * operand2);
                break;
            case '/':
                push(operandStack, operand1 / operand2);
                break;
        }
    }

    // The final result is on the top of the operand stack
    double result = pop(operandStack);

    // Free the memory allocated for the stacks
    free(operandStack->array);
    free(operatorStack->array);
    free(operandStack);
    free(operatorStack);


    return result;
}
