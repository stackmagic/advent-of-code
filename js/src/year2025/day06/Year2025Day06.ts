import {rmul, rsum} from "../../utils";

export function solve1(grid: Map<number, Map<number, string>>): number {
    const ymax = grid.size;
    const xmax = grid.get(0).size;

    let sum = 0;

    for (let x = 0; x < xmax; x++) {
        const col: string[] = [];
        for (let y = 0; y < ymax; y++) {
            col.push(grid.get(y).get(x))
        }

        const nums = col.slice(0, ymax - 1).map(Number);
        const op = col[ymax - 1];
        sum += doCalc(op, nums);
    }

    return sum;
}

export function solve2(lines: string[]): number {
    const rot = rotate(lines);
    let sum = 0;
    let rows: string[] = [];
    for (let y = 0; y < rot.length; y++) {
        const line = rot[y];
        if (line.trim().length === 0) {
            sum += process(rows);
            rows = [];
        } else {
            rows.push(line);
        }
    }

    sum += process(rows);

    return sum;
}

function doCalc(op: string, nums: number[]): number {
    return nums.reduce(
        op === '+' ? rsum : rmul,
        op === '+' ? 0 : 1,
    );
}

function process(lines: string[]): number {
    const op = lines.at(-1).at(-1);
    const nums = lines
        .map(it => it.trim())
        .map(it => it.replaceAll(op, ''))
        .map(Number);
    return doCalc(op, nums);
}

/** "rotate" the array 90° counter-clockwise */
function rotate(lines: string[]): string[] {
    const rv: string[] = [];
    let ymax = lines.length;
    let xmax = Math.max(...lines.map(it => it.length));

    for (let y = 0; y < ymax; y++) {
        const line = lines[y];
        for (let x = 0; x < xmax; x++) {
            const fy = xmax - x - 1;
            rv[fy] ??= '';
            rv[fy] += (line[x] ?? ' ');
        }
    }

    return rv;
}
