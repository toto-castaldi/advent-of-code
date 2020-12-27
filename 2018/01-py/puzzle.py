import os


def main():
    input_file = "/input.txt"
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        changes = list(map(lambda x: int(x.strip()), file.readlines()))
        frequency = 0
        first = None
        frequency_twice = None
        frequencies = {}
        while frequency_twice is None:
            for change in changes:
                if frequencies.get(frequency, None) is None:
                    frequencies[frequency] = 1
                else:
                    frequency_twice = frequency if frequency_twice is None else frequency_twice
                frequency += change
            first = frequency if first is None else first

        print("first",  first)
        print("second", frequency_twice)


if __name__ == "__main__":
    main()