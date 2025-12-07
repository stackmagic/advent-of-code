import {loadLines} from "../../utils";
import {solve1, solve2} from "./Year2025Day07";

describe('Year2025Day07', () => {
    it('example1', () => expect(solve1(loadLines('example.txt'))).toBe(21));
    it('real1', () => expect(solve1(loadLines('data.txt'))).toBe(1560));
    it('example2', () => expect(solve2(loadLines('example.txt'))).toBe(40));
    it('real2', () => expect(solve2(loadLines('data.txt'))).toBe(25592971184998));
});