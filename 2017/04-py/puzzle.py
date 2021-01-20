import os
import itertools


def main():
    input_file = "/input.txt"
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        valid_count_step1 = 0
        valid_count_step2 = 0
        for line in file:
            line = line.strip()
            valid_passphrase_step1 = True
            valid_passphrase_step2 = True
            words = line.split(' ')
            for word in words:
                if words.count(word) > 1:
                    valid_passphrase_step1 = False
                all_permutations = itertools.permutations(word)
                for perm in all_permutations:
                    if ''.join(perm) != word and words.count(''.join(perm)) > 0:
                        valid_passphrase_step2 = False
            print(line, valid_passphrase_step1, valid_passphrase_step2)
            valid_count_step1 += 1 if valid_passphrase_step1 else 0
            valid_count_step2 += 1 if valid_passphrase_step2 else 0

        print("step1", valid_count_step1)
        print("step2", valid_count_step2)
        #step2 284 is too high


if __name__ == "__main__":
    main()