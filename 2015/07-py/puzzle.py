import os
import re


def value_at(memory, connections, wire_name):
    if memory.get(wire_name, None):
        print(f"{wire_name} = {memory.get(wire_name)}")
        return memory.get(wire_name)
    for connection in connections:
        if connection.endswith(f" -> {wire_name}"):
            match = re.match(r"^NOT ([a-z0-9]*) -> ([a-z]*)", connection)
            if match:
                # not
                first, _ = match.groups()
                if first.isdigit():
                    first = int(first)
                else:
                    first = value_at(memory, connections, first)

                memory[wire_name] = first ^ 65535
                return memory[wire_name]
            match = re.match(r"^(\d*) -> (.*)", connection)
            if match:
                # costant value
                value, _ = match.groups()

                memory[wire_name] = int(value)
                return memory[wire_name]
            match = re.match(r"^([a-z]*) -> (.*)", connection)
            if match:
                # copy value
                first, _ = match.groups()
                return value_at(memory, connections, first)
            match = re.match(r"^([a-z0-9]*) (AND|OR|LSHIFT|RSHIFT) ([a-z0-9]*) -> ([a-z]*)", connection)
            if match:
                # binary operator
                first, operator, second, _ = match.groups()
                if first.isdigit():
                    first = int(first)
                else:
                    first = value_at(memory, connections, first)
                if second.isdigit():
                    second = int(second)
                else:
                    second = value_at(memory, connections, second)
                if operator == "AND":
                    memory[wire_name] = first & second
                    return memory[wire_name]
                if operator == "OR":
                    memory[wire_name] = first | second
                    return memory[wire_name]
                if operator == "LSHIFT":
                    memory[wire_name] = first << second
                    return memory[wire_name]
                if operator == "RSHIFT":
                    memory[wire_name] = first >> second
                    return memory[wire_name]
                raise NotImplementedError(f"unknown binary {operator} in {connection}")

            raise NotImplementedError(f"unknown operations {connection}")

    raise NotImplementedError(f"can't find value for {wire_name}")



def main():

    file_name = "/input.txt"
    with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
              'r') as file:
        connections = list(map(lambda x: x.strip(), file.readlines()))
        print(value_at({}, connections, "a"))


if __name__ == "__main__":
    main()