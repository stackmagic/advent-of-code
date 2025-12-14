import {solve1, toGraph} from "./Year2015Day07";
import {loadLines} from "../../utils";

describe('Year2015Day07', () => {
    it('example d', () => expect(solve1(loadLines('example.txt'), 'd')).toBe(72));
    it('example e', () => expect(solve1(loadLines('example.txt'), 'e')).toBe(507));
    it('example f', () => expect(solve1(loadLines('example.txt'), 'f')).toBe(492));
    it('example g', () => expect(solve1(loadLines('example.txt'), 'g')).toBe(114));
    it('example h', () => expect(solve1(loadLines('example.txt'), 'h')).toBe(65412));
    it('example i', () => expect(solve1(loadLines('example.txt'), 'i')).toBe(65079));
    it('example x', () => expect(solve1(loadLines('example.txt'), 'x')).toBe(123));
    it('example y', () => expect(solve1(loadLines('example.txt'), 'y')).toBe(456));
    it('real1', () => expect(solve1(loadLines('data.txt'), 'a')).toBe(16076));
    it('real2', () => expect(solve1(loadLines('data.txt'), 'a')).toBe(2797));
    it('graph', () => expect(toGraph(loadLines('data.txt')).length).not.toBe(0));
});
