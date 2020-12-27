import os


def fuel_of(mass):
    return int(mass / 3) - 2


def main():
    input_file = "/input.txt"
    with open(f".{input_file}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + input_file,
              'r') as file:
        modules = list(map(lambda x: int(x.strip()), file.readlines()))
        total_step1_fuel = 0
        total_step2_fuel = 0
        for module in modules:
            fuel = fuel_of(module)
            total_step1_fuel += fuel
            while fuel > 0:
                total_step2_fuel += fuel
                fuel = fuel_of(fuel)

        print("step1",  total_step1_fuel)
        print("step2", total_step2_fuel)


if __name__ == "__main__":
    main()