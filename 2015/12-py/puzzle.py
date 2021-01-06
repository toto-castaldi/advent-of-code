import json
import os


def rec_json(j):
    acc_1 = 0
    acc_2 = 0
    if isinstance(j, list):
        for el in j:
            acc_1 += rec_json(el)[0]
            acc_2 += rec_json(el)[1]
    elif isinstance(j, dict):
        has_red = "red" in j.values()
        for el in j.values():
            acc_1 += rec_json(el)[0]
            acc_2 += rec_json(el)[1] if not has_red else 0
    elif type(j) == int:
        acc_1 += j
        acc_2 += j
    return acc_1, acc_2


file_name = "/input.json"
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as json_file:
    data = json.load(json_file)
    print(rec_json(data))
