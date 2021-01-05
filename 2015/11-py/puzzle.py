letters = list(map(chr, range(97, 123)))


def next_pwd(pwd_str, index):
    pwd = list(pwd_str)
    if pwd[len(pwd) - index] != 'z':
        pwd[len(pwd) - index] = letters[letters.index(pwd[len(pwd) - index]) + 1]
        if pwd[len(pwd) - index] in ['i', 'o', 'l']:
            return next_pwd("".join(pwd), index)
        else:
            return "".join(pwd)
    else:
        pwd[len(pwd) - index] = 'a'
        return next_pwd("".join(pwd), index + 1)


def valid_password(pwd):
    pwd_indexes = list(map(lambda x : letters.index(x), pwd))
    sequence = False
    valid_letters = True
    pairs = []
    for i in range(len(pwd_indexes)):
        if i < len(pwd_indexes) - 2 and pwd_indexes[i] + 1 == pwd_indexes[i + 1] and pwd_indexes[i + 1] + 1 == pwd_indexes[i + 2]:
            sequence = True
        if pwd[i] in ['i', 'o', 'l']:
            valid_letters = False
        if i < len(pwd) - 1 and pwd[i] == pwd[i+1]:
            if (i > 0 and pwd[i] != pwd[i - 1]) or i == 0:
                if pwd[i] not in pairs:
                    pairs.append(pwd[i])
    #print(sequence, valid_letters, pairs)
    return sequence and valid_letters and (len(pairs) == 2)


pwd = "hepxcrrq"
while not valid_password(pwd):
    pwd = next_pwd(pwd, 1)
print(pwd)