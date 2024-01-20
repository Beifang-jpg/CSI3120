package analyzer;
public class ExpressionEvaluator {
    private static int precedence(char op) {
        if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        } else {
            return -1; // Invalid operator
        }
    }

    public static double evaluateExpression(Stack expression) {
        Stack operandStack = new Stack(200);
        Stack operatorStack = new Stack(200);

        while (!expression.isEmpty()) {
            double token = expression.pop() ;

            if (token == '(') {
                operatorStack.push(token);
            } else if (token == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    char op = (char) operatorStack.pop();

                    // Perform the operation and push the result onto the operand stack
                    operandStack.push(performOperation(op, operand1, operand2));
                }

                // Pop the opening parenthesis from the operator stack
                operatorStack.pop();
            } else if (token == '+' || token == '-' || token == '*' || token == '/') {
                while (!operatorStack.isEmpty() && precedence((char) operatorStack.peek()) >= precedence((char) token)) {
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    char op = (char) operatorStack.pop();
                    

                    // Perform the operation and push the result onto the operand stack
                    operandStack.push(performOperation(op, operand1, operand2));
                }
                
                // Push the current operator onto the operator stack
                operatorStack.push(token);
            } else {
                // Handle operands
                
                double operand = Double.parseDouble(String.valueOf(token));
                operandStack.push(operand);
            }
        }

        // Evaluate any remaining operators
        while (!operatorStack.isEmpty()) {
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            char op = (char) operatorStack.pop();

            // Perform the operation and push the result onto the operand stack
            operandStack.push(performOperation(op, operand1, operand2));
        }

        // The final result is on the top of the operand stack
        return operandStack.pop();
    }

    private static double performOperation(char op, double operand1, double operand2) {
        switch (op) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

}

