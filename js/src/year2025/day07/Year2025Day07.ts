export function solve1(lines: string[]): number {
    let splits = 0;
    for (let row = 1; row < lines.length; row++) {
        const lastLine = [...lines[row - 1]];
        const thisLine = [...lines[row]];

        for (let col = 0; col < thisLine.length; col++) {
            const lastChar = lastLine[col];
            const thisChar = thisLine[col];

            if (lastChar === 'S' || lastChar === '|') {
                if (thisChar === '.') {
                    thisLine[col] = '|';
                }
                if (thisChar === '^') {
                    // this is not the same as counting the number of '^' in the
                    // input, as a '^' with a '.' above doesn't count as a split
                    splits++;
                    if (thisLine[col - 1] === '.') {
                        thisLine[col - 1] = '|';
                    }
                    if (thisLine[col + 1] === '.') {
                        thisLine[col + 1] = '|';
                    }
                }
            }
        }

        lines[row] = thisLine.join('');
        // console.log('---------------------\n' + splits + '\n' + lines.join('\n'));
    }

    // console.log(lines.join('\n'));
    return splits;
}

export function solve2(lines: string[]): number {
    solve1(lines);

    // last row, count each ray as 1
    let nums = [...lines[lines.length - 1]]
        .map(c => c === '|' ? 1 : 0);

    for (let row = lines.length - 2; row >= 1; row--) {

        const line = [...lines[row]];
        const newNums = new Array(line.length).fill(0);

        // if its a ray, copy the value from below
        for (let col = 0; col < line.length; col++) {
            if (line[col] === '|') {
                newNums[col] = nums[col];
            }
        }

        // if its a splitter, add the left and right numbers
        for (let col = 0; col < line.length; col++) {
            if (line[col] === '^') {
                newNums[col] = newNums[col - 1] + newNums[col + 1];
            }
        }

        nums = newNums;
    }

    return nums.filter(it => !!it)[0];
}
