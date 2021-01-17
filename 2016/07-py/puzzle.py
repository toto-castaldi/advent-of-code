import os
import re


def has_abba(s):
    match = re.match(r".*(.)(.)\2\1.*", s)
    print(s)
    if match is not None:
        return match.groups()[0] != match.groups()[1]
    return False


def main():
    input_file = "/input-test.txt"
    abba_count = 0
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        for line in file:
            line = line.strip()
            abba_outside = False
            abba_inside = False
            if '[' in line and ']' in line and line.index('[') < line.index(']'):
                abba_outside = has_abba(line[0:line.index('[')]) or has_abba(line[line.index(']') + 1:len(line)])
                abba_inside = has_abba(line[line.index('[') + 1: line.index(']')])
            else:
                abba_outside = has_abba(line)
            print(line, abba_outside and not abba_inside)
            abba_count += 1 if abba_outside and not abba_inside else 0
        print(abba_count)


if __name__ == "__main__":
    main()

# 149 too high