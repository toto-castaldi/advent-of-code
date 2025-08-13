import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

async function main(bagConfig : any): Promise<number> {
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



if (import.meta.main) {
  try {
    const result = await main({
      red : 12,
      green : 13,
      blue : 14,
    });
    console.log(`Possible games sum: ${result}`);
  } catch (error) {
    console.error("💥", error);
  }
}

export function possibleGame(bagConfig: any, line: string): boolean {
  let result = true;
  line.split(':')[1].split(';').forEach((set) => {
    set.trim().split(',').forEach((part) => {
      const [count, colorName] = part.trim().split(' ');
      const maxOfColor = bagConfig[colorName.toLocaleLowerCase().trim()]
      if (maxOfColor !== undefined && parseInt(count.trim()) > maxOfColor) result = false;
    });
  });
  return result;
}

function gameId(line: string) : number {
  return parseInt(line.split(':')[0].split(' ')[1].trim());
}
