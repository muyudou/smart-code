var plusOne = function(digits) {
    let addOne = true;
    for (let i = digits.length - 1; i >= 0; i--) {
        const sum = digits[i] + (addOne ? 1 : 0);
        if (sum > 9) {
            digits[i] = sum % 10;
            addOne = true;
        } else {
            addOne = false;
            digits[i] = sum;
            return digits;
        }
    }
    if (addOne) {
        digits.unshift(1);
    }
    return digits;
};

var arr = [9, 9];
plusOne(arr) // [1, 0, 0]