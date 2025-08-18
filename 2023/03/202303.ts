import { findNumbers } from '../../src/main/typescript/utils.ts';
import { containsOnlyDotsOrNumbers } from '../../src/main/typescript/utils.ts';
import { readInputLines } from '../../src/main/typescript/utils.ts';


export class EngineSchema {
  private lineBuffer0: string;
  private lineBuffer1: string;
  private lineBuffer2: string;

  private sumOfParts: number;

  private lineLenght: number;

  constructor() {
    this.lineBuffer0 = "";
    this.lineBuffer1 = "";
    this.lineBuffer2 = "";
    this.sumOfParts = 0;
    this.lineLenght = 0;
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
                //console.log(numbInfo.value);
            } else {
                console.log(numbInfo.position, numbInfo.value, aroundSymbols);
            }
            
        }
        
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
    
    const result = engineSchema.getSumOfParts(); 
    console.log(`Step 1: ${result}`);
    
  } catch (error) {
    console.error("💥", error);
  }
}



