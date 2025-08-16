import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

type BagConfig = {
  red: number;
  green: number;
  blue: number;
}

async function step1(bagConfig : BagConfig): Promise<number> {
  const currentDir = new URL('.', import.meta.url).pathname;
  const fileName = `${currentDir}input.txt`;
  
  const file = await Deno.open(fileName, { read: true });
  
  const lines = file
    .readable
    .pipeThrough(new TextDecoderStream())
    .pipeThrough(new TextLineStream());
  
  let sum = 0;
  for await (const line of lines) {
    if (possibleGame(bagConfig, line)) {
      sum += gameId(line);
    }    
  }
  
  return sum;
}

async function step2(): Promise<number> {
  const currentDir = new URL('.', import.meta.url).pathname;
  const fileName = `${currentDir}input.txt`;
  
  const file = await Deno.open(fileName, { read: true });
  
  const lines = file
    .readable
    .pipeThrough(new TextDecoderStream())
    .pipeThrough(new TextLineStream());
  
  let sum = 0;
  for await (const line of lines) {
    sum += gamePower(line);   
  }
  
  return sum;
}



if (import.meta.main) {
  try {
    let result = await step1({
      red : 12,
      green : 13,
      blue : 14,
    });
    console.log(`Possible games sum: ${result}`);
    result = await step2();
    console.log(`Sum o games power : ${result}`);
  } catch (error) {
    console.error("💥", error);
  }
}

export function gamePower(line: string) : number {
  const result : BagConfig = {
      red : 0,
      green : 0,
      blue : 0,
  };
  line.split(':')[1].split(';').forEach((set) => {
    set.trim().split(',').forEach((part) => {
      const [countStr, colorName] = part.trim().split(' ');
      const count = parseInt(countStr);
      if (result[colorName.toLocaleLowerCase().trim() as keyof BagConfig] === undefined) {
        throw new Error(`invalid key ${colorName}`);
      }
      if (result[colorName.toLocaleLowerCase().trim() as keyof BagConfig] < count) {
        result[colorName.toLocaleLowerCase().trim() as keyof BagConfig] = count;
      }
    });
  });

  let power = 1;
  Object.values(result).forEach(value => {
    power = power * value;
  });

  return power;
}

export function possibleGame(bagConfig: BagConfig, line: string): boolean {
  let result = true;
  line.split(':')[1].split(';').forEach((set) => {
    set.trim().split(',').forEach((part) => {
      const [count, colorName] = part.trim().split(' ');
      const maxOfColor = bagConfig[colorName.toLocaleLowerCase().trim() as keyof BagConfig]
      if (maxOfColor !== undefined && parseInt(count.trim()) > maxOfColor) result = false;
    });
  });
  return result;
}

function gameId(line: string) : number {
  return parseInt(line.split(':')[0].split(' ')[1].trim());
}
