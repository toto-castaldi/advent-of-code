import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

// input = "467..*..114.."
// Output: [
//   { value: '467', position: 0 },
//   { value: '114', position: 8 }
// ]
export function findNumbers(str: string): Array<{value: number, position: number}> {
  const regex = /\d+/g;
  const matches = str.matchAll(regex);
  const results = [];
  
  for (const match of matches) {
    results.push({
      value: parseInt(match[0]),
      position: match.index!
    });
  }
  
  return results;
}

export function isDigit(char: string): boolean {
  return /[0-9]/.test(char);
}

export function containsOnlyDotsOrNumbers(arr: string[]): boolean {
  return arr.every(item => /^[\d.]+$/.test(item));
}

export async function* readInputLines(fileName : string): AsyncGenerator<string> {
  
  const file = await Deno.open(fileName, { read: true });
  
  const lines = file
    .readable
    .pipeThrough(new TextDecoderStream())
    .pipeThrough(new TextLineStream());
  
  for await (const line of lines) {
    yield line;
  }
}

export function findItemInString(item: string, str : string): number[] {
    const positions: number[] = [];
    let index = str.indexOf(item);
    
    while (index !== -1) {
        positions.push(index);
        index = str.indexOf(item, index + 1);
    }
    
    return positions;
}

export function extractNumberFromString(str: string, position: number): number | null {
    if (position < 0 || position >= str.length) {
        return null;
    }
    
    if (!/\d/.test(str[position])) {
        return null;
    }
    
    let start = position;
    while (start > 0 && /\d/.test(str[start - 1])) {
        start--;
    }
    
    let end = position;
    while (end < str.length - 1 && /\d/.test(str[end + 1])) {
        end++;
    }
    
    const numberStr = str.substring(start, end + 1);
    
    return parseInt(numberStr, 10);
}