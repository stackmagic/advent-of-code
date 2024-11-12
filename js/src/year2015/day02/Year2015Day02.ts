function surfacearea(l: number, w: number, h: number): number {
    return (2 * l * w) + (2 * w * h) + (2 * h * l);
}

function slack(l: number, w: number, h: number): number {
    return Math.min(l * w, l * h, w * h);
}

function perimeter(dims: number[]): number {
    const sorted: number[] = [...dims].sort((a, b) => a - b);
    const a = sorted[0];
    const b = sorted[1];
    return a + a + b + b;
}

function volume(l: number, w: number, h: number): number {
    return l * w * h;
}

export function solve(lines: string[]): [number, number] {
    let paperSqFt = 0;
    let ribbonFt = 0;

    lines.forEach(line => {
        const dims: number[] = line.split('x').map(it => parseInt(it, 10));
        paperSqFt += surfacearea(dims[0], dims[1], dims[2])
        paperSqFt += slack(dims[0], dims[1], dims[2]);

        ribbonFt += perimeter(dims);
        ribbonFt += volume(dims[0], dims[1], dims[2]);
    });

    return [paperSqFt, ribbonFt];
}
