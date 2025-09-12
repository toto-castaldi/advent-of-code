# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Advent of Code solutions repository containing implementations in multiple languages:
- **JavaScript** (Node.js) - Files in format `YYYY/DD-js/`
- **Python** - Files in format `YYYY/DD-py/`
- **Kotlin** (Gradle) - Files in format `YYYY/DD/`
- **TypeScript** (Deno) - Files in format `YYYY/DD/`

## Running Solutions

### JavaScript (Node.js v14.15.1+)
```bash
# From solution folder
node index.js
# From root
node 2015/01-js/index.js
```

### Python (3.8.3+)
```bash
# From solution folder
python puzzle.py
# From root
python 2015/03-py/puzzle.py
```

### Kotlin (Java 17, Kotlin 1.7.21)
```bash
./gradlew run --args 2022/14
```

### TypeScript (Deno 2.4.3+)
```bash
deno run --allow-read 2023/01/202301a.ts
```

## Testing

### JavaScript
Tests use Jest framework. Test files follow pattern `*.test.js`
```bash
npm test  # If package.json exists in solution folder
```

### TypeScript
Tests use Deno's built-in test framework
```bash
deno test
```

## Project Structure

Each year's solutions are in directories named by year (e.g., `2015/`, `2022/`).
Within each year, solutions are organized by day and language:
- `DD-js/` - JavaScript solutions
- `DD-py/` - Python solutions
- `DD/` - Kotlin/TypeScript solutions (newer years)

Common files in each solution:
- `input.txt` - Puzzle input
- `puzzle.txt` - Problem description
- Test files for JS/TS solutions

Kotlin utilities are in `src/main/kotlin/com/toto_castaldi/common/algo/` including:
- BFS, Dijkstra, BackTrackProblem, Sudoku, Distance calculations

TypeScript utilities are in `src/main/typescript/utils.ts`