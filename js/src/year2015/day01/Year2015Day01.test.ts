import {solve} from "./Year2015Day01";

describe('Year2015 Day01', () => {

    let first: number;
    let end: number;

    beforeAll(() => {
        [first, end] = solve();
    });

    it('part 1', () => expect(end).toBe(232));
    it('part 2', () => expect(first).toBe(1783));
});
