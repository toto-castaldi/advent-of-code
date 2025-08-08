import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

async function main(): Promise<void> {

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
        const trimmedLine : string = line.trim();
        let first = undefined;
        let last = undefined;
        for (const char of trimmedLine) {
            if ("0123456789".includes(char)) {
                if (first === undefined) {
                    first = char;
                    break;
                }
            }
        }
        for (const char of trimmedLine.split('').reverse().join('')) {
            if ("0123456789".includes(char)) {
                if (last === undefined) {
                    last = char;
                    break
                }
            }
        }
        const num = (first !== undefined ? first : '') + (last !== undefined ? last : '')
        console.log(`Processing line: ${line.trim()} => first: ${first}, last: ${last}, num: ${num}`);
        sum += parseInt(num);
      }
      console.log(`Sum of all numbers: ${sum}`);
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
    await main();
  } catch (error) {
    console.error("💥", error);
  }
}