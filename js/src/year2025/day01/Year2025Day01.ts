export function solve(lines: string[]): [number, number] {
    let count1 = 0;
    let count2 = 0;
    let curr = 50;

    for (const line of lines) {
        const add = line.charAt(0) === 'R';
        const num = +line.substring(1);

        if (add) {
            for (let i = 0; i < num; i++) {
                curr++;
                if (curr % 100 === 0) {
                    count2++;
                }
            }
        } else {
            for (let i = 0; i < num; i++) {
                curr--;
                if (curr % 100 === 0) {
                    count2++;
                }
            }
        }

        if (curr % 100 === 0) {
            count1++;
        }
    }

    return [count1, count2];
}
