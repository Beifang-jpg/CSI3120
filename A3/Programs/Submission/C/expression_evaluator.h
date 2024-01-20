#ifndef EXPRESSION_EVALUATOR_H
#define EXPRESSION_EVALUATOR_H

// Define a structure for the stack
struct Stack {
    int top;
    unsigned capacity;
    double* array;
};

struct Stack* createStack(unsigned capacity);

void push(struct Stack* stack, double item);

double pop(struct Stack* stack);

double evaluateExpression(struct Stack* expressionStack);

int precedence(char op);

#endif