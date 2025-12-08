export enum Unit {
    BIT = 'b', BYTE = 'B', KILOBYTE = 'KB', MEGABYTE = 'MB', GIGABYTE = 'GB', TERABYTE = 'TB',
    MILLI = 'ms', SECOND = 'secs', MINUTE = 'mins', HOUR = 'hrs', DAY = 'days', YEAR = 'years',
}

class Operation {
    constructor(public readonly fromUnit: Unit,
                public readonly divisor: number,
                public readonly toUnit: Unit) {
    }

    public canApply(value: number) {
        return Math.abs(this.apply(value)) >= 1;
    }

    public apply(value: number) {
        return value / this.divisor
    }
}

const operations = [
    new Operation(Unit.BIT, 8, Unit.BYTE),
    new Operation(Unit.BYTE, 1024, Unit.KILOBYTE),
    new Operation(Unit.KILOBYTE, 1024, Unit.MEGABYTE),
    new Operation(Unit.MEGABYTE, 1024, Unit.GIGABYTE),
    new Operation(Unit.GIGABYTE, 1024, Unit.TERABYTE),

    new Operation(Unit.MILLI, 1000, Unit.SECOND),
    new Operation(Unit.SECOND, 60, Unit.MINUTE),
    new Operation(Unit.MINUTE, 60, Unit.HOUR),
    new Operation(Unit.HOUR, 24, Unit.DAY),
    new Operation(Unit.DAY, 365, Unit.YEAR),
];

export function reduceUnits(value: number, unit: Unit): string {
    while (true) {
        const operation = operations.find(it => it.fromUnit === unit);
        if (operation?.canApply(value)) {
            value = operation.apply(value);
            unit = operation.toUnit;
        } else {
            const valueStr = value.toString();
            const rounded = valueStr.includes('.') && valueStr.split('.')[1].length > 2
                ? value.toFixed(2)
                : valueStr;
            return `${rounded}${unit}`;
        }
    }
}
