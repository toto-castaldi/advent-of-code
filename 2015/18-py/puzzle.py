import os


def main():
    def neighbors(x, y, grid):
        if 0 <= y < len(grid):
            row = grid[y]
            if 0 <= x < len(row):
                return row[x]
        return '.'

    def step(grid, border_stuck):
        result = []
        for y in range(len(grid)):
            result_row = []
            for x in range(len(grid[0])):
                if border_stuck and ((y == x == 0) or (y == 0 and x == len(grid[0]) - 1) or (y == len(grid) - 1 and x == 0) or (y == len(grid) - 1 and x == len(grid[0]) - 1)):
                    result_row.append(grid[y][x])
                else:
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

    file_name = "/input.txt"
    steps_count = 100
    lights_grid_step1 = []
    lights_grid_step2 = []
    with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
              'r') as file:
        for line in file:
            line = line.strip()
            lights_grid_step1.append([char for char in line])
            lights_grid_step2.append([char for char in line])
            lights_grid_step2[0][0] = '#'
            lights_grid_step2[len(lights_grid_step2) - 1][0] = '#'
            lights_grid_step2[0][len(lights_grid_step2[0]) - 1] = '#'
            lights_grid_step2[len(lights_grid_step2) - 1][len(lights_grid_step2[0]) - 1] = '#'
        for s in range(steps_count):
            lights_grid_step1 = step(lights_grid_step1, False)
            lights_grid_step2 = step(lights_grid_step2, True)
            count_on_step1 = 0
            count_on_step2 = 0
            for row in lights_grid_step1:
                count_on_step1 += row.count('#')
            for row in lights_grid_step2:
                #print(row)
                count_on_step2 += row.count('#')
        print("step1", count_on_step1)
        print("step2", count_on_step2) #839 is too high


if __name__ == "__main__":
    main()