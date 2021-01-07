import os
import re
import itertools


file_name = "/input.txt"
people = set()
weights = {}
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as input_file:
    for line in input_file:
        findall = re.findall(r"(\w*) would (gain|lose) (\d*) happiness units by sitting next to (\w*)", line.strip())
        person_from, lose_gain, amount, person_to = findall[0]
        people.add(person_from)
        people.add(person_to)
        person_weights = weights.get(person_from, {})
        person_weights[person_to] = int(amount) if lose_gain == "gain" else int(amount) * -1
        weights[person_from] = person_weights
    print(people)
    print(weights)
    permutations = itertools.permutations(people)
    max_perm_value = 0
    for perm in permutations:
        acc = 0
        for i in range(len(perm) - 1):
            acc += weights[perm[i]][perm[i + 1]]
            acc += weights[perm[i + 1]][perm[i]]
        acc += weights[perm[len(perm) - 1]][perm[0]]
        acc += weights[perm[0]][perm[len(perm) - 1]]
        max_perm_value = acc if acc > max_perm_value else max_perm_value
    print(max_perm_value)