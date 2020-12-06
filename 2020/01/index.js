const { exec } = require('child_process');
const fs = require('fs');

const find = (expenses, sum) => {
    let min = 0;
    let max = expenses.length - 1;
    while (min < max && expenses[min] + expenses[max] !== sum) {
        if (expenses[min] + expenses[max] > sum) {
            max--;
        }
        if (expenses[min] + expenses[max] < sum) {
            min++;
        }
    }
    if (min < max) {
        return { a: expenses[min], b: expenses[max] };
    }
}

fs.readFile('./input.txt', 'utf8', function (err, data) {
    if (err) {
        return console.log(err);
    }

    let lines = data.split('\n');
    let expenses = [];

    for (line of lines) {
        if (line.trim().length !== 0) {
            expenses.push(parseInt(line.trim()));
        }
    }

    expenses.sort((a, b) => a - b);

    const two = find(expenses, 2020);

    console.log(`${two.a} + ${two.b} = ${two.a + two.b},`, `${two.a} * ${two.b} = ${two.a * two.b}`);

    for (let i = expenses.length - 1; i >= 0; i++) {
        const first = expenses.pop();
        const second = find(expenses, 2020 - first);
        if (second) {
            console.log(first, second, first + second.a + second.b, first * second.a * second.b);
            return;
        }
    }
});