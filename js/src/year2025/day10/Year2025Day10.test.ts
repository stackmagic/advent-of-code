import {loadLines} from "../../utils";
import {solve1, solve2} from "./Year2025Day10";

describe('Year2025Day10', () => {
    it('example1', () => expect(solve1(loadLines('example.txt'))).toBe(7));
    it('real1', () => expect(solve1(loadLines('data.txt'))).toBe(484));
    it('example2', () => expect(solve2(loadLines('example.txt'))).toBe(33));
    // it('example2[0]', () => expect(buttons1(parseButton(loadLines('example.txt')[2]))).toBe(10));
    // it('example2[1]', () => expect(buttons1(parseButton(loadLines('example.txt')[2]))).toBe(12));
    // it('example2[2]', () => expect(buttons1(parseButton(loadLines('example.txt')[2]))).toBe(11));
    it('real2', () => expect(solve2(loadLines('data.txt'))).toBe(444444));
});
