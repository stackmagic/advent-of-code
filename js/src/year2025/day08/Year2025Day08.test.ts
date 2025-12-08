import {loadLines} from "../../utils";
import {solve} from "./Year2025Day08";

describe('Year2025Day08', () => {
    it('example1', () => expect(solve(10, loadLines('example.txt'))).toBe(40));
    it('real1', () => expect(solve(1000, loadLines('data.txt'))).toBe(50568));
    it('example2', () => expect(solve(Number.MAX_SAFE_INTEGER, loadLines('example.txt'))).toBe(25272));
    it('real2', () => expect(solve(Number.MAX_SAFE_INTEGER, loadLines('data.txt'))).toBe(36045012));
});
