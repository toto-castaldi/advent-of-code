import os
from functools import reduce


def main():
    file_path = "./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt"

    with open(file_path, 'r') as file:
        path = file.read()

    print(reduce((lambda p, n: p + (1 if n == '(' else -1)), path, 0))

    level = 0
    basement = -1
    step = 0

    for p in path:
        level += (1 if p == '(' else -1)
        if level == basement:
            print(step)
            break
        step += 1


if __name__ == "__main__":
    main()