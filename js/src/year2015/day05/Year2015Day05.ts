function hasAtLeastThreeVowels(line: string): boolean {
    const vowelCount: number = Array.from(line)
        .filter(c => 'aeiou'.includes(c))
        .length;
    return vowelCount >= 3;
}

function hasDoubleLetter(line: string): boolean {
    let hasDouble = false;
    for (let i = 0; i < line.length; i++) {
        if (line[i] === line[i + 1]) {
            return true;
        }
    }
    return false;
}

function hasNoForbiddenStrings(line: string): boolean {
    return !(line.includes('ab') || line.includes('cd') || line.includes('pq') || line.includes('xy'));
}

function checkNiceA(line: string): boolean {
    return hasAtLeastThreeVowels(line) &&
        hasDoubleLetter(line) &&
        hasNoForbiddenStrings(line);
}

export function solveA(lines: string[]): number {
    return lines
        .map(line => checkNiceA(line) ? 1 : 0)
        .reduce((a, b) => a + b, 0);
}

export function hasDoublePair(line: string): boolean {
    return line.match(/.*(..).*\1.*/)?.length >= 2;
}

export function hasSandwich(line: string): boolean {
    for (let i = 0; i < line.length - 2; i++) {
        if (line[i] === line[i + 2]) {
            return true;
        }
    }
    return false;
}

function checkNiceB(line: string): boolean {
    return hasDoublePair(line) &&
        hasSandwich(line);
}

export function solveB(lines: string[]): number {
    return lines
        .map(line => checkNiceB(line) ? 1 : 0)
        .reduce((a, b) => a + b, 0);
}
