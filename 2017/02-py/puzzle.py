import os
import re


input_file = "/input.txt"
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    acc = 0
    for line in file:
        findall = re.findall(r"[\d]+", line.strip())
        row_sorted = sorted(list(map(lambda x: int(x.strip()), findall)))
        acc += row_sorted[-1] - row_sorted[0]
    print(acc)