const crypto = require('node:crypto');

export function solve(str: string, leading: number): number {
    let suffix = 0;
    outer: while (true) {
        const digest: string = crypto.createHash('md5')
            .update(str + suffix)
            .digest('hex');

        for (let i = 0; i < leading; i++) {
            if (digest.at(i) !== '0') {
                suffix++;
                continue outer;
            }
        }

        return suffix;
    }
}
