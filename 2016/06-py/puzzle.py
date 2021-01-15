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
        error_corrected = []
        for common_char in common_chars:
            error_corrected.append(sorted(common_char.items(), reverse=True, key=lambda item: item[1])[0][0])
        print(''.join(error_corrected))


if __name__ == "__main__":
    main()