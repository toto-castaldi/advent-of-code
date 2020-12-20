import hashlib


def advent_coin(seed):
    counter = 0
    while True:
        yield hashlib.md5((seed + str(counter)).encode()).hexdigest(), counter
        counter += 1


def main():
    puzzle_input = "yzbqklnj"

    hash1 = None
    hash2 = None

    for code, counter in advent_coin(puzzle_input):
        hash1 = (code, counter) if hash1 is None and code[:5] == "00000" else hash1
        hash2 = (code, counter) if hash2 is None and code[:6] == "000000" else hash2

        if hash1 is not None and hash2 is not None:
            break

    print(hash1, hash2)


if __name__ == "__main__":
    main()