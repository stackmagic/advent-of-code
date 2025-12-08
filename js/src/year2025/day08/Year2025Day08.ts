import {Perf} from "../../perf";

export function solve(connections: number, lines: string[]): number {
    const perf = new Perf();

    // load junction boxes and calculate all distances between all of them
    const junctionBoxes = toCoords(lines);
    const distanceBetweenJunctionBoxes = new Map<number, [Coord, Coord]>();
    for (let i = 0; i < junctionBoxes.length; i++) {
        const boxI = junctionBoxes[i];
        for (let j = 0; j < junctionBoxes.length; j++) {
            if (i === j) {
                continue;
            }
            const boxJ = junctionBoxes[j];
            const dist = boxI.distanceTo(boxJ);
            distanceBetweenJunctionBoxes.set(dist, [boxI, boxJ]);
        }
    }

    // just the distances, shortest to longest
    const distancesSorted = [...distanceBetweenJunctionBoxes.keys()].sort((a, b) => a - b);

    // by default each junction box is its own circuit
    const circuits: Set<Coord>[] = junctionBoxes.map(it => new Set([it]));

    // make the N connections (shortest to longest) and merge circuits
    // because there is initially 1 circuit per junction box, we can use find
    // instead of filter and don't need to deal with lists inside the loop.
    for (let i = 0; i < connections; i++) {
        const distance = distancesSorted[i];
        const [boxA, boxB] = distanceBetweenJunctionBoxes.get(distance);

        // get the circuits that contain the 2 boxes of the pair and merge B into A, then remove B
        const circuitA = circuits.find(it => it.has(boxA));
        const indexB = circuits.findIndex(it => it.has(boxB));
        const circuitB = circuits[indexB];
        if (circuitA !== circuitB) {
            circuitB.forEach(b => circuitA.add(b));
            circuits.splice(indexB, 1);
        }

        // *** part 2 solution
        if (circuits.length === 1) {
            perf.finish();
            return boxA.x * boxB.x;
        }
    }

    // *** part 1 solution
    circuits.sort((a, b) => b.size - a.size);
    perf.finish();
    return circuits[0].size * circuits[1].size * circuits[2].size;
}

function toCoords(lines: string[]): Coord[] {
    return lines.map(line => new Coord(...line.split(',').map(Number) as [number, number, number]))
}

class Coord {
    constructor(public readonly x, public readonly y, public readonly z) {
    }

    public distanceTo(other: Coord): number {
        return Math.sqrt(
            Math.pow(this.x - other.x, 2) +
            Math.pow(this.y - other.y, 2) +
            Math.pow(this.z - other.z, 2)
        );
    }
}