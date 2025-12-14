import {solve} from "./Year2015Day04";

describe('Year2015 Day04', () => {
    it('example1a', () => expect(solve('abcdef', 5)).toBe(609043));
    it('example1b', () => expect(solve('pqrstuv', 5)).toBe(1048970));
    it('real1', () => expect(solve('iwrupvqb', 5)).toBe(346386));
    it('real2', () => expect(solve('iwrupvqb', 6)).toBe(9958218));
});
