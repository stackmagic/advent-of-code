import {solve} from "./Year2015Day06";

describe('Year2015 Day06', () => {

    let partACount: number;
    let partBCount: number;

    beforeAll(() => {
        [partACount, partBCount] = solve();
    });

    it('part a', () => expect(partACount).toBe(543903));
    it('part b', () => expect(partBCount).toBe(14687245));
});
