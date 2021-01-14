import hashlib


def password(seed):
    counter = 0
    while True:
        yield hashlib.md5((seed + str(counter)).encode()).hexdigest(), counter
        counter += 1


the_pwd = ""
for pwd, index in password("wtnhxymk"):
    if pwd[0:5] == "00000":
        the_pwd += pwd[5]
        print(the_pwd)
    if len(the_pwd) == 8:
        break
print(the_pwd)



