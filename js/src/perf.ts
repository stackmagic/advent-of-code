import {reduceUnits, Unit} from "./units";

export interface Stats {
    time: number;
    // properties below copied from MemoryUsage in process.d.ts because it isn't exported
    rss: number;
    heapTotal: number;
    heapUsed: number;
    external: number;
    arrayBuffers: number;
}

export class Perf {

    private readonly start = this.snapshot();

    public finish(toConsole = true): Stats {
        const end = this.snapshot();
        const diff = this.diff(this.start, end);

        if (toConsole) {
            this.dumpToConsole(diff);
        }

        return diff;
    }

    private snapshot(): Stats {
        if (this.gcAvailable()) {
            global.gc();
            global.gc();
        }
        return {
            time: performance.now(),
            ...process.memoryUsage(),
        };
    }

    private diff(start: Stats, end: Stats): Stats {
        return Object.fromEntries(
            Object.entries(end).map(([k, v]) => [k, v - start[k]])
        ) as unknown as Stats;
    }

    private dumpToConsole(stats: Stats): void {
        const clean = Object.fromEntries(
            Object.entries(stats)
                .filter(([k]) => k === 'time' || k === 'heapUsed')
                .map(([k, v]) => [k, v, k === 'time' ? Unit.MILLI : Unit.BYTE])
                .map(([k, v, u]) => [k, reduceUnits(v, u)])
        );

        const output = JSON.stringify(clean, null, 2)
            .split('\n')
            .map(it => it.trim())
            .join(' ')
        const msg = this.gcAvailable() ? '' : '\nnode should be started with --expose-gc --predictable to get accurate memory readings';
        console.log(output + msg);
    }

    private gcAvailable(): boolean {
        return typeof global.gc === 'function';
    }
}



