import {loadGrid} from "../../utils";
import {solve} from "./Year2025Day04"

describe('Year2025 Day04', () => {
    it('example1', () => expect(solve(loadGrid('example.txt'), 1)).toBe(13));
    it('real1', () => expect(solve(loadGrid('data.txt'), 1)).toBe(1460));
    it('example2', () => expect(solve(loadGrid('example.txt'), Number.MAX_SAFE_INTEGER)).toBe(43));
    it('real2', () => expect(solve(loadGrid('data.txt'), Number.MAX_SAFE_INTEGER)).toBe(9243));
});
