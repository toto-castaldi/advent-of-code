import os
import re


def twice_count(string):
    result = 0
    for i in range(len(string)):
        couple = string[i:i + 2]
        if len(couple) == 2:
            if couple[0] == couple[1]:
                result += 1
    return result


def nice_first(string):
    if "ab" in string or "cd" in string or "pq" in string or "xy" in string:
        return False

    vowel_count = 0
    for vowel in "aeiou":
        for c in string:
            if vowel == c:
                vowel_count += 1
            if vowel_count >= 3:
                break
    if vowel_count < 3:
        return False

    if twice_count(string) < 1:
        return False

    return True


def nice_second(string):
    search_pair = re.search(r"([\w]{2}).*\1", string)

    search_between = re.search(r"(.)\w\1", string)

    return (search_pair is not None) and (search_between is not None)


def main():

    print("first", nice_first("aaa"))
    print("first", nice_first("jchzalrnumimnmhp"))
    print("first", nice_first("haegwjzuvuyypxyu"))
    print("first", nice_first("dvszwmarrgswjxmb"))
    print("first", nice_first("ugknbfddgicrmopn"))
    print("second", nice_second("qjhvhtzxzqqjkmpb"))
    print("second", nice_second("xxyxx"))
    print("second", nice_second("uurcxstgmygtbstg"))
    print("second", nice_second("ieodomkazucvgmuy"))

    nice_first_count = 0
    nice_second_count = 0
    with open("./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt",
              'r') as file:
        for line in file:
            if len(line.strip()) > 0:
                if nice_first(line.strip()):
                    nice_first_count += 1
                if nice_second(line.strip()):
                    nice_second_count += 1

    print(nice_first_count)
    print(nice_second_count)


if __name__ == "__main__":
    main()