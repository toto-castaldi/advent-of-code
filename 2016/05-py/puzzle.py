import hashlib


def password(seed):
    counter = 0
    while True:
        yield hashlib.md5((seed + str(counter)).encode()).hexdigest(), counter
        counter += 1


step1_the_pwd = ""
step2_the_pwd = [' ' for i in range(8)]
for pwd, index in password("wtnhxymk"):
    if pwd[0:5] == "00000":
        if len(step1_the_pwd) < 8:
            step1_the_pwd += pwd[5]
        if pwd[5] in [str(i) for i in range(8)] and step2_the_pwd[int(pwd[5])] == ' ':
            step2_the_pwd[int(pwd[5])] = pwd[6]
        print(step1_the_pwd.rjust(8, ' '), "<-->", ''.join(step2_the_pwd))
    if len(step1_the_pwd) == 8 and (' ' not in step2_the_pwd):
        break



