import os


input_file = "/input.txt"
keypad_step1 = [
            ['1', '2', '3'],
            ['4', '5', '6'],
            ['7', '8', '9']
        ]
keypad_step2 = [
            [' ', ' ', '1', ' ', ' '],
            [' ', '2', '3', '4', ' '],
            ['5', '6', '7', '8', '9'],
            [' ', 'A', 'B', 'C', ' '],
            [' ', ' ', 'D', ' ', ' ']
        ]
combination_step1 = []
combination_step2 = []
x_step1 = 1
y_step1 = 1
x_step2 = 0
y_step2 = 2
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    for line in file:
        line = line.strip()
        for dir in line:
            y_step1 += -1 if dir == 'U' and y_step1 > 0 else +1 if dir == 'D' and y_step1 < 2 else 0
            x_step1 += -1 if dir == 'L' and x_step1 > 0 else +1 if dir == 'R' and x_step1 < 2 else 0

            pre_y = y_step2
            pre_x = x_step2
            y_step2 += -1 if dir == 'U' and y_step2 > 0 else +1 if dir == 'D' and y_step2 < 4 else 0
            x_step2 += -1 if dir == 'L' and x_step2 > 0 else +1 if dir == 'R' and x_step2 < 4 else 0
            if keypad_step2[y_step2][x_step2] == ' ':
                x_step2 = pre_x
                y_step2 = pre_y

        combination_step1.append(keypad_step1[y_step1][x_step1])
        combination_step2.append(keypad_step2[y_step2][x_step2])

print("first", combination_step1)
print("second", combination_step2)