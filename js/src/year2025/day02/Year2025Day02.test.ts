import {load} from "../../utils";
import {solve} from './Year2025Day02';

describe('year2025 Day02', () => {
    it('example', () => expect(solve(load('example.txt'))).toStrictEqual([1227775554, 4174379265]));
    it('real', () => expect(solve(load('data.txt'))).toStrictEqual([23701357374, 34284458938]));
});
