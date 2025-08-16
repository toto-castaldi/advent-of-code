import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

type BagConfig = {
  red: number;
  green: number;
  blue: number;
}

type GameSet = {
  count: number;
  color: keyof BagConfig;
}

async function* readInputLines(): AsyncGenerator<string> {
  const currentDir = new URL('.', import.meta.url).pathname;
  const fileName = `${currentDir}input.txt`;
  
  const file = await Deno.open(fileName, { read: true });
  
  const lines = file
    .readable
    .pipeThrough(new TextDecoderStream())
    .pipeThrough(new TextLineStream());
  
  for await (const line of lines) {
    yield line;
  }
}

async function step1(bagConfig: BagConfig): Promise<number> {
  let sum = 0;
  
  for await (const line of readInputLines()) {
    if (isGamePossible(bagConfig, line)) {
      sum += extractGameId(line);
    }
  }
  
  return sum;
}

async function step2(): Promise<number> {
  let sum = 0;
  
  for await (const line of readInputLines()) {
    sum += calculateGamePower(line);
  }
  
  return sum;
}



if (import.meta.main) {
  try {
    const bagConfig: BagConfig = {
      red: 12,
      green: 13,
      blue: 14,
    };
    
    const possibleGamesSum = await step1(bagConfig);
    console.log(`Possible games sum: ${possibleGamesSum}`);
    
    const powerSum = await step2();
    console.log(`Sum of games power: ${powerSum}`);
  } catch (error) {
    console.error("💥", error);
  }
}

function parseGameSets(gameData: string): GameSet[] {
  const sets: GameSet[] = [];
  const gameParts = gameData.split(':')[1];
  
  if (!gameParts) {
    throw new Error('Invalid game format');
  }
  
  for (const setString of gameParts.split(';')) {
    for (const cubeInfo of setString.trim().split(',')) {
      const [countStr, colorName] = cubeInfo.trim().split(' ');
      const color = colorName?.toLowerCase().trim() as keyof BagConfig;
      
      if (!countStr || !color || !(color in { red: 0, green: 0, blue: 0 })) {
        throw new Error(`Invalid game data: ${cubeInfo}`);
      }
      
      sets.push({
        count: parseInt(countStr, 10),
        color
      });
    }
  }
  
  return sets;
}

export function calculateGamePower(line: string): number {
  const minCubes: BagConfig = {
    red: 0,
    green: 0,
    blue: 0,
  };
  
  const gameSets = parseGameSets(line);
  
  for (const { count, color } of gameSets) {
    minCubes[color] = Math.max(minCubes[color], count);
  }
  
  return Object.values(minCubes).reduce((power, count) => power * count, 1);
}

export function isGamePossible(bagConfig: BagConfig, line: string): boolean {
  const gameSets = parseGameSets(line);
  
  return gameSets.every(({ count, color }) => count <= bagConfig[color]);
}

function extractGameId(line: string): number {
  const match = line.match(/^Game (\d+):/);
  
  if (!match || !match[1]) {
    throw new Error(`Invalid game format: ${line}`);
  }
  
  return parseInt(match[1], 10);
}
