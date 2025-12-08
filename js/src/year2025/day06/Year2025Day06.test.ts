import {loadLines, loadWordGrid} from "../../utils";
import {solve1, solve2} from "./Year2025Day06";

describe('Year2025Day06', () => {
    it('example1', () => expect(solve1(loadWordGrid('example.txt'))).toBe(4277556));
    it('real1', () => expect(solve1(loadWordGrid('data.txt'))).toBe(4387670995909));
    it('example2', () => expect(solve2(loadLines('example.txt'))).toBe(3263827));
    it('real2', () => expect(solve2(loadLines('data.txt'))).toBe(9625320374409));
});
