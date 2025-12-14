import {writeLines} from "../../utils";

export function solve1(lines: string[], output: string): number {
    return run(parse(lines), output);
}

export function solve2(lines: string[]): number {
    const ops = parse(lines);
    const aOut = run(ops, 'a');
    ops.set('b', new SetNumberOp(aOut, 'b'));
    return run(ops, 'a');
}

function run(ops: Map<string, Op>, output: string) {
    return ops.get(output).evaluate(ops);
}

export function toGraph(data: string[]): string[] {
    const ops = [...parse(data).values()];
    const lines = [];
    lines.push('digraph G {');
    lines.push('    rankdir=LR;');
    lines.push('    node [shape=box, style=rounded, fontname="Arial", fontsize=10];');
    lines.push('    edge [fontname="Arial", fontsize=9, arrowsize=0.8];');
    lines.push(...ops.flatMap(op => op.toGraph().map(line => '    ' + line)));
    lines.push('    a [style="filled,rounded",fillcolor=lightblue];')
    lines.push('    b [style="filled,rounded",fillcolor=lightgreen];')
    lines.push('}');
    writeLines('graph.dot', lines);
    return lines;
}

abstract class Op {
    constructor(public readonly output: string) {
    }

    abstract evaluate(ops: Map<string, Op>): number;

    abstract toGraph(): string[];
}

class SetNumberOp extends Op {
    constructor(public readonly value: number,
                output: string) {
        super(output);
    }

    public static create(parts: string[]): SetNumberOp {
        return new SetNumberOp(+parts[0], parts[2]);
    }

    public evaluate(ops: Map<string, Op>): number {
        return 0xFFFF & (this.value);
    }

    public toGraph(): string[] {
        return [
            `${this.value} -> ${this.output};`,
        ];
    }
}

class CopyOp extends Op {
    constructor(public readonly input: string,
                output: string) {
        super(output);
    }

    public static create(parts: string[]): CopyOp {
        return new CopyOp(parts[0], parts[2]);
    }

    public evaluate(ops: Map<string, Op>): number {
        return 0xFFFF & (ops.get(this.input).evaluate(ops));
    }

    public toGraph(): string[] {
        return [
            `${this.input} -> ${this.output};`,
        ];
    }
}

class AndOp extends Op {
    constructor(public readonly input1: string,
                public readonly input2,
                output: string) {
        super(output);
    }

    public static create(parts: string[]): AndOp {
        return new AndOp(parts[0], parts[2], parts[4]);
    }

    public evaluate(ops: Map<string, Op>): number {
        return 0xFFFF & (ops.get(this.input1).evaluate(ops) & ops.get(this.input2).evaluate(ops));
    }

    public toGraph(): string[] {
        return [
            `${this.input1} -> ${this.output};`,
            `${this.input2} -> ${this.output};`,
            `${this.output} [label="AND -> ${this.output}"];`,
        ];
    }
}

class OrOp extends Op {
    constructor(public readonly input1: string,
                public readonly input2,
                output: string) {
        super(output);
    }

    public static create(parts: string[]): OrOp {
        return new OrOp(parts[0], parts[2], parts[4]);
    }

    public evaluate(ops: Map<string, Op>): number {
        return 0xFFFF & (ops.get(this.input1).evaluate(ops) | ops.get(this.input2).evaluate(ops));
    }

    public toGraph(): string[] {
        return [
            `${this.input1} -> ${this.output};`,
            `${this.input2} -> ${this.output};`,
            `${this.output} [label="OR -> ${this.output}"];`,
        ];
    }
}

class LeftShiftOp extends Op {
    constructor(public readonly input: string,
                public readonly shift: number,
                output: string) {
        super(output);
    }

    public static create(parts: string[]): LeftShiftOp {
        return new LeftShiftOp(parts[0], +parts[2], parts[4]);
    }

    public evaluate(ops: Map<string, Op>): number {
        return 0xFFFF & (ops.get(this.input).evaluate(ops) << this.shift);
    }

    public toGraph(): string[] {
        return [
            `${this.input} -> ${this.output};`,
            `${this.output} [label="LSHIFT ${this.shift} -> ${this.output}"];`,
        ];
    }
}

class RightShiftOp extends Op {
    constructor(public readonly input: string,
                public readonly shift: number,
                output: string) {
        super(output);
    }

    public static create(parts: string[]): RightShiftOp {
        return new RightShiftOp(parts[0], +parts[2], parts[4]);
    }

    public evaluate(ops: Map<string, Op>): number {
        return 0xFFFF & (ops.get(this.input).evaluate(ops) >>> this.shift);
    }

    public toGraph(): string[] {
        return [
            `${this.input} -> ${this.output};`,
            `${this.output} [label="RSHIFT ${this.shift} -> ${this.output}"];`,
        ];
    }
}

class NotOp extends Op {
    constructor(public readonly input: string,
                output: string) {
        super(output);
    }

    public static create(parts: string[]): NotOp {
        return new NotOp(parts[1], parts[3]);
    }

    public evaluate(ops: Map<string, Op>): number {
        return 0xFFFF & (~ops.get(this.input).evaluate(ops));
    }

    public toGraph(): string[] {
        return [
            `${this.input} -> ${this.output};`,
            `${this.output} [label="NOT -> ${this.output}"];`,
        ];
    }
}

type OpFactory = (parts: string[]) => Op;
const factoryMap = new Map<string, OpFactory>();
factoryMap.set('AND', AndOp.create);
factoryMap.set('OR', OrOp.create);
factoryMap.set('LSHIFT', LeftShiftOp.create);
factoryMap.set('RSHIFT', RightShiftOp.create);
factoryMap.set('NOT', NotOp.create);

/** @returns Map<Output, Operation> */
export function parse(lines: string[]): Map<string, Op> {
    const ops = lines.map(line => {
        const parts = line.split(' ');
        const factory = [...factoryMap.entries()]
            .filter(([key, fn]) => line.includes(key))
            .map(([key, fn]) => fn);
        if (factory.length) {
            return factory[0](parts);
        } else {
            if (isNaN(+parts[0])) {
                return CopyOp.create(parts);
            } else {
                return SetNumberOp.create(parts);
            }
        }
    });

    const rv = new Map<string, Op>();
    ops.forEach(op => rv.set(op.output, op));
    return rv;
}
