import os
import re


def main():
    lights = [[(False, 0) for col in range(1000)] for row in range(1000)]
    with open("./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt",
              'r') as file:
        count_lit = 0
        brightness_total = 0
        for line in file:
            matches = re.findall(r"(on|off|toggle) (\d+),(\d+) through (\d+),(\d+)", line)[0]
            for row in range(int(matches[1]), int(matches[3]) + 1):
                for col in range(int(matches[2]), int(matches[4]) + 1):
                    prev = lights[row][col]
                    if matches[0] == "on":
                        lights[row][col] = (True, prev[1] + 1)
                    elif matches[0] == "off":
                        lights[row][col] = (False, prev[1] - 1 if prev[1] >= 1 else 0)
                    else:
                        lights[row][col] = (not prev[0], prev[1] + 2)
                    count_lit += 1 if not prev[0] and lights[row][col][0] else -1 if prev[0] and not lights[row][col][0] else 0
                    brightness_total += lights[row][col][1] - prev[1]
        print(count_lit)
        print(brightness_total)


if __name__ == "__main__":
    main()