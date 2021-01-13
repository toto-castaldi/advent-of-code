import re
import os


file_name = "/input.txt"
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as file:
    the_sue = {
        "children": 3,
        "cats": 7,
        "samoyeds": 2,
        "pomeranians": 3,
        "akitas": 0,
        "vizslas": 0,
        "goldfish": 5,
        "trees": 3,
        "cars": 2,
        "perfumes": 1
    }
    for line in file:
        fm = re.fullmatch(r"Sue (\d*): (\w*): (\d*), (\w*): (\d*), (\w*): (\d*)", line.strip())
        sue_num, field_1, value_1, field_2, value_2, field_3, value_3 = fm.groups()
        if the_sue.get(field_1, None) == int(value_1) and the_sue.get(field_2, None) == int(value_2) and the_sue.get(field_3, None) == int(value_3):
            print(sue_num)


