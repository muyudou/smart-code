/**
* @file: index.js
* @author: xulinfeng
* @date:   2020-05-19 09:15:47
*/
const myAssign = (target, ...source) => {
    if (target === null) {
        throw new TypeError('cant convert undefined or null to object');
    }
    return source.reduce((acc, cur) => {
        if (cur === null) {
            return acc;
        }
        Object.keys(cur).forEach(key => {
            acc[key] = cur(key);
        })
        return acc;
    }, target);
};