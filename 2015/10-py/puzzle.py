def main():
    _input_text = "1"
    input_text = "1113222113"
    for i in range(40):
        index = 0
        new_input = ""
        while index < len(input_text):
            count = 1
            while (index < len(input_text) - 1) and (input_text[index] == input_text[index + 1]):
                index += 1
                count += 1
            new_input += str(count) + input_text[index]
            index += 1
        input_text = new_input
        print(len(input_text))


if __name__ == "__main__":
    main()