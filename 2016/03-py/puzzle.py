import os
import re


def possible(triangle):
    edges = sorted(triangle)
    return edges[0] + edges[1] > edges[2]


input_file = "/input.txt"
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    possible_count_step1 = 0
    possible_count_step2 = 0
    lines = []
    for line in file:
        match = re.match(r"[ \t]*(\d*)[ \t]*(\d*)[ \t]*(\d*)", line.strip())
        line_numbers = list(map(lambda x: int(x), match.groups()))
        lines.append(line_numbers)
        if possible(line_numbers):
            possible_count_step1 += 1
        if len(lines) == 3:
            for i in range(3):
                if possible([lines[0][i], lines[1][i], lines[2][i]]):
                    possible_count_step2 += 1
            lines = []
    print("step_1", possible_count_step1)
    print("step_2", possible_count_step2)
