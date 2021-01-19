// f(n) = fn(n - 1) + f(n - 2);

const fibs = n => {
    if (n < 3) {
        return n;
    }
    return f(n - 1) + f(n - 2);
}

const fibInner = (n, last1, last2) => {
    if (n < 3) {
        return n;
    }
    return fibInner(n - 1, last2, last1 + last2);
}

const fibs = n => {
    return fibInner(n, 1, 2);
}

const fibs = n => {
    if (n < 3) {
        return n;
    }
    let number1 = 1;
    let number2 = 2;
    let target = number2;
    for (let i = 3; i <= n; i++) {
        target = number1 + number2;
        number1 = number2;
        number2 = target;
    }
    return target;
}