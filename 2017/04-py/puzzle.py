import os


def main():
    input_file = "/input.txt"
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        valid_count = 0
        for line in file:
            line = line.strip()
            valid_passphrase = True
            words = line.split(' ')
            for word in words:
                if words.count(word) > 1:
                    valid_passphrase = False
                    break
            print(line, valid_passphrase)
            valid_count += 1 if valid_passphrase else 0
        print(valid_count)


if __name__ == "__main__":
    main()