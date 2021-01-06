import os
import re


input_file = "/input.txt"
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    acc_step1 = 0
    acc_step2 = 0
    for line in file:
        findall = re.findall(r"[\d]+", line.strip())
        row_sorted = sorted(list(map(lambda x: int(x.strip()), findall)))
        acc_step1 += row_sorted[-1] - row_sorted[0]
        clear_div = None
        for a in row_sorted:
            for b in row_sorted:
                clear_div = int(a / b) if a > b and a % b == 0 and clear_div is None else clear_div
        acc_step2 += clear_div

    print("step1", acc_step1)
    print("step2", acc_step2)