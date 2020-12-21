import os


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
    pairs = {}
    for index in range(1, len(string)):
        if index == 1 or not(string[index - 2] == string[index - 1] == string[index]):
            current_count = pairs.get(string[index - 1:index + 1], 0)
            current_count += 1
            pairs[string[index - 1:index + 1]] = current_count
    if len(pairs.values()) == 0:
        return False
    pairs_count = sorted(list(pairs.values()), reverse=True)
    if pairs_count[0] == 1 or (len(pairs_count) > 1 and pairs_count[1] > 1):
        return False
    if twice_count(string[0::2]) < 1 and twice_count(string[1::2]) < 1:
        return False

    return True


def main():
    print("first", nice_first("ugknbfddgicrmopn"))
    print("first", nice_first("aaa"))
    print("first", nice_first("jchzalrnumimnmhp"))
    print("first", nice_first("haegwjzuvuyypxyu"))
    print("first", nice_first("dvszwmarrgswjxmb"))
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
    #172 too high
    print(nice_second_count)


if __name__ == "__main__":
    main()