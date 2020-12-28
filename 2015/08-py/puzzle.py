import os
import re


def main():
    file_name = "/input.txt"
    with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
              'r') as file:
        code_string = 0
        memory_string = 0
        for line in file:
            line = line.strip()
            original = line
            code_string += len(line)
            line = line[1:-1]
            line = line.replace("\\\"", "\"")
            findall = re.findall(r"(\\x[a-f0-9]{2})", line)
            if findall:
                for hexadecimal in findall:
                    line = line.replace(hexadecimal, "_")
            line = line.replace("\\\\", "\\")
            print(original, "->", line)
            memory_string += len(line)

        #1258 low
        print("step_1", code_string, memory_string, {code_string - memory_string})


if __name__ == "__main__":
    main()