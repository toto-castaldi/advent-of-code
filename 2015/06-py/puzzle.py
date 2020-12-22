import os
import re


def main():
    # coding=utf8
    # the above tag defines encoding for this document and is for Python 2.x compatibility



    regex = r"(on|off|toggle) ([0-9]+,[0-9]+) through ([0-9]+,[0-9]+)"

    test_str = ("turn off 301,3 through 808,453\n"
                "turn on 351,678 through 951,908\n"
                "toggle 720,196 through 897,994")

    matches = re.finditer(regex, test_str, re.MULTILINE)

    for matchNum, match in enumerate(matches, start=1):

        print("Match {matchNum} was found at {start}-{end}: {match}".format(matchNum=matchNum, start=match.start(),
                                                                            end=match.end(), match=match.group()))

        for groupNum in range(0, len(match.groups())):
            groupNum = groupNum + 1

            print("Group {groupNum} found at {start}-{end}: {group}".format(groupNum=groupNum,
                                                                            start=match.start(groupNum),
                                                                            end=match.end(groupNum),
                                                                            group=match.group(groupNum)))

    # Note: for Python 2.7 compatibility, use ur"" to prefix the regex and u"" to prefix the test string and substitution.

    return
    with open("./input.txt" if len(os.path.dirname(__file__)) == 0 else os.path.dirname(__file__) + "/input.txt",
              'r') as file:
        for line in file:
            matches = re.findall(r"(on|off|toggle) ([0-9]+,[0-9]+)", line, re.MULTILINE)
            print(f"|{matches}|")





if __name__ == "__main__":
    main()