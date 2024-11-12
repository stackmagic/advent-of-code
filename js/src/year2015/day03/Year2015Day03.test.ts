import {solve} from "./Year2015Day03";

describe('Year2015Day03', () => {

    let visitedAlone;
    let visitedRobo;

    beforeAll(() => {
        [visitedAlone, visitedRobo] = solve();
    });

    it('part 1', () => expect(visitedAlone).toBe(2592));
    it('part 2', () => expect(visitedRobo).toBe(2360));
})