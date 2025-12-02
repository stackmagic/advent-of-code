import {loadLines} from "../../utils";
import {solve} from './Year2025Day01';

describe('Year2025 Day01', () => {
    it('example', () => expect(solve(loadLines('example.txt'))).toStrictEqual([3, 6]));
    it('real', () => expect(solve(loadLines('data.txt'))).toStrictEqual([1071, 6700]));
});
