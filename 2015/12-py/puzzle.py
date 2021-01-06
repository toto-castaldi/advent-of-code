import json
import os


def rec_json(j):
    acc = 0
    if isinstance(j, list):
        for el in j:
            acc += rec_json(el)
    elif isinstance(j, dict):
        for el in j.values():
            acc += rec_json(el)
    elif type(j) == int:
        acc += j
    else:
        print("skip", j)
    return acc


file_name = "/input.json"
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as json_file:
    data = json.load(json_file)
    print("step_1", rec_json(data))
