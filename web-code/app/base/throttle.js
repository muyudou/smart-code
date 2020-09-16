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

