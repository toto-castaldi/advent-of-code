import os
import re


input_file = "/input.txt"

with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    possible_count = 0
    for line in file:
        match = re.match(r"[ \t]*(\d*)[ \t]*(\d*)[ \t]*(\d*)", line.strip())
        edges = sorted(list(map(lambda x: int(x), match.groups())))
        if edges[0] + edges[1] > edges[2]:
            possible_count += 1
    print("step_1", possible_count)
