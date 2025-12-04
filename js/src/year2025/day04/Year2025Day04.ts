export function solve(grid: Map<number, Map<number, string>>, iterations: number): number {
    let rv = 0;
    for (let i = 0; i < iterations; i++) {
        cleanupRemovedRolls(grid);
        const removed = runIteration(grid);
        rv += removed;
        if (removed === 0) {
            break;
        }
    }
    return rv;
}

function runIteration(grid: Map<number, Map<number, string>>): number {
    let rv = 0;
    const ymax = grid.size;
    const xmax = grid.get(0).size;

    for (let y = 0; y < ymax; y++) {
        for (let x = 0; x < xmax; x++) {

            const adjacent = [
                grid.get(y - 1)?.get(x - 1),
                grid.get(y - 1)?.get(x),
                grid.get(y - 1)?.get(x + 1),
                grid.get(y)?.get(x - 1),
                grid.get(y)?.get(x + 1),
                grid.get(y + 1)?.get(x - 1),
                grid.get(y + 1)?.get(x),
                grid.get(y + 1)?.get(x + 1),
            ].filter(it => it === '@' || it === 'x').length

            const current = grid.get(y)?.get(x);
            if (adjacent < 4 && current === '@') {
                rv++;
                grid.get(y).set(x, 'x')
            }
        }
    }

    return rv;
}

function cleanupRemovedRolls(grid: Map<number, Map<number, string>>): void {
    const ymax = grid.size;
    const xmax = grid.get(0).size;

    for (let y = 0; y < ymax; y++) {
        for (let x = 0; x < xmax; x++) {
            const current = grid.get(y)?.get(x);
            if (current === 'x') {
                grid.get(y).set(x, '.');
            }
        }
    }
}