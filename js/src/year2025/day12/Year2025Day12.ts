import {rsum} from "../../utils";

export function solve1(lines: string[]): number {
    const [shapes, trees] = parse(lines);

    const feasible = trees
        // .filter(tree => {
        //     // checks if the total  space required fits in the available space
        //     const totalRequired = tree.packages
        //         .map((count, idx) => count * shapes[idx].spaceRequirement)
        //         .reduce(rsum, 0);
        //     return tree.availableSpace >= totalRequired;
        // })
        .filter(tree => {
            // check if there are enough 3x3 squares to fit everything without fancy packing
            const squaresAvailable = Math.floor(tree.x / 3) * Math.floor(tree.y / 3)
            const squaresRequired = tree.packages.reduce(rsum, 0);
            return squaresAvailable >= squaresRequired
        });

    return feasible.length;
}

function parse(lines: string[]): [Shape[], Tree[]] {
    const shapes: Shape[] = [];
    for (let i = 0; i <= 5; i++) {
        shapes.push(new Shape(i, lines.slice(i * 5 + 1, i * 5 + 4)));
    }

    const trees = lines.filter(line => line.includes('x'))
        .map(line => {
            const parts = line.split(': ');
            const size = parts[0].split('x').map(Number) as [number, number];
            const packages = parts[1].split(' ').map(Number);
            return new Tree(...size, packages);
        })

    return [shapes, trees];
}

class Shape {
    public readonly spaceRequirement: number;

    constructor(public readonly id: number, public readonly shape: string[]) {
        this.spaceRequirement = [...shape.join('')].filter(c => c === '#').length;
    }
}

class Tree {
    public readonly availableSpace: number;

    constructor(public readonly x: number, public readonly y: number, public readonly packages: number[]) {
        this.availableSpace = x * y;
    }
}
