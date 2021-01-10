import os
import re


chars = list(map(chr, range(97, 123)))


def decrypt(name, sector):
    result = ""
    for c in name:
        if c == '-':
            result += ' '
        else:
            result += chars[(chars.index(c) + sector) % len(chars)]
    return result


input_file = "/input.txt"
with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file, 'r') as file:
    sector_sum = 0
    decrypted = []
    for line in file:
        match = re.match(r"(.*)-(\d+)\[(\w*)\]", line.strip())
        name, sector, checksum = match.groups()
        letters = {}
        for l in name:
            if l != '-':
                letters[l] = letters.get(l, 0) + 1
        most_common = sorted(letters.values(), reverse=True)[0:5]
        valid = True
        for c in checksum:
            if letters.get(c, 0) not in most_common:
                valid = False
        if valid:
            sector_sum += int(sector)
            decrypted = decrypt(name, int(sector))
            if "northpole object" in decrypted:
                print(decrypted, sector)

    print(sector_sum)