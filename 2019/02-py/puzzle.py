import os


def execute_program(memory):
    instruction_pointer = 0
    opcode = memory[instruction_pointer]
    while opcode != 99:
        if opcode == 1:
            memory[memory[instruction_pointer + 3]] = memory[
                                                                          memory[instruction_pointer + 1]] + \
                                                      memory[
                                                                          memory[instruction_pointer + 2]]
            instruction_pointer += 4
        elif opcode == 2:
            memory[memory[instruction_pointer + 3]] = memory[
                                                                          memory[instruction_pointer + 1]] * \
                                                      memory[
                                                                          memory[instruction_pointer + 2]]
            instruction_pointer += 4
        opcode = memory[instruction_pointer]
    return memory[0]


input_file = "/input.txt"
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
          'r') as file:
    initial_program_memory = list(map(lambda x: int(x.strip()), file.readline().strip().split(",")))
    program_memory = initial_program_memory.copy()
    program_memory[1] = 12 #noun
    program_memory[2] = 2  #verb
    print("step_1", execute_program(program_memory))

    for noun in range(100):
        for verb in range(100):
            program_memory = initial_program_memory.copy()
            program_memory[1] = noun
            program_memory[2] = verb
            output = execute_program(program_memory)
            if output == 19690720:
                print("step_2", noun, verb, 100 * noun + verb)


