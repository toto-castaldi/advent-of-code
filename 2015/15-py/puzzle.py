import re
import os


file_name = "/input.txt"
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as file:
    properties = {}
    for line in file:
        fm = re.fullmatch(r"(.*): capacity (-?\d*). durability (-?\d*), flavor (-?\d*), texture (-?\d*), calories (-?\d*)", line.strip())
        name, capacity, durability, flavor, texture, calories = fm.groups()
        properties[name] = {"capacity": int(capacity), "durability": int(durability), "flavor": int(flavor), "texture": int(texture), "calories": int(calories)}

    def hs(max_spoons, prev_tail):
        if len(prev_tail) > 1:
            result = []
            _, *new_tail = prev_tail
            for head_spoons in range(1, max_spoons - len(prev_tail)):
                right = hs(max_spoons - head_spoons, new_tail)
                for r in right:
                    p = [head_spoons]
                    if type(r) == list:
                        p.extend(r)
                    else:
                        p.append(r)
                    result.append(p)
            return result
        else:
            return [max_spoons]

    ingredients = list(properties.keys())
    permutations = hs(100, ingredients)
    max_score = 0
    the_max_p = None
    for p in permutations:
        capacity = 0
        durability = 0
        flavor = 0
        texture = 0
        for index, spoons in enumerate(p):
            capacity += spoons * properties[ingredients[index]]["capacity"]
            durability += spoons * properties[ingredients[index]]["durability"]
            flavor += spoons * properties[ingredients[index]]["flavor"]
            texture += spoons * properties[ingredients[index]]["texture"]
        capacity = 0 if capacity < 0 else capacity
        durability = 0 if durability < 0 else durability
        flavor = 0 if flavor < 0 else flavor
        texture = 0 if texture < 0 else texture
        if capacity * durability * flavor * texture > max_score:
            the_max_p = p
            max_score = capacity * durability * flavor * texture

    print(the_max_p, max_score)


