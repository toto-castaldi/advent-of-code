import os
import re
import itertools


def main():
    file_name = "/input.txt"
    with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
              'r') as file:
        cities = []
        distances = {}
        for line in file:
            line = line.strip()
            city_from, city_to, distance_str = re.fullmatch(r"(\w*) to (\w*) = (\d*)", line).groups()
            if city_from not in cities:
                cities.append(city_from)
            if city_to not in cities:
                cities.append(city_to)
            distances[(city_from, city_to)] = int(distance_str)
            distances[(city_to, city_from)] = int(distance_str)
        print(cities)
        print(distances)
        permutations = itertools.permutations(cities)
        min_perm = None
        max_perm = None
        min_perm_val = 0
        max_perm_val = 0
        for perm in permutations:
            total = 0
            for i in (range(len(perm) - 1)):
                total += distances[(perm[i], perm[(i + 1)])]
            if (total < min_perm_val) or (min_perm_val == 0):
                min_perm = perm
                min_perm_val = total
            if (total > max_perm_val) or (max_perm_val == 0):
                max_perm = perm
                max_perm_val = total
        print(min_perm, min_perm_val)
        print(max_perm, max_perm_val)


if __name__ == "__main__":
    main()