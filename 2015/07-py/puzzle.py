import re
import os


def main():
    memory = {}
    file_name = "/input.txt"
    with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
              'r') as file:
        for line in file:
            print(line.strip())
            match = re.match(r'^([a-z0-9]*) -> (.*)', line.strip())
            if match is not None:
                # costant to line
                print(match.groups())
                first, dest = match.groups()
                if first.isdigit() or memory.get(first, None) is not None:
                    memory[dest] = (int(first) if first.isdigit() else memory.get(first))
                continue

            match = re.match(r'^([a-z0-9]*) (AND|OR|LSHIFT|RSHIFT) ([a-z0-9]*) -> ([a-z]*)', line.strip())
            if match is not None:
                #binary operator
                first, operator, second, dest = match.groups()
                if operator == "AND" and memory.get(first, None) is not None and memory.get(second, None) is not None:
                    memory[dest] = (int(first) if first.isdigit() else memory.get(first, 0)) & (int(second) if second.isdigit() else memory.get(second, 0))
                if operator == "OR" and memory.get(first, None) is not None and memory.get(second, None) is not None:
                    memory[dest] = (int(first) if first.isdigit() else memory.get(first, 0)) | (int(second) if second.isdigit() else memory.get(second, 0))
                if operator == "LSHIFT" and memory.get(first, None) is not None:
                    memory[dest] = (int(first) if first.isdigit() else memory.get(first, 0)) << int(second)
                if operator == "RSHIFT" and memory.get(first, None) is not None:
                    memory[dest] = (int(first) if first.isdigit() else memory.get(first, 0)) >> int(second)
                continue

            match = re.match(r'^NOT ([a-z0-9]*) -> ([a-z]*)', line.strip())
            if match is not None:
                #not operator
                first, dest = match.groups()
                if first.isdigit() or memory.get(first, None) is not None:
                    memory[dest] = (int(first) if first.isdigit() else memory.get(first))^ 65535
                continue

            raise NotImplementedError(f"unknow operations {line.strip()}")

    print(memory)


if __name__ == "__main__":
    main()