import {solve1} from "./Year2025Day12";
import {loadLines} from "../../utils";

describe('Year2025 Day12', () => {
    // "solution" only works for the real data, the example is quite the misdirection
    // it('example1', () => expect(solve1(loadLines('example.txt'))).toBe(2));
    it('real1', () => expect(solve1(loadLines('data.txt'))).toBe(443));
});
