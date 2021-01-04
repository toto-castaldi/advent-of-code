square_len = 1
target = 312051
while square_len ** 2 < target:
    square_len += 2
print(square_len)
index_x_t = None
index_y_t = None
grid = [[0 for i in range(square_len)] for j in range(square_len)]
index_x = index_y = square_len - 1
c = square_len ** 2
for index in range(square_len):
    grid[index_y][index_x] = c
    if c == target:
        index_x_t = index_x
        index_y_t = index_y
    c -= 1
    index_x -= 1

index_x = 0
index_y -= 1
for index in range(square_len - 1):
    grid[index_y][index_x] = c
    if c == target:
        index_x_t = index_x
        index_y_t = index_y
    c -= 1
    index_y -= 1

index_x = 1
index_y = 0
for index in range(square_len - 1):
    grid[index_y][index_x] = c
    if c == target:
        index_x_t = index_x
        index_y_t = index_y
    c -= 1
    index_x += 1

index_x = square_len - 1
index_y = 1
for index in range(square_len - 2):
    grid[index_y][index_x] = c
    if c == target:
        index_x_t = index_x
        index_y_t = index_y
    c -= 1
    index_y += 1

print(index_x_t, index_y_t, abs(index_x_t - square_len // 2), abs(index_y_t - square_len // 2), abs(index_x_t - square_len // 2) + abs(index_y_t - square_len // 2))
