// 对于一个给定的序列 a = [a1, a2, a3, … , an]，请设计一个算法，用于输出这个序列的全部排列方式。

let f = arr => {
    const result = [];
    fullArray(arr, arr.length, result, 0);
    console.log(result);
};
let swap = (arr, index1, index2) => {
    const val = arr[index1];
    arr[index1] = arr[index2];
    arr[index2] = val;
};
let fullArray = (arr, len, result, first) => {
    if (first === len) {
        result.push(arr);
        return;
    }
    for (let i = 0; i < len; i++) {
        swap(arr, i, first);
        fullArray(arr, len, result, first + 1);
        swap(arr, i, first);
    }
};
var arr = [1, 2, 3];
f(arr);