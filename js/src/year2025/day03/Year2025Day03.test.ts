import {findLargestNumber, solve} from "./Year2025Day03";
import {loadLines} from "../../utils";

describe('Year2025 Day03', () => {

    describe('part 1', () => {
        describe('find largest 2 digit number', () => {
            [
                ['987654321111111', 98],
                ['811111111111119', 89],
                ['234234234234278', 78],
                ['818181911112111', 92],
            ].forEach((test: [string, number]) => {
                it(`${test[0]} should result in ${test[1]}`, () => expect(findLargestNumber(test[0], 2)).toBe(test[1]));
            });
        });
        it('example', () => expect(solve(loadLines('example.txt'), 2)).toBe(357));
        it('real', () => expect(solve(loadLines('data.txt'), 2)).toBe(16993));
    });

    describe('part 2', () => {
        describe('find largest 12 digit number', () => {
            [
                ['987654321111111', 987654321111],
                ['811111111111119', 811111111119],
                ['234234234234278', 434234234278],
                ['818181911112111', 888911112111],
            ].forEach((test: [string, number]) => {
                it(`${test[0]} should result in ${test[1]}`, () => expect(findLargestNumber(test[0], 12)).toBe(test[1]));
            });
        });
        it('example', () => expect(solve(loadLines('example.txt'), 12)).toBe(3121910778619));
        it('real', () => expect(solve(loadLines('data.txt'), 12)).toBe(168617068915447));
    });
});
