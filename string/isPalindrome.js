/**
 * @file:
 * 判断一个字符串是否是回文字符串
 */

/**
 * @param {string} s
 * @return {boolean}
 */
var isPalindrome = function(s) {
    let len = s.length;
    if (len === 0 || len === 1) {
        return true;
    } 
    s = s.replace(/[^a-zA-Z0-9]/g, '');
    len = s.length;
    let mid = parseInt(len / 2, 10);
    let i, j;
    if (len % 2) {
        i = mid - 1;
        j = mid + 1;
    } else {
        i = mid - 1;
        j = mid;
    }
    while (i >= 0 && j < len) {
        if (s[i].toLowerCase() === s[j].toLowerCase()) {
            i--;
            j++;
        } else {
            return false;
        }
    }
    return true;
};