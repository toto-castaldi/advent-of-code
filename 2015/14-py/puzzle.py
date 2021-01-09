import re
import os


file_name = "/input.txt"
limit = 2503
with open(f".{file_name}" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + file_name,
          'r') as file:
    reindeer = {}
    for line in file:
        fm = re.fullmatch(r"(.*) can fly (\d*) km\/s for (\d*) seconds, but then must rest for (\d*) seconds.", line.strip())
        name, vel, duration, rest = fm.groups()
        reindeer[name] = {"vel": int(vel), "duration": int(duration), "rest": int(rest), "counter": 0, "km": 0}

    points = {}
    for seconds in range(limit):
        kms = []
        for k, v in reindeer.items():
            vel = v["vel"]
            duration = v["duration"]
            rest = v["rest"]
            counter = v["counter"]
            km = v["km"]
            if counter < duration:
                km += vel
                counter += 1
            elif counter < duration + rest - 1:
                counter += 1
            else:
                counter = 0
            kms.append(km)
            reindeer[k] = {"vel": vel, "duration": duration, "rest": rest, "counter": counter, "km": km}

        winning = max(kms)
        for k, v in reindeer.items():
            if v["km"] == winning:
                points[k] = points.get(k, 0) + 1

        if seconds == limit - 1:
            print("step_1", max(kms))

    print("step_2", max(points.values()), points)

