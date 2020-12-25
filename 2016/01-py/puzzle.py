import os


def main():
    input_file = "/input.txt"
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        directions = ['N', 'O', 'S', 'E']
        x = 0
        y = 0
        dirindex = 0
        locations = []
        loc_twice = None
        for line in file:
            steps = line.split(", ")
            for step in steps:
                if step[0] == 'L':
                    dirindex += 1
                if step[0] == 'R':
                    dirindex -= 1

                dirindex = dirindex % 4
                step_count = int(step[1:])
                for i in range(step_count):
                    if directions[dirindex] == 'N':
                        y += 1
                    if directions[dirindex] == 'S':
                        y -= 1
                    if directions[dirindex] == 'E':
                        x += 1
                    if directions[dirindex] == 'O':
                        x -= 1

                    print(step, directions[dirindex], x, y)

                    loc_twice  = (x, y) if (x, y) in locations and loc_twice is None else loc_twice
                    locations.append((x, y))

        print("first",  abs(x) + abs(y), x, y)
        print("second", abs(loc_twice[0]) + abs(loc_twice[1]), loc_twice)


if __name__ == "__main__":
    main()