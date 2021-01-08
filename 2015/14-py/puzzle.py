import re
import os


file_name = "/input.txt"
seconds = 2503
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as file:
    reindeer = {}
    for line in file:
        fm = re.fullmatch(r"(.*) can fly (\d*) km\/s for (\d*) seconds, but then must rest for (\d*) seconds.", line.strip())
        name, vel, duration, rest = fm.groups()
        reindeer[name] = {"vel": int(vel), "duration": int(duration), "rest": int(rest)}

    kms = []
    for k, v in reindeer.items():
        vel = v["vel"]
        duration = v["duration"]
        rest = v["rest"]
        module = duration + rest
        steps = seconds // module
        delta = seconds - (steps * module)
        km = steps * vel * duration + (vel * duration if delta > duration else int(vel * delta / duration))
        print(k, v, steps, delta, km)
        kms.append(km)

    print(max(kms))

