const debounce = (fn, timeout, immediate) => {
    let timer = null;
    return function () {
        const args = arguments;
        timer && clearTimeout(timer);
        timer = setTimeout(() => {
            timer = null;
            fn.apply(this, args)
        }, timeout);
    };
};

// 有头有尾
const debounce = (fn, timeout, immediate) => {
    let timer = null;
    return function () {
        const args = arguments;
        if (immediate) {
            !timer && fn.apply(this, args);  
        }
        timer && clearTimeout(timer);
        timer = setTimeout(() => {
            timer = null;
            fn.apply(this, args)
        }, timeout);
    };
};

window.onscroll = debounce(() => console.log('scroll'), 1000, true);

// 首次执行
const throttle = (fn, interval) => {
    let lastTime = 0;
    return function () {
        const now = new Date();
        if (now - lastTime > interval) {
            fn.apply(this, Array.from(arguments));
            lastTime = now;
        }
    };
};

// 最后一次执行
const throttle = (fn, interval) => {
    let timer = null;
    return function () {
        if (!timer) {
            timer = setTimeout(() => {
                fn.apply(this, arguments);
                timer = null;
            }, interval);
        }
    }
}

// 有头有尾
const throttle = (fn, wait) => {
    let previous = 0;
    let timer = null;
    return function () {
        let now = new Date();
        let remaining = wait - (now - previous);
        if (remaining <= 0) {
            timer && clearTimeout(timer);
            fn.apply(this, arguments);
            previous = now;
        } else if (!timer) {
            timer = setTimeout(() => {
                fn.apply(this, arguments);
                timer = null;
                previous = now;
            }, remaining)
        }
    }
};

// 加参数版
// leading: true  有头有尾   false： 无头有尾
// trailing：true  有尾 false  无尾
const throttle = (fn, wait, options = {}) => {
    let previous = 0;
    let timer = null;
    return function () {
        let now = new Date();
        let remaining = wait - (now - previous);
        if (remaining <= 0 && options.leading) {
            timer && clearTimeout(timer);
            fn.apply(this, arguments);
            previous = now;
        } else if (!timer && options.trailing) {
            timer = setTimeout(() => {
                fn.apply(this, arguments);
                timer = null;
                previous = now;
            }, remaining)
        }
    }
};

window.onscroll = throttle(() => console.log('scroll'), 1000, {leading: true, trailing: true});

function scroll() {console.log('scroll')};
ds = debounce(scroll, 1000, true);
window.onscroll = ds;

const debounce = function (fn, timeout, immediate) {
    let timer = null;
    const outerArgs = Array.prototype.slice.call(arguments, 2);
    return function () {
        const innerArgs = Array.prototype.slice.call(arguments);
        const args = innerArgs.concat(outerArgs);
        if (immediate && !timer) {
            fn.apply(this, args);
        }
        clearTimeout(timer);
        timer = setTimeout(() => {
            fn.apply(this, args);
            timer = null;
        }, timeout);
    };
};

function scroll() {console.log('scroll')};
ds = throttle(scroll, 100);
window.onscroll = ds;

// 一进来就触发一次，最后没有
const throttle = function (fn, wait) {
    let last = 0;
    return function () {
        const now = +new Date();
        if (now - last > wait) {
            fn.apply(this, arguments);
            last = now;
        }
    }
};

// 一进来没有触发，最后会再触发一次
const throttle2 = function (fn, wait) {
    let timeout;
    let first = true;
    return function () {
        if (timeout) {
            return;
        }
        timeout = setTimeout(() => {
            fn.apply(this, arguments);
            timeout = null;
            first = true;
        }, wait);
    }
}

const throttle3 = function (fn, wait) {
    let last = 0;
    let timeout = null;
    return function () {
        now = +new Date();
        if (now - last > wait) {
            fn.apply(this, arguments);
            last = now;
        } else {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                fn.apply(this, arguments);
            }, wait);
        }
    }
}