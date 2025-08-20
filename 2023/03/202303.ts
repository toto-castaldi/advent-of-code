import { findNumbers, containsOnlyDotsOrNumbers, readInputLines, findItemInString, isDigit, extractNumberFromString } from '../../src/main/typescript/utils.ts';

export class EngineSchema {
  
  private lineBuffer0: string;
  private lineBuffer1: string;
  private lineBuffer2: string;

  private sumOfParts: number;
  private sumOfGears: number;

  private lineLenght: number;

  private gears : [number, number][];

  constructor() {
    this.lineBuffer0 = "";
    this.lineBuffer1 = "";
    this.lineBuffer2 = "";
    this.sumOfParts = 0;
    this.lineLenght = 0;
    this.sumOfGears = 0;
    this.gears = [];
  }

  public getSumOfParts() : number {
    return this.sumOfParts;
  }

    public addLine(line: string) : void {
        if (this.lineBuffer0.length === 0) {
            this.lineLenght = line.length;
            this.lineBuffer0 = ".".repeat(this.lineLenght);
            this.lineBuffer1 = line;
        } else if (this.lineBuffer2.length === 0) {
            this.lineBuffer2 = line;
        } else {
            this.lineBuffer0 = this.lineBuffer1;
            this.lineBuffer1 = this.lineBuffer2;
            this.lineBuffer2 = line;  
        }

        if (this.lineBuffer2.length > 0) {
            this.computeOnBuffer();
        }
    }

    public endLines() : void {
        this.addLine(".".repeat(this.lineLenght));
    }

    private computeOnBuffer() : void {
        const numbers = findNumbers(this.lineBuffer1);
        for (const numbInfo of numbers) {
            const aroundSymbols = [];
            const valueStr = numbInfo.value.toString();

            if (numbInfo.position > 0) {
                aroundSymbols.push(this.lineBuffer0.charAt(numbInfo.position - 1));
                aroundSymbols.push(this.lineBuffer1.charAt(numbInfo.position - 1));
                aroundSymbols.push(this.lineBuffer2.charAt(numbInfo.position - 1));
            }
            if (numbInfo.position + valueStr.length < this.lineBuffer1.length) {
                aroundSymbols.push(this.lineBuffer0.charAt(numbInfo.position + valueStr.length));
                aroundSymbols.push(this.lineBuffer1.charAt(numbInfo.position + valueStr.length));
                aroundSymbols.push(this.lineBuffer2.charAt(numbInfo.position + valueStr.length));
            }
            for (let pos = 0; pos < valueStr.length; pos ++) {
                aroundSymbols.push(this.lineBuffer0.charAt(numbInfo.position + pos));
                aroundSymbols.push(this.lineBuffer2.charAt(numbInfo.position + pos));
            }
            if (!containsOnlyDotsOrNumbers(aroundSymbols)) {
                this.sumOfParts += numbInfo.value;
            }
            
        }

        const asteriskInString = findItemInString("*", this.lineBuffer1);
        for (const asteriskPos of asteriskInString){
            const numbers : (number | null)[] = [];
            if (asteriskPos > 0 && isDigit(this.lineBuffer0.charAt(asteriskPos - 1))) {
                numbers.push(extractNumberFromString(this.lineBuffer0, asteriskPos - 1));
            } 
            if (asteriskPos < this.lineBuffer0.length && isDigit(this.lineBuffer0.charAt(asteriskPos + 1))) {
                numbers.push(extractNumberFromString(this.lineBuffer0, asteriskPos + 1));
            }
            if (isDigit(this.lineBuffer0.charAt(asteriskPos))) {
                numbers.push(extractNumberFromString(this.lineBuffer0, asteriskPos));
            }
            if (asteriskPos > 0 && isDigit(this.lineBuffer1.charAt(asteriskPos - 1))) {
                numbers.push(extractNumberFromString(this.lineBuffer1, asteriskPos - 1));
            }
            if (asteriskPos < this.lineBuffer1.length && isDigit(this.lineBuffer1.charAt(asteriskPos + 1))) {
                numbers.push(extractNumberFromString(this.lineBuffer1, asteriskPos + 1));
            }
            if (asteriskPos > 0 && isDigit(this.lineBuffer2.charAt(asteriskPos - 1))) {
                numbers.push(extractNumberFromString(this.lineBuffer2, asteriskPos - 1));
            }
            if (asteriskPos < this.lineBuffer2.length && isDigit(this.lineBuffer2.charAt(asteriskPos + 1))) {
                numbers.push(extractNumberFromString(this.lineBuffer2, asteriskPos + 1));
            }
            if (isDigit(this.lineBuffer2.charAt(asteriskPos))) {
                numbers.push(extractNumberFromString(this.lineBuffer2, asteriskPos));
            }

            const uniques = [...new Set(numbers)];

            if (uniques.length == 2 && !(uniques.includes(null))) {
                const ratio = uniques[0]! * uniques[1]!;
                console.log(uniques[0], uniques[1], ratio);
                this.sumOfGears += ratio;
                this.gears.push([uniques[0]!, uniques[1]!]);
            } else {

            }
        }
        
    }

    public getSumOfGears(): number {
        return this.sumOfGears;
    }

    public getGears() : [number, number] [] {
        return this.gears;
    }
}

if (import.meta.main) {
  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const engineSchema = new EngineSchema();
    for await (const line of readInputLines(`${currentDir}input.txt`)) {
        engineSchema.addLine(line);
    }
    engineSchema.endLines();
    
    let result = engineSchema.getSumOfParts(); 
    console.log(`Step 1: ${result}`);

    result = engineSchema.getSumOfGears();
    console.log(`Step 2: ${result}`);
    
  } catch (error) {
    console.error("💥", error);
  }
}



