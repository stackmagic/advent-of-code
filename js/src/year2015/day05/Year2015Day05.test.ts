import {hasDoublePair, hasSandwich, solveA, solveB} from "./Year2015Day05";
import {loadLines} from "../../utils";

describe('Year2015 Day05', () => {

    class Test {
        constructor(
            public readonly name: string,
            public readonly file: string,
            public readonly expected: number,
            public readonly solve: (lines: string[]) => number,
        ) {
        }
    }

    [
        new Test('example a', 'example-a.txt', 2, solveA),
        new Test('challenge a', 'data.txt', 255, solveA),
        new Test('example b', 'example-b.txt', 2, solveB),
        new Test('challenge b', 'data.txt', 55, solveB),
    ].forEach(test => {
        it(test.name, () => expect(test.solve(loadLines(test.file))).toBe(test.expected));
    });

    describe('hasDoublePair', () => {
        it('no', () => expect(hasDoublePair('aaa')).toBe(false));
        it('yes', () => expect(hasDoublePair('aaaa')).toBe(true));
        it('also yes', () => expect(hasDoublePair('aadaa')).toBe(true));
        it('yes also', () => expect(hasDoublePair('dasdaaxaay')).toBe(true));
    });

    describe('hasSandwich', () => {
        it('yes', () => expect(hasSandwich('aba')).toBe(true));
        it('also yes', () => expect(hasSandwich('aaa')).toBe(true));
        it('no', () => expect(hasSandwich('abc')).toBe(false));
    });
});
