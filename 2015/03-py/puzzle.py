import os


def main():
    _path = ">"
    _path = "^>v<"
    _path = "^v^v^v^v^v"

    file_path = "./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt"

    with open(file_path, 'r') as file:
        path = file.read()

    houses = {
        '0': {
            '0': 1
        }
    }
    position = [0, 0]
    rule = {
        '^': [0, 1],
        'v': [0, -1],
        '>': [1, 0],
        '<': [-1, 0]
    }
    for movement in path:
        position[0] += rule[movement][0]
        position[1] += rule[movement][1]
        x_key = str(position[0])
        y_key = str(position[1])
        x = houses.get(x_key, {})
        gift_count = x.get(y_key, 0)
        x[str(y_key)] = gift_count + 1
        houses[x_key] = x

    acc = 0
    for _, x in houses.items():
        for _, y in x.items():
            acc += 1

    print(acc)


if __name__ == "__main__":
    main()