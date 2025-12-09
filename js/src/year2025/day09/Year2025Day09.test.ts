import {loadLines} from "../../utils";
import {Coord, Rectangle, solve1, solve2, Vertex} from "./Year2025Day09";

describe('Year2025Day09', () => {
    it('example1', () => expect(solve1(loadLines('example.txt'))).toBe(50));
    it('real1', () => expect(solve1(loadLines('data.txt'))).toBe(4758121828));
    it('example2', () => expect(solve2(loadLines('example.txt'))).toBe(24));
    it('real2', () => expect(solve2(loadLines('data.txt'))).toBe(1577956170));

    describe('Rectangle', () => {
        let r: Rectangle;

        beforeEach(() => r = Rectangle.fromCoords(new Coord(10, 10), new Coord(20, 20)));

        it('outside', () => {
            const v = new Vertex(new Coord(10, 9), new Coord(20, 9));
            expect(r.intersects(v)).toBeFalsy();
        });

        it('touching', () => {
            const v = new Vertex(new Coord(5, 10), new Coord(25, 10));
            expect(r.intersects(v)).toBeFalsy();
        });

        it('passing trough horizontal', () => {
            const v = new Vertex(new Coord(5, 11), new Coord(25, 11));
            expect(r.intersects(v)).toBeTruthy();
        });

        it('passing trough vertical', () => {
            const v = new Vertex(new Coord(11, 5), new Coord(11, 25));
            expect(r.intersects(v)).toBeTruthy();
        });
    });
});
