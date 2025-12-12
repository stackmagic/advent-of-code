import {loadLines} from "../../utils";
import {solve1, solve2} from "./Year2025Day11";

describe('Year2025 Day11', () => {
    it('example1', () => expect(solve1(loadLines('example1.txt'))).toBe(5));
    it('real1', () => expect(solve1(loadLines('data.txt'))).toBe(543));
    it('example2', () => expect(solve2(loadLines('example2.txt'))).toBe(2));
    it('real2', () => expect(solve2(loadLines('data.txt'))).toBe(479511112939968));
});
