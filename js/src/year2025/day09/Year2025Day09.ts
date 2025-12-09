import {Perf} from "../../perf";

/*
 * https://github.com/zebalu/advent-of-code-2025/blob/master/Day09.java
 * the above solution helped but i'm still struggling to understand the overlap check used.
 * it helped to understand the problem a bit better and to massively simplify my solution
 * to part1. but for part2 i needed to do my own check, for it to make sense.
 * part2 takes ~17sec on an i7-1365U
 */

export function solve1(lines: string[]): number {
    const coords = toCoords(lines);
    const rects = toRectangles(coords);
    return rects.map(r => r.area())
        .sort((a, b) => b - a)[0];
}

export function solve2(lines: string[]): number {
    const perf = new Perf();
    const coords = toCoords(lines);
    const rects = toRectangles(coords);
    const vertices = toVertices(coords);
    // filter out all rectangles that intersect one of the vertices. which is the case, when:
    // - one of the vertex endpoints lies within the rectangle
    // - the vertex is fully passing through the rectangle
    const max = rects.filter(r => !vertices.some(v => r.intersects(v)))
        .map(r => r.area())
        .sort((a, b) => b - a)[0];
    perf.finish();
    return max;
}

function toRectangles(coords: Coord[]): Rectangle[] {
    const rectangles: Rectangle[] = [];
    for (let i = 0; i < coords.length - 1; i++) {
        for (let j = i + 1; j < coords.length; j++) {
            rectangles.push(Rectangle.fromCoords(coords[i], coords[j]));
        }
    }
    return rectangles;
}

function toCoords(lines: string[]): Coord[] {
    return lines.map(it => new Coord(...it.split(',').map(Number) as [number, number]));
}

function toVertices(coords: Coord[]): Vertex[] {
    coords = [...coords, coords[0]];
    const vertices: Vertex[] = [];
    for (let i = 0; i < coords.length - 1; i++) {
        vertices.push(new Vertex(coords[i], coords[i + 1]));
    }
    return vertices;
}

export class Coord {
    constructor(public readonly x: number, public readonly y: number) {
    }
}

export class Vertex {
    public constructor(public readonly a: Coord, public readonly b: Coord) {
    }
}

export class Rectangle {
    private constructor(public readonly min: Coord, public readonly max: Coord) {
    }

    public static fromCoords(a: Coord, b: Coord): Rectangle {
        return new Rectangle(
            new Coord(Math.min(a.x, b.x), Math.min(a.y, b.y)),
            new Coord(Math.max(a.x, b.x), Math.max(a.y, b.y)),
        );
    }

    public area(): number {
        return (1 + this.max.x - this.min.x) * (1 + this.max.y - this.min.y)
    }

    public intersects(vertex: Vertex): boolean {
        return this.contains(vertex.a) || this.contains(vertex.b) || this.passing(vertex);
    }

    public contains(point: Coord): boolean {
        return this.min.x < point.x && point.x < this.max.x
            && this.min.y < point.y && point.y < this.max.y;
    }

    public passing(vertex: Vertex): boolean {
        const vmin = new Coord(Math.min(vertex.a.x, vertex.b.x), Math.min(vertex.a.y, vertex.b.y));
        const vmax = new Coord(Math.max(vertex.a.x, vertex.b.x), Math.max(vertex.a.y, vertex.b.y));

        // vertical vertex
        if (vmin.x === vmax.x) {
            return this.min.x < vmin.x && vmin.x < this.max.x
                && vmin.y <= this.min.y && this.max.y <= vmax.y;
        }

        // horizontal vertex
        if (vmin.y === vmax.y) {
            return this.min.y < vmin.y && vmin.y < this.max.y
                && vmin.x <= this.min.x && this.max.x <= vmax.x;
        }

        throw new Error('only rectangular plz!');
    }
}
