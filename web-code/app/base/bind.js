const mybind = (fn, ...args) => {
    const outerArgs = Array.from(args);
    const context = outerArgs.pop();
    const fBound = function(...innerArgs) {
        const mergeArgs = [...outerArgs, ...innerArgs];
        if (this instanceof fBound) {
            let res = fn.apply(this, mergeArgs);
            if (typeof res === 'object') {
                return res;
            }
            return this;
        } else {
            return fn.apply(context, mergeArgs);
        }
    };
    if (fn.prototype) {
        function F() {}
        F.prototype = fn.prototype;
        fBound.prototype = new F();
    }
    const desc = Object.getOwnPropertyDescriptors(fn);
    Object.defineProperties(fBound, {
        length: desc.length,
        name: Object.assign(desc.name, {
            value: `bound ${desc.name.value}`
        })
    });
    return fBound;
};

const myCall = (context, ...args) => {
    const fn = this;
    let caller = Symbol('caller');
    context[caller] = fn;
    const res = context[caller](...args);
    delete context[caller];
    return res;
};

const myCall2 = (context, ...args) => {
    const fn = this;
    fn(context, ...args);
};