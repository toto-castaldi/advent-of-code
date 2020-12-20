import os
from functools import reduce


def main():
    file_path = "./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt"

    with open(file_path, 'r') as file:
        path = file.read()


if __name__ == "__main__":
    main()