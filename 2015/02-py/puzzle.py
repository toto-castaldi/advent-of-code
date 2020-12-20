import os
from functools import reduce



def parse_gifts(text):
    pass

def total_of(gifts, f):
    pass

def paper(gift):
    pass

def ribbon(gift):
    pass

def main():
    file_path = "./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt"

    with open(file_path, 'r') as file:
        gift_description = file.read()

    gifts = parse_gifts(gift_description)

    tot_paper = total_of(gifts, paper)
    tot_ribbon = total_of(gifts, ribbon)

    print(tot_paper)
    print(tot_ribbon)

if __name__ == "__main__":
    main()