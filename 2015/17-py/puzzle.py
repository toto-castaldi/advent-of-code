import os
import itertools
import time

file_name = "/input.txt"
liters = 150
start = time.time()
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as file:
    containers = {}
    for i, e in enumerate((map(lambda x: int(x.strip()), file.readlines()))):
        containers[str(i)] = e
    print(containers)
    good_perms = set()
    for perm in itertools.permutations(containers.keys()):
        acc_liters = 0
        sub_perm = []
        for i in perm:
            acc_liters += containers[i]
            sub_perm.append(i)
            if acc_liters == liters:
                good_perms.add(tuple(e for e in sorted(sub_perm)))
    print(len(good_perms))
    print(f"{time.time() - start}")
