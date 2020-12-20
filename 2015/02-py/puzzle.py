import os
from functools import reduce
import math


def total_of(gifts, f):
    return reduce((lambda p, n: p + f(n)), gifts, 0)


def paper(g):
    min_surface = ([g[0], g[1]] if g[0] * g[1] < g[1] * g[2] else [g[1], g[2]]) if g[0] * g[1] < g[0] * g[2] else ([g[0], g[2]] if g[0] * g[2] < g[1] * g[2] else [g[1], g[2]])
    return 2 * g[0] * g[1] + 2 * g[0] * g[2] + 2 * g[1] * g[2] + min_surface[0] * min_surface[1]


def ribbon(g):
    g_sorted = sorted(g)
    return g_sorted[0] * 2 + g_sorted[1] * 2 + math.prod(g)


def main():
    file_path = "./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt"

    with open(file_path, 'r') as file:
        gifts = [[int(dim_str) for dim_str in gift.split('x')] for gift in [line.rstrip() for line in file.readlines()]]

    tot_paper = total_of(gifts, paper)
    tot_ribbon = total_of(gifts, ribbon)

    print(tot_paper)
    print(tot_ribbon)


if __name__ == "__main__":
    main()