class Symbol:
    def __init__(self):
        self.type = ""
        self.name = ""
        self.value = ""


class SymbolTable:
    def __init__(self):
        self.symbols = [Symbol() for _ in range(MAX_SYMBOLS)]


class Node:
    def __init__(self):
        # To be completed

    def push_scope(self):
        # To be completed

    def pop_scope(self):
        # To be completed

    def print_current_scope(self):
        # To be completed

    def print_all_scopes(self):
        # To be completed

    def insert_symbol(self, symbol_type, symbol_name, symbol_value):
        # To be completed

    def symbol_exists(self, name):
        # To be completed
    
    def symbol_existsInCurrent(self, name):
        # To be completed


def free_environment(head):
    # To be completed

MAX_SYMBOLS = 100

