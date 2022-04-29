package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
)

func main() {
	var lastVar int = -1
	var countIncrease int = 0

	file1, _ := os.Open("input-1.txt")
	defer file1.Close()

	scanner1 := bufio.NewScanner(file1)

	for scanner1.Scan() {
		intVar, _ := strconv.Atoi(scanner1.Text())
		if lastVar > -1 {
			if lastVar < intVar {
				countIncrease++
			}
		}
		lastVar = intVar
	}

	fmt.Println("Result1", countIncrease)

	file2, _ := os.Open("input-2.txt")
	defer file2.Close()
	var w1 int = 0
	var w2 int = 0
	var w3 int = 0
	var w4 int = 0
	var index int = 0
	countIncrease = 0

	scanner2 := bufio.NewScanner(file2)

	for scanner2.Scan() {
		intVar, _ := strconv.Atoi(scanner2.Text())
		var mod4 int = int(math.Mod(float64(index), 4))

		if mod4 != 3 {
			w1 += intVar
		}

		if mod4 != 0 && w1 > 0 {
			w2 += intVar
		}

		if mod4 != 1 && w2 > 0 {
			w3 += intVar
		}

		if mod4 != 2 && w3 > 0 {
			w4 += intVar
		}

		if mod4 == 3 {
			if w2 > w1 && w2 != 0 && w1 != 0 {
				countIncrease++
			}
			w1 = 0
		}

		if mod4 == 0 {
			if w3 > w2 && w3 != 0 && w2 != 0 {
				countIncrease++
			}
			w2 = 0
		}

		if mod4 == 1 {
			if w4 > w3 && w4 != 0 && w3 != 0 {
				countIncrease++
			}
			w3 = 0
		}

		if mod4 == 2 {
			if w1 > w4 && w1 != 0 && w4 != 0 {
				countIncrease++
			}
			w4 = 0
		}

		fmt.Println(mod4, intVar, countIncrease, w1, w2, w3, w4)

		index++
	}

	fmt.Println("Result2", countIncrease)

}
