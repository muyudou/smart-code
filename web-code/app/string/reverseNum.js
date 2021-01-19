/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转
 * @param {*} num 
 * 123 -> 321  -123 -> -321 120 -> 21
 */
var reverse = num => {
    let index = 0;
    let flag = '';
    let strNum = ('' + num).split('');
    if (strNum[0] === '+' || strNum[0] === '-') {
        index++;
        flag = strNum[0];
    }
    for (let i = index, j = strNum.length - 1; i < j; i++, j--) {
        let tmp = strNum[j];
        strNum[j] = strNum[i];
        strNum[i] = tmp;
    }
    num = Number(strNum.join(''));
    return num;
};

var reverse = num => {
    let sum = 0;
    let MAX = Math.pow(2, 31) - 1;
    let MIN = Math.pow(2, -31);
    while(num) {
        yushu = num % 10;
        num = parseInt(num / 10, 10);
        sum = sum * 10 + yushu;
        if (sum > MAX || sum < MIN) {
            return 0;
        }
    }
    
    return sum;
};

reverse(123);
reverse(-123);
reverse(120);
reverse(-2147483648);

var reverse = num => {
    let sum = 0;
    while(num) {
        num = num / 10;
        yushu = num % 10;
        sum = sum * 10 + yushu;
    }
    return num < 0 ? -sum : sum;
};