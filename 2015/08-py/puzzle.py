import os
import re


def main():
    file_name = "/input-text.txt"
    with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
              'r') as file:
        code_string = 0
        step_1_memory_string = 0
        for line in file:
            line = line.strip()
            original = line
            step1_encoding = line
            step2_encoding = line
            code_string += len(step1_encoding)
            step1_encoding = step1_encoding[1:-1]
            step1_encoding = step1_encoding.replace("\\\"", "\"")
            findall = re.findall(r"(\\x[a-f0-9]{2})", step1_encoding)
            if findall:
                for hexadecimal in findall:
                    step1_encoding = step1_encoding.replace(hexadecimal, "_")
            step1_encoding = step1_encoding.replace("\\\\", "\\")
            print(original, "->", step1_encoding, "->", step2_encoding)
            step_1_memory_string += len(step1_encoding)

        print("step_1", code_string, step_1_memory_string, {code_string - step_1_memory_string})


if __name__ == "__main__":
    main()