/**
 * @file: 求最长回文子串
 */
import isPalindrome from '../string/isPalindrome';
/**
 * 思路一：i, j从最2边开始找，找到一个可能的子串判断是否是回文
 * @param {string} s
 * @return {string}
 */
export function longestPalindrome(s) {
    let len = s.length;
    if (len === 0 || len === 1) {
        return s;
    }
    let i = 0;
    let j = len;
    let maxStr = '';
    let maxLen = 0;
    for (let i = 0; i < len; i++) {
        j = len;
        while (j-- > i) {
            if (s[j] === s[i] && j - i + 1 > maxLen) {
                let sublen = j - i + 1;
                let substr = s.substr(i, sublen);
                if (isPalindrome(substr)) {
                    if (sublen > maxLen) {
                        maxStr = substr;
                        maxLen = sublen;
                    }
                }
            }
        }
    }
    return maxStr;
};

/**
 * 思路2：回文字符串的特点是有个中间字符，左右两边相等，可能有奇数或偶数的情况，
 * 因此遍历字符，已每个字符为中间点判断回文字符串
 * @param  {[type]} s [description]
 * @return {[type]}   [description]
 */
export function longestPalindrome2(s) {
    let len = s.length;
    if (len === 0 || len === 1) {
        return s;
    }
    let maxStr = s[0];
    let maxLen = 1;
    for (let i = 1; i < len; i++) {
        let mid = s[i];
        let j = 0;
        let subLen = 0;
        for (j = 0; i - j - 1 >= 0 && i + j < len; j++) {
            if (s[i + j] !== s[i - j - 1]) {
                break;
            }
        }
        subLen = 2 * (--j + 1);
        if (subLen > maxLen) {
            maxStr = s.substr(i - j - 1, subLen);
            maxLen = subLen;
        }

        for (j = 1; i - j >= 0 && i + j < len; j++) {
            if (s[i + j] !== s[i - j]) {
                break;
            }
        }
        subLen = 2 * --j + 1;
        if (subLen > maxLen) {
            maxStr = s.substr(i - j, subLen);
            maxLen = subLen;
        }
    }
    return maxStr;
};
