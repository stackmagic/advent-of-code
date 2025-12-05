export function solve1(strRanges: string[], strIds: string[]): number {
    const ranges = toSortedRanges(strRanges);
    const ids = strIds.map(Number);
    return ids.filter(id => ranges.some(r => r.includes(id))).length;
}

/**
 * it first took a convoluted implementation to finally wrap my head around the problem.
 *
 * here we sort the ranges by start and check if the next elements' start value is
 * within our range. if so, we merge the next element into the current one, replace
 * the current and remove the next - then re-check the element we just created, since
 * the end value may have risen or there are multiple elements that would have fallen
 * into the range we started with.
 */
export function solve2(strRanges: string[]): number {
    const ranges = toSortedRanges(strRanges);

    for (let i = 0; i < ranges.length - 1; i++) {

        const a = ranges[i];
        const b = ranges[i + 1];

        if (a.includes(b.start)) {
            const merged = new Range(
                a.start,
                Math.max(a.end, b.end),
            )

            ranges[i] = merged;
            ranges.splice(i + 1, 1);
            i--;
        }
    }

    // count numbers
    return ranges.map(r => r.end - r.start + 1)
        .reduce((acc, curr) => acc + curr, 0);
}

class Range {
    constructor(public readonly start: number,
                public readonly end: number) {
    }

    public includes(n: number) {
        return n >= this.start && n <= this.end;
    }
}

function toSortedRanges(str: string[]): Range[] {
    return str.map(s => new Range(...s.split('-').map(Number) as [number, number]))
        .sort((a, b) => a.start - b.start);
}
