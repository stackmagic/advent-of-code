import {rsum} from "../../utils";

export function solve(lines: string[], k: number): number {
    return lines
        .map(it => findLargestNumber(it, k))
        .reduce(rsum, 0);
}

export function findLargestNumber(str: string, k: number): number {

    if (k > str.length) {
        throw new Error('nope');
    }

    // reversing so index 0 is the least significant bit, seems easier to work this way
    const nums = [...str].map(Number).reverse();

    // "pointers" pointing to an index in the nums array. initially, ptrs[0] points at nums[0] and so on
    const ptrs = [...Array(k).keys()];

    // start with the rightmost/most significant pointer and find the largest number before the end of the nums array
    // then repeat with every less significant pointer but stop searching before hitting the previous pointer
    for (let p = k - 1; p >= 0; p--) {

        // max index to search for in the nums. if we read undefined, we're in the first iteration
        const endIdx = (ptrs[p + 1] ?? nums.length) - 1;

        // find the largest digit from the starting index to end of nums or before reaching the previous pointer
        let prevNum = 0;
        for (let n = p; n <= endIdx; n++) {
            const thisNum = nums[n];
            if (thisNum >= prevNum) {
                prevNum = thisNum;
                ptrs[p] = n;
            }
        }
    }

    // resolve pointers to values, re-reverse and convert to number
    return +ptrs
        .map(it => nums[it])
        .reverse()
        .join('');
}
