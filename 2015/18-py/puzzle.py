import os


def neighbors(x, y, grid):
    if 0 <= y < len(grid):
        row = grid[y]
        if 0 <= x < len(row):
            return row[x]
    return '.'


def step(grid):
    result = []
    for y in range(len(grid)):
        result_row = []
        for x in range(len(grid[0])):
            count_on = 0
            count_on += 1 if neighbors(x - 1, y - 1, grid) == '#' else 0
            count_on += 1 if neighbors(x, y - 1, grid) == '#' else 0
            count_on += 1 if neighbors(x + 1, y - 1, grid) == '#' else 0
            count_on += 1 if neighbors(x - 1, y, grid) == '#' else 0
            count_on += 1 if neighbors(x + 1, y, grid) == '#' else 0
            count_on += 1 if neighbors(x - 1, y + 1, grid) == '#' else 0
            count_on += 1 if neighbors(x, y + 1, grid) == '#' else 0
            count_on += 1 if neighbors(x + 1, y + 1, grid) == '#' else 0
            if grid[y][x] == '#':
                if count_on == 2 or count_on == 3:
                    result_row.append('#')
                else:
                    result_row.append('.')
            else:
                if count_on == 3:
                    result_row.append('#')
                else:
                    result_row.append('.')
        result.append(result_row)
    return result


def main():
    file_name = "/input.txt"
    steps_count = 100
    lights_grid = []
    with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
              'r') as file:
        for line in file:
            line = line.strip()
            lights_grid.append([char for char in line])
        for s in range(steps_count):
            lights_grid = step(lights_grid)
            count_on = 0
            for row in lights_grid:
                print(row)
                count_on += row.count('#')
            print(count_on)


if __name__ == "__main__":
    main()