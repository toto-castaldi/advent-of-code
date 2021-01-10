import re
import os


file_name = "/input-test.txt"
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as file:
    properties = {}
    for line in file:
        fm = re.fullmatch(r"(.*): capacity (-?\d*). durability (-?\d*), flavor (-?\d*), texture (-?\d*), calories (-?\d*)", line.strip())
        name, capacity, durability, flavor, texture, calories = fm.groups()
        properties[name] = {"capacity": int(capacity), "durability": int(durability), "flavor": int(flavor), "texture": int(texture), "calories": int(calories)}

    print(properties)

    for spoons in range(100):
        print(spoons)

