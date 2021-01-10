import os
import re


input_file = "/input.txt"
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    sector_sum = 0
    for line in file:
        match = re.match(r"(.*)-(\d+)\[(\w*)\]", line.strip())
        name, sector, checksum = match.groups()
        letters = {}
        for l in name:
            if l != '-':
                letters[l] = letters.get(l, 0) + 1
        most_common = sorted(letters.values(), reverse=True)[0:5]
        valid = True
        for c in checksum:
            if letters.get(c, 0) not in most_common:
                valid = False
        print(valid, name, checksum, letters, most_common)
        if valid:
            sector_sum += int(sector)
    print(sector_sum)