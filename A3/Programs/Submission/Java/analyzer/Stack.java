package analyzer;
public class Stack {
    private int top;
    private int capacity;
    private double[] array;

    public Stack(int capacity) {
        this.top = -1;
        this.capacity = capacity;
        this.array = new double[capacity];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(double item) {
        if (top == capacity - 1) {
            System.out.println("Stack overflow");
            return;
        }
        top++;
        array[top] = item;
    }

    public double pop() {
        if (isEmpty()) {
            System.out.println("Stack underflow");
            return -1; // Assuming -1 represents an error in this context
        }
        double item = array[top];
        top--;
        return item;
    }

    public double peek() {
        if (isEmpty()) {
            System.out.println("Stack underflow");
            return -1; // Assuming -1 represents an error in this context
        }
        return array[top];
    }
}
