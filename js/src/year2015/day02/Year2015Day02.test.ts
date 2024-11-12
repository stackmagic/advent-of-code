import {solve} from "./Year2015Day02";
import {loadLines} from "../../utils";

describe('Year2015 Day02',() => {

    let paperSqFt: number;
    let ribbonFt: number;

    beforeAll(() => {
        const lines = loadLines('data.txt');
        [paperSqFt, ribbonFt] = solve(lines);
    });

    it('part 1', () => expect(paperSqFt).toBe(1586300))
    it('part 2', () => expect(ribbonFt).toBe(3737498))
});
