import os
import re


def main():
    lights = [[False for col in range(1000)] for row in range(1000)]
    with open("./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt",
              'r') as file:
        count_lit = 0;
        for line in file:
            matches = re.findall(r"(on|off|toggle) (\d+),(\d+) through (\d+),(\d+)", line)[0]
            for row in range(int(matches[1]), int(matches[3]) + 1):
                for col in range(int(matches[2]), int(matches[4]) + 1):
                    prev = lights[row][col]
                    if matches[0] == "on":
                        lights[row][col] = True
                    elif matches[0] == "off":
                        lights[row][col] = False
                    else:
                        lights[row][col] = not lights[row][col]
                    count_lit += 1 if not prev and lights[row][col] else -1 if prev and not lights[row][col] else 0
        print(count_lit)


if __name__ == "__main__":
    main()