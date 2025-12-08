import {reduceUnits, Unit} from "./units";

describe('units', () => {
    it('should not reduce 7b', () => expect(reduceUnits(7, Unit.BIT)).toBe('7b'));
    it('should reduce 8b to 1B', () => expect(reduceUnits(8, Unit.BIT)).toBe('1B'));
    it('should reduce 123456789B to 117.74MB', () => expect(reduceUnits(123456789, Unit.BYTE)).toBe('117.74MB'));
});
