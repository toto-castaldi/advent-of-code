import os


def nice(string):
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

    twice_count = 0
    for i in range(len(string)):
        couple = string[i:i+2]
        if len(couple) == 2:
            if couple[0] == couple[1]:
                twice_count += 1
    if twice_count < 1:
        return False

    return True


def main():
    print(nice("ugknbfddgicrmopn"))
    print(nice("aaa"))
    print(nice("jchzalrnumimnmhp"))
    print(nice("haegwjzuvuyypxyu"))
    print(nice("dvszwmarrgswjxmb"))

    nice_count = 0
    with open("./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt",
              'r') as file:
        for line in file:
            if len(line.strip()) > 0:
                if nice(line.strip()):
                    nice_count += 1

    print(nice_count)


if __name__ == "__main__":
    main()