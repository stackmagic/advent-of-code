import {rsum} from "../../utils";

export function solve1(lines: string[]): number {
    const instr = parse(lines);
    return instr.map(i => startMachine(i))
        .reduce(rsum, 0);
}

function startMachine(inst: Instructions): number {

    let minNumOfPresses = Number.MAX_SAFE_INTEGER;
    const max = Math.pow(2, inst.buttons.length);
    for (let i = 1; i <= max; i++) {

        const buttonsToPress: boolean[] = [];
        let num = i;
        while (num > 0) {
            buttonsToPress.push((num & 0x01) === 1);
            num >>= 1;
        }
        while (buttonsToPress.length < inst.buttons.length) {
            buttonsToPress.push(false);
        }

        buttonsToPress.reverse();

        // -------------

        const lights = [...inst.lights.map(_ => false)];

        for (let k = 0; k < buttonsToPress.length; k++) {
            if (!buttonsToPress[k]) {
                continue;
            }

            const wiring = inst.buttons[k];
            wiring.forEach(w => lights[w] = !lights[w]);
        }

        // --------------

        const numOfPresses = buttonsToPress.filter(it => it).length
        if ('' + lights === '' + inst.lights && numOfPresses < minNumOfPresses) {
            minNumOfPresses = numOfPresses;
        }
    }

    return minNumOfPresses;
}

/*
 * max 13 buttons =
 * 8192 combinations in base2 (part1) - doable
 * 1'000'000'000'000 combinations in base10 - omg
 *
 * basically, each counter has N open spots
 *
 * overfilling means it's not a solution
 *
 * can we be clever in how we choose the next button to press?
 *
 * it's a packing problem / tetris with an irregular playing area and weird shapes
 * (on reddit everyone talks about some z3 lib and how this is a set of equations)
 *
 * idea for a loop:
 * - remove all buttons, that affect joltage, that already is at the desired level
 * - take the input joltage as starting point and count down to zero by pressing buttons
 * - when counting up, instead of always adding 1, only increment positions in the number, where there's still spots available
 * - how to stop at failures and try other numbers??? recursion??? hard stop at say 9x pressing each button?
 */
export function solve2(lines: string[]): number {
    const instr = parse(lines);
    return instr.map(i => findJoltage(i))
        .reduce((a, b) => a + b, 0);
}

function findJoltage(instr: Instructions): number {
    const pushableButtons = filterButtons(instr.buttons, instr.joltage);
    return 0;
}

function testButtons(): number {
    return 0;
}

/** if a button affects a joltage slot, that is 0, the button is invalid and cannot be pressed */
function filterButtons(buttons: number[][], joltage: number[]): number[][] {
    return buttons.filter(b => {
        return b.every(pos => joltage[pos] > 0)
    });
}

function parse(lines: string[]): Instructions[] {
    return lines.map(l => Instructions.fromLine(l));
}

class Instructions {
    constructor(
        public readonly lights: boolean[],
        public readonly buttons: number[][],
        public readonly joltage: number[],
    ) {
        buttons.forEach(b => {
            if (b.sort().reverse()[0] >= lights.length) {
                throw new Error('invalid input');
            }
        })
    }

    public static fromLine(line: string): Instructions {
        const parts = line.split(' ');
        const lights = [...parts[0].replaceAll(/[\[\]]/g, '')].map(c => c === '#');
        const buttons = parts.slice(1, parts.length - 1).map(b => b.replaceAll(/[()]/g, '').split(',').map(Number));
        const joltage = parts[parts.length - 1].replaceAll(/[{}]/g, '').split(',').map(Number);
        return new Instructions(lights, buttons, joltage);
    }
}
