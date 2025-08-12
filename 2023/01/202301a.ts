import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

async function main(spelledNumbers : boolean): Promise<number> {

  const currentDir = new URL('.', import.meta.url).pathname;
  const fileName = `${currentDir}input.txt`;

  try {
    let sum : number = 0;
    const file = await Deno.open(fileName, { read: true });
    try {
      const lines : ReadableStream<string> = file
        .readable
        .pipeThrough(new TextDecoderStream())
        .pipeThrough(new TextLineStream());
      for await (const line of lines) {
        sum += extractNum(spelledNumbers, line.trim().toLocaleLowerCase());
      }
      return sum;
    } catch (error) {
      file.close();
      throw error;
    }
  } catch (error) {
    console.error('Error reading file:', error);
    throw error;
  }


  
}

if (import.meta.main) {
  try {
    let result = await main(false);
    console.log(`Sum of all numbers: ${result}`);
    result = await main(true);
    console.log(`Sum of all numbers: ${result}`);
  } catch (error) {
    console.error("💥", error);
  }
}

export function extractNum(spelledNumbers: boolean, trimmedLine: string) : number{
  const line : string[] = trimmedLine.split('');
  if (spelledNumbers) {
    const found: Record<string, number[]> = {
      zero: [],
      one: [],
      two: [],
      three: [],
      four: [],
      five: [],
      six: [],
      seven: [],
      eight: [],
      nine: []
    };
    const spelledVal: Record<string, string> = {
      zero: '0',
      one: '1',
      two: '2',
      three: '3',
      four: '4',
      five: '5',
      six: '6',
      seven: '7',
      eight: '8',
      nine: '9'
    };
    for (const spelledNum of Object.keys(spelledVal)) {
      for (let index = 0; index < line.length; index++) {
        if (line.length >= index + spelledNum.length && line.slice(index, index + spelledNum.length).join('') === spelledNum) {
          found[spelledNum].push(index);
        }
      }
    }
    for (const spelledNum in found) {
      for (const index of found[spelledNum]) {
        line[index] = spelledVal[spelledNum]
      }
    }
  }
  let first = undefined;
  let last = undefined;
  for (const char of line) {
    if ("0123456789".includes(char)) {
      if (first === undefined) {
        first = char;
        break;
      }
    }
  }
  for (const char of line.reverse().join('')) {
    if ("0123456789".includes(char)) {
      if (last === undefined) {
        last = char;
        break;
      }
    }
  }
  return parseInt((first !== undefined ? first : '') + (last !== undefined ? last : ''));
}
