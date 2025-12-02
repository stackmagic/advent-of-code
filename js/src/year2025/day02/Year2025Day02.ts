export function solve(line: string): [number, number] {
    const ranges = line.split(',')
    let count1 = 0;
    let count2 = 0;

    for (const range of ranges) {
        const parts = range.split('-');
        const start = +parts[0];
        const end = +parts[1];

        for (let i = start; i <= end; i++) {
            if (isInvalidPart1(i)) {
                count1 += i;
            }
            if (isInvalidPart2(i)) {
                count2 += i;
            }
        }
    }
    return [count1, count2];
}

function isInvalidPart1(num: number): boolean {
    const str = `${num}`;
    const len = str.length;
    if (len % 2 !== 0) {
        return false;
    }

    const left = str.substring(0, len / 2);
    const right = str.substring(len / 2);

    return left === right;
}

function isInvalidPart2(num: number): boolean {
    const str = `${num}`;
    const len = str.length;

    for (let i = 2; i <= len; i++) {
        if (len % i !== 0) {
            continue;
        }

        const lenOfPart = len / i;
        const parts: Set<string> = new Set();
        for (let x = 0; x < i; x++) {
            parts.add(str.substring(x * lenOfPart, (x + 1) * lenOfPart));
        }

        if (parts.size === 1) {
            return true;
        }
    }

    return false;
}
