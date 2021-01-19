var longestCommonPrefix = function(strs) {
    if (strs.length <= 0) {
        return '';
    }
    let minLen = Infinity;
    let minIndex = -1;
    strs.forEach((item, index) => {
        if (item.length < minLen) {
            minLen = item.length;
            minIndex = index;
        }
    });
    let result = [];
    for (let i = 0; i < minLen; i++) {
        const lastVal = strs[minIndex][i];
        for (let j = 1; j < strs.length; j++) {
            if (lastVal !== strs[j][i]) {
                return result.join('');
            }
        }
        result.push(lastVal);
    }
    console.log(result)
    return result.join('');
};