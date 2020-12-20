import os


def main():
    file_path = "./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt"

    with open(file_path, 'r') as file:
        path = file.read()

    _path = ">"
    _path = "^>v<"
    _path = "^v^v^v^v^v"

    rule = {
        '^': [0, 1],
        'v': [0, -1],
        '>': [1, 0],
        '<': [-1, 0]
    }

    houses = {
        '0': {
            '0': 1
        }
    }
    
    position_santa = [0, 0]
    for movement in path:
        position_santa[0] += rule[movement][0]
        position_santa[1] += rule[movement][1]
        x_key = str(position_santa[0])
        y_key = str(position_santa[1])
        x = houses.get(x_key, {})
        gift_count = x.get(y_key, 0)
        x[str(y_key)] = gift_count + 1
        houses[x_key] = x

    acc = 0
    for _, x in houses.items():
        for _, y in x.items():
            acc += 1

    print(acc)

    position_santa = [0, 0]
    position_robot = [0, 0]
    houses = {
        '0': {
            '0': 2
        }
    }

    santa_turn = True
    for movement in path:
        if santa_turn:
            position_santa[0] += rule[movement][0]
            position_santa[1] += rule[movement][1]
            x_key = str(position_santa[0])
            y_key = str(position_santa[1])
        else:
            position_robot[0] += rule[movement][0]
            position_robot[1] += rule[movement][1]
            x_key = str(position_robot[0])
            y_key = str(position_robot[1])

        santa_turn = not santa_turn

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