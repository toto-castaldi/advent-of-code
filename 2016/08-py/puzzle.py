import os
import re


def main():
    input_file = "/input.txt"
    wide = 50
    tall = 6
    screen = [['.' for _ in range(wide)] for _ in range(tall)]
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        for line in file:
            line = line.strip()
            match = re.match(r"rect (\d+)x(\d+)", line)
            if match:
                x_len = int(match.groups()[0])
                y_len = int(match.groups()[1])
                for y in range(y_len):
                    for x in range(x_len):
                        screen[y][x] = '#'
            match = re.match(r"rotate column x=(\d+) by (\d+)", line)
            if match:
                column_index = int(match.groups()[0])
                steps = int(match.groups()[1])
                column_copy = [' ' for _ in range(len(screen))]
                for c in range(len(screen)):
                    column_copy[c] = screen[c][column_index]
                for c in range(len(screen)):
                    screen[(c + steps) % len(screen)][column_index] = column_copy[c]
            match = re.match(r"rotate row y=(\d+) by (\d+)", line)
            if match:
                row_index = int(match.groups()[0])
                steps = int(match.groups()[1])
                row_copy = [x for x in screen[row_index]]
                for c in range(len(screen[row_index])):
                    screen[row_index][(c + steps) % len(screen[row_index])] = row_copy[c]
        print(screen)
        acc = 0
        for row in screen:
            for c in row:
               acc += 1 if c == '#' else 0
        print(acc)

if __name__ == "__main__":
    main()