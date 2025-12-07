import {solve1, solve2} from "./Year2025Day05"
import {loadLinesSplitByBlankLine} from "../../utils";

describe("Year2025 Day05", () => {
    it('example1', () => expect(solve1(...loadLinesSplitByBlankLine('example.txt'))).toBe(3));
    it('real1', () => expect(solve1(...loadLinesSplitByBlankLine('data.txt'))).toBe(821));
    it('example2b', () => expect(solve2(loadLinesSplitByBlankLine('example.txt')[0])).toBe(14));
    it('real2b', () => expect(solve2(loadLinesSplitByBlankLine('data.txt')[0])).toBe(344771884978261));
});
