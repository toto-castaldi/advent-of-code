_input_text = "1"
input_text = "1113222113"
for i in range(50):
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
    if i == 39:
        print("step_1", len(input_text))
print("step_2", len(input_text))
