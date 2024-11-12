import * as fs from 'fs-extra';

export function load(path: string): string {
    const prefix = getDirOfCaller();
    return fs.readFileSync(prefix + path, 'ascii');
}

export function loadLines(path: string): string[] {
    return load(path).split('\n');
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