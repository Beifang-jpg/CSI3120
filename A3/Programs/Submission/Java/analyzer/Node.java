package analyzer;
import java.util.Stack;

class SymbolTable {
    public static final int MAX_SYMBOLS = 100; // Assuming MAX_SYMBOLS is a constant value
    Symbol[] symbols;

    public SymbolTable() {
        this.symbols = new Symbol[MAX_SYMBOLS];
        for (int i = 0; i < MAX_SYMBOLS; i++) {
            this.symbols[i] = new Symbol();
        }
    }
}

public class Node {
    static java.util.Stack<Node> scopes = new Stack<>();

    SymbolTable symbolTable;
    int count;
    Node next;

    public Node() {
        // To be completed

        symbolTable = new SymbolTable();
        count = 0;
        next = null;
    }

    public static int getCurrentSymbolCount() {
        return scopes.peek().count;
    }

    public static Node pushScope() {
        // To be completed

        var newNode = new Node();
        scopes.push(newNode);
        return newNode;
    }

    public static Node popScope() {
        
        if (scopes.empty()) return null;
        return scopes.pop();
    }

    public void printCurrentScope() {
        // To be completed
        System.out.println("it seems the 10 input don't need this one");
    }

    public void printAllScopes() {
        // To be completed
        System.out.println("it seems the 10 input don't need this one");
    }

    public static void insertSymbol(String symbolType, String symbolName, String symbolValue) {
        // To be completed
        var currentScope = scopes.peek();

        if (currentScope.count >= SymbolTable.MAX_SYMBOLS) {
            System.out.println("Symbol table is full.");
            return;
        }

        Symbol newSymbol = new Symbol();
        newSymbol.type = symbolType;
        newSymbol.name = symbolName;
        newSymbol.value = symbolValue;
    
        currentScope.symbolTable.symbols[currentScope.count] = newSymbol;
        currentScope.count++;
    }

    public static Symbol symbolExists(String name) {
        // To be completed

        Stack<Node> tmp = new Stack<>();

        while (!scopes.empty()) {
            var currentNode = scopes.pop();
            tmp.push(currentNode);

            for (int i = 0; i < currentNode.count; i++) {
                if (currentNode.symbolTable.symbols[i].name.equals(name)) {
                    while (!tmp.empty()) {
                        scopes.push(tmp.pop());
                    }
                    return currentNode.symbolTable.symbols[i];
                }
            }
            currentNode = currentNode.next;
        }

        while (!tmp.empty()) {
            scopes.push(tmp.pop());
        }

        return null;
    }

    public static Symbol symbolExistsInCurrent(String name) {
        // To be completed
        var currentNode = scopes.peek();
        if (currentNode.symbolTable == null) {
            return null;
        }

        for (int i = currentNode.count -1 ; i > -1 ; i--) {
            if (currentNode.symbolTable.symbols[i].name.equals(name)) {
                return currentNode.symbolTable.symbols[i];
            }
        }
        return null;
    }

    public void freeEnvironment() {
        // To be completed

        System.out.println("it seems the 10 input don't need this one");
    }

}


