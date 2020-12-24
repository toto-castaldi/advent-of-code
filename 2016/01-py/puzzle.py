import os


def main():
    with open("./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt",
              'r') as file:
        directions = ['N', 'O', 'S', 'E']
        x = 0
        y = 0
        dirindex = 0
        for line in file:
            steps = line.split(", ")
            for step in steps:
                if step[0] == 'L':
                    dirindex += 1
                if step[0] == 'R':
                    dirindex -= 1

                dirindex = dirindex % 4
                step_count = int(step[1:])
                if directions[dirindex] == 'N':
                    y += step_count
                if directions[dirindex] == 'S':
                    y -= step_count
                if directions[dirindex] == 'E':
                    x += step_count
                if directions[dirindex] == 'O':
                    x -= step_count

                print(step, directions[dirindex], x, y)


if __name__ == "__main__":
    main()