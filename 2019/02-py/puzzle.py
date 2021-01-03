import os


input_file = "/input.txt"
program_index = 0
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
          'r') as file:
    program = list(map(lambda x: int(x.strip()), file.readline().strip().split(",")))
    print(program)
    program[1] = 12
    program[2] = 2
    while program[program_index] != 99:
        if program[program_index] == 1:
            program[program[program_index + 3]] = program[program[program_index + 1]] + program[program[program_index + 2]]
            program_index += 4
        elif program[program_index] == 2:
            program[program[program_index + 3]] = program[program[program_index + 1]] * program[program[program_index + 2]]
            program_index += 4
        print(program)
