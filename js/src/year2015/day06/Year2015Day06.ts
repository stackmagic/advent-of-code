import {loadLines} from "../../utils";
import * as fs from 'fs-extra';
import {PNG} from 'pngjs';

function command(line: string) {
    if (line.startsWith('toggle ')) {
        return undefined;
    }
    return line.startsWith('turn on');
}

function stripCommand(cmd: boolean, line: string): string {
    if (cmd) {
        return line.substring(8);
    }
    if (cmd === false) {
        return line.substring(9);
    }
    return line.substring(7);
}

function coords(line: string): [number, number, number, number] {
    const parts = line.split(' ');
    const start = parts[0].split(',');
    const end = parts[2].split(',');
    return [
        parseInt(start[0]),
        parseInt(start[1]),
        parseInt(end[0]),
        parseInt(end[1]),
    ];
}

function dumpImage(path: string, data: number[][]): void {
    const height = data.length;
    const width = data[0].length;
    const png = new PNG({width, height});
    for (let y = 0; y < height; y++) {
        for (let x = 0; x < width; x++) {
            const value = data[y][x];
            const idx = (y * width + x) * 4; // RGBA index
            png.data[idx] = value; // R
            png.data[idx + 1] = value; // G
            png.data[idx + 2] = value; // B
            png.data[idx + 3] = 255; // A (fully opaque)
        }
    }

    png.pack().pipe(fs.createWriteStream(path));
}

export function solve(dumpImages: boolean = false): [number, number] {
    const lines: string[] = loadLines('data.txt')

    const arr1: boolean[][] = [];
    const arr2: number[][] = [];
    for (let x = 0; x < 999; x++) {
        arr1[x] = [];
        arr2[x] = [];
        for (let y = 0; y < 999; y++) {
            arr1[x][y] = false;
            arr2[x][y] = 0;
        }
    }

    lines.forEach((line) => {
        const cmd = command(line);
        line = stripCommand(cmd, line);
        const [xs, ys, xe, ye] = coords(line);
        for (let x = xs; x <= xe; x++) {
            for (let y = ys; y <= ye; y++) {
                // part a
                if (cmd === undefined) {
                    arr1[x][y] = !arr1[x][y];
                } else {
                    arr1[x][y] = cmd;
                }

                // part b
                if (cmd === undefined) {
                    arr2[x][y] += 2;
                } else if (cmd === true) {
                    arr2[x][y] += 1;
                } else if (cmd === false) {
                    arr2[x][y] -= 1;
                    arr2[x][y] = Math.max(0, arr2[x][y]);
                }
            }
        }
    });

    if (dumpImages) {
        // dump the data as images - missed opportunity to hide a hidden message
        const maxVal = arr2.flatMap(it => it)
            .filter(it => isFinite(it))
            .sort()
            .reverse()[0];
        const arr1Img: number[][] = arr1.map(x => x.map(y => y ? 255 : 0))
        dumpImage('year2015day06a.png', arr1Img);
        const arr2Img: number[][] = arr2.map(x => x.map(y => Math.floor((y / 9) * 255)));
        dumpImage('year2015day06b.png', arr2Img);
    }

    // calculate result and return
    let partACount = 0;
    let partBCount = 0;
    for (let x = 0; x < 999; x++) {
        for (let y = 0; y < 999; y++) {
            if (arr1[x][y]) {
                partACount++;
            }
            partBCount += arr2[x][y];
        }
    }

    return [partACount, partBCount];
}
