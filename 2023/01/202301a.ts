async function main(): Promise<void> {

  const currentDir = new URL('.', import.meta.url).pathname;
  const fileName = `${currentDir}input.txt`;

  try {
    const file = await Deno.open(fileName);
    const lines = file.readable
      .pipeThrough(new TextDecoderStream())
      .pipeThrough(new TransformStream({
        transform(chunk, controller) {
          controller.enqueue(chunk);
        }
      }));

    for await (const line of lines) {
        let sum = 0;
        if (line.trim()) {
            let first = undefined;
            let last = undefined;
            for (const char of line.trim()) {
                if ("0123456789".includes(char)) {
                    if (first === undefined) {
                        first = char;
                        break;
                    }
                }
            }
            for (const char of line.trim().split('').reverse().join('')) {
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
        console.log(sum);
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