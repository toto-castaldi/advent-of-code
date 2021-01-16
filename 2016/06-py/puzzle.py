import os


def main():
    input_file = "/input.txt"
    common_chars = []
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        for line in file:
            for index, c in enumerate(line.strip()):
                if len(common_chars) <= index:
                    common_chars.append({})
                common_char = common_chars[index]
                common_char[c] = common_char.get(c, 0) + 1
        error_corrected_step1 = []
        error_corrected_step2 = []
        for common_char in common_chars:
            error_corrected_step1.append(sorted(common_char.items(), reverse=True, key=lambda item: item[1])[0][0])
            error_corrected_step2.append(sorted(common_char.items(), reverse=False, key=lambda item: item[1])[0][0])
        print("step_1", ''.join(error_corrected_step1))
        print("step_2", ''.join(error_corrected_step2))


if __name__ == "__main__":
    main()