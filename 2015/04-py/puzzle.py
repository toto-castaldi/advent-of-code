import hashlib


def advent_coin(seed):
    counter = 0
    while True:
        to_be_coded = seed + str(counter)
        yield hashlib.md5(to_be_coded.encode()).hexdigest(), counter
        counter += 1


def main():
    input = "yzbqklnj"
    for code, counter in advent_coin(input):
        if code[:5] == "00000":
            print(code, counter)
            break
    for code, counter in advent_coin(input):
        if code[:6] == "000000":
            print(code, counter)
            break


if __name__ == "__main__":
    main()