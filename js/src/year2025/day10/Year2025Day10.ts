import {arrDiv, rsum, sortNumAsc} from "../../utils";

/*
 * I have been struggling a lot with this one. My first solution to part1 was ok
 * and used also a bitfield but too much indirection. After failing to find a
 * solution for part2 while refusing to just use z3 or whatever libs other
 * people used, I found this post [1]. This helped and I almost solved the
 * problem but still a few inputs were failing (returning Infinity, meaning it
 * found no solution), I decided to just rewrite from scratch and this is nicer,
 * decently readable and documented code and it works.
 *
 * [1] https://www.reddit.com/r/adventofcode/comments/1pk87hl/2025_day_10_part_2_bifurcate_your_way_to_victory/
 */

/**
 * For each input, Build a superbutton for each possible combination of buttons,
 * then check if this one button fulfills the on/off (odd/even) requirement. If
 * so, it's a candidate. From all the candidates, we take the one made from the
 * least number of individual buttons and that count is the solution for one input.
 */
export function solve1(lines: string[]): number {
    return parse(lines)
        .map(instr => {
            return createButtonCombinations(instr.buttons, instr.lights.length)
                .filter(entry => matchesEvenOdd(entry[0], instr.lights))
                .map(entry => entry[1].length)
                .sort(sortNumAsc)[0];
        })
        .reduce(rsum, 0);
}

function matchesEvenOdd(arr1: number[], arr2: number[]): boolean {
    // this check could also be something like (val1 + arr2[idx]) % 2 === 0 because
    // adding 2 even numbers results in an even number and adding 2 uneven numbers
    // also results in an even number - only adding an even and uneven number results
    // in an uneven number... wonder which is faster?
    return arr1.length === arr2.length
        && arr1.every((val1, idx) => (val1 % 2) === (arr2[idx] % 2))
}

/** generate each possible combination of buttons and return the merged super-button as well as the individual base-buttons */
function createButtonCombinations(buttons: Button [], nOfLights: number): ButtonCombo[] {
    const combinations: ButtonCombo[] = []

    // 5 buttons => 2^5 combinations => 32 combinations
    const max = Math.pow(2, buttons.length);
    for (let i = 0; i < max; i++) {
        let idx = 0;
        let num = i;
        const mergedButton: Button = new Array(nOfLights).fill(0);
        const individualButtonsUsed: Button[] = [];
        while (num > 0) {
            // if the bit for this number is 1, we merge the button at the
            // corresponding index into the mergedButton by incrementing every
            // index that is affected when pressing the button
            if ((num & 0x01) === 1) {
                const button = buttons[idx];
                button.forEach(w => mergedButton[w]++);
                individualButtonsUsed.push(button);
            }

            // setup next iteration:
            // increment index and shift bitmask to the right, the just processed
            // bit will be discarded and the empty position will be filled with 0
            num >>>= 1;
            idx++;
        }

        combinations.push([mergedButton, individualButtonsUsed]);
        // console.log(i + ' ==> ' + mergedButton + ' <== ' + individualButtonsUsed.map(b => '(' + b + ')').join(' '));
    }

    return combinations;
}

/**
 * According to the post linked on top, we need to take the current joltages,
 * run them through the part1 logic to make them even and check all the returned
 * options, subtract them each from the joltages, divide the joltages by 2, then
 * recurse, and return the used buttons plus twice what the subtree returned
 */
export function solve2(lines: string[]): number {
    return parse(lines)
        .map(instr => {
            const buttonCombinations = createButtonCombinations(instr.buttons, instr.lights.length)
            return countLeast(buttonCombinations, instr.joltage);
        })
        .reduce(rsum, 0);
}

function countLeast(buttonCombinations: ButtonCombo[], joltage: number[]): number {

    // if all joltages are even, we can divide right away
    // const allEven = joltage.every(j => j % 2 === 0);
    // if (allEven) {
    //     const halfJoltage = arrDiv(joltage, 2);
    //     return 2 * countLeast(buttonCombinations, halfJoltage);
    // }

    const target = joltage.map(j => j % 2);
    const combinationsToTry = buttonCombinations.filter(entry => matchesEvenOdd(entry[0], target))
        .sort((a, b) => a[1].length - b[1].length);

    // if there are no combinations able to flip all values to even, this is not a viable solution
    if (combinationsToTry.length === 0) {
        return Infinity;
    }

    // test every combination that will make all numbers even, use the smallest count
    return combinationsToTry
        .map(combo => {

            const mergedButton = combo[0];
            const newJoltage = joltage.map((j, idx) => j - mergedButton[idx])

            // if anything is negative, we went too far and this path is not a viable solution
            if (newJoltage.some(j => j < 0)) {
                return Infinity;
            }

            // if everything is 0, return the number of buttons
            const individualButtonsCount = combo[1].length;
            if (newJoltage.every(j => j === 0)) {
                return individualButtonsCount;
            }

            // all joltages are even so we can just divide by 2 and recurse
            const halfJoltage = arrDiv(newJoltage, 2);
            return individualButtonsCount + 2 * countLeast(buttonCombinations, halfJoltage);
        })
        .sort(sortNumAsc)[0];
}

function parse(lines: string[]): Instructions[] {
    return lines
        .filter(l => l.length)
        .map(l => Instructions.fromLine(l));
}

/** better code readability */
type Button = number[];

/**
 * ButtonCombo[0] = merged button
 * ButtonCombo[1] = individual buttons from which [0] was calculated
 */
type ButtonCombo = [Button, Button[]];

class Instructions {
    constructor(
        public readonly line: string,
        /** true means on (or odd when counting) */
        public readonly lights: number[],
        public readonly buttons: Button[],
        public readonly joltage: number[],
    ) {
    }

    public static fromLine(line: string): Instructions {
        const parts = line.split(' ');
        const lights = [...parts[0].replaceAll(/[\[\]]/g, '')].map(c => c === '#').map(Number);
        const buttons = parts.slice(1, parts.length - 1).map(b => b.replaceAll(/[()]/g, '').split(',').map(Number));
        const joltage = parts[parts.length - 1].replaceAll(/[{}]/g, '').split(',').map(Number);
        return new Instructions(line, lights, buttons, joltage);
    }
}
