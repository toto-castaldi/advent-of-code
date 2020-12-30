import os


input_file = "/input.txt"
keypad = [
            ['1', '2', '3'],
            ['4', '5', '6'],
            ['7', '8', '9']
        ]
combination = []
x = 1
y = 1
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    for line in file:
        line = line.strip()
        for dir in line:
            y += -1 if dir == 'U' and y > 0 else +1 if dir == 'D' and y < 2 else 0
            x += -1 if dir == 'L' and x > 0 else +1 if dir == 'R' and x < 2 else 0
        combination.append(keypad[y][x])

print("first",  combination)