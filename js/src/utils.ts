import * as fs from 'fs-extra';

export function load(path: string): string {
    const prefix = getDirOfCaller();
    return fs.readFileSync(prefix + path, 'ascii');
}

export function loadLines(path: string): string[] {
    return load(path).split('\n');
}

export function loadLinesSplitByBlankLine(path: string): [string[], string[]] {
    const lines = loadLines(path);
    const blankIndex = lines.indexOf('');
    if (blankIndex < 0) {
        throw new Error('no blank line');
    }
    return [
        lines.slice(0, blankIndex),
        lines.slice(blankIndex + 1),
    ];
}



/** @returns a Map<Y, Map<X, value>> of each word in the file (split by spaces) */
export function loadWordGrid(path: string): Map<number, Map<number, string>> {
    return toWordGrid(loadLines(path));
}

/** @returns a Map<Y, Map<X, value>> of each word in the file (split by spaces) */
export function toWordGrid(lines: string[]): Map<number, Map<number, string>> {
    const rv = new Map<number, Map<number, string>>();
    lines.forEach((line, y) => {
        const lineMap = new Map<number, string>;
        line.trim()
            .split(/\s+/)
            .forEach((word, x) => lineMap.set(x, word));
        rv.set(y, lineMap);
    });
    return rv;
}

/** @returns a Map<Y, Map<X, value>> of each char in the file */
export function loadCharGrid(path: string): Map<number, Map<number, string>> {
    return toCharGrid(loadLines(path));
}

/** @returns a Map<Y, Map<X, value>> of each char in the file */
export function toCharGrid(lines: string[]): Map<number, Map<number, string>> {
    const rv = new Map<number, Map<number, string>>();
    lines.forEach((line, y) => {
        const lineMap = new Map<number, string>();
        [...line].forEach((value, x) => lineMap.set(x, value));
        rv.set(y, lineMap);
    });
    return rv;
}

export function write(path: string, data: string): void {
    const prefix = getDirOfCaller();
    fs.writeFileSync(prefix + path, data);
}

export function writeLines(path: string, data: string[]): void {
    write(path, data.join('\n'));
}

function getDirOfCaller(): string {
    return new Error().stack.split('\n')
        .map(it => it.substring(it.indexOf('(') + 1, it.lastIndexOf('/') + 1))
        .filter(it => it.length)
        .filter(it => !it.endsWith('/src/'))
        [0];
}
