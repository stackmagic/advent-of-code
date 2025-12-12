import {rsum, writeLines} from "../../utils";
import {Perf} from "../../perf";

/*
 * visualizing the graph helped a lot to realize this is a DAG (directed acyclic graph - like git history)
 * and the magic numbers for the depth parameter below are from me counting the distance between nodes to
 * stop this depth-first recursion when there's no use in searching any deeper. still takes almost 2mins...
 */

export function solve1(lines: string[]): number {
    const nodes = parseNodes(lines)

    // dump to graph.dot file
    toGraph(nodes);

    const map = toMap(nodes);
    return countPaths('you', 'out', map, 8);
}

export function solve2(lines: string[]): number {
    const perf = new Perf();

    const nodes = parseNodes(lines);
    const map = toMap(nodes);

    const a = countPaths('svr', 'fft', map, 12);
    const b = countPaths('fft', 'dac', map, 17);
    const c = countPaths('dac', 'out', map, 11);

    perf.finish();

    return a * b * c;
}

function countPaths(from: string, to: string, map: Map<string, Node>, depth: number): number {
    const src = map.get(from);
    if (!src || depth === 0) {
        return 0;
    }
    if (src.children.includes(to)) {
        return 1;
    }
    const depthMinus = depth - 1;
    return src.children
        .map(c => countPaths(c, to, map, depthMinus))
        .reduce(rsum, 0);
}

function toMap(nodes: Node[]): Map<string, Node> {
    const rv = new Map<string, Node>();
    nodes.forEach(n => rv.set(n.name, n));
    return rv;
}

function parseNodes(lines: string[]): Node[] {
    return lines.map(it => Node.fromString(it));
}

class Node {
    private constructor(public readonly name: string,
                        public readonly children: string[]) {
    }

    public static fromString(line: string): Node {
        const parts = line.split(':');
        return new Node(parts[0], parts[1].trim().split(' '));
    }
}

/** paste output here: https://dreampuf.github.io/GraphvizOnline or call dot on the commandline */
function toGraph(nodes: Node[]): void {
    const lines = [];
    lines.push('digraph G {');
    lines.push('    rankdir=LR;');
    lines.push('    node [shape=box, style=rounded, fontname="Arial", fontsize=10];');
    lines.push('    edge [fontname="Arial", fontsize=9, arrowsize=0.8];');
    lines.push(...[...new Set(nodes.flatMap(n => n.children.map(c => `    ${n.name} -> ${c};`)))].sort());
    lines.push('    you [style="filled,rounded",fillcolor=lightblue];');
    lines.push('    out [style="filled,rounded",fillcolor=lightgreen];');
    lines.push('    svr [style="filled,rounded",fillcolor=cornsilk];');
    lines.push('    dac [style="filled,rounded",fillcolor=gold];');
    lines.push('    fft [style="filled,rounded",fillcolor=aquamarine];');
    lines.push('}');
    writeLines('graph.dot', lines);
}
