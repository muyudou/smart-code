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

Function.prototype.bind2 = function (context, ...outArgs) {
    const fn = this;
    return (...innerArgs) => {
        return fn.apply(context, outArgs.concat(innerArgs));
    };
};

Function.prototype.bind3 = function (context) {
    const fn = this;
    let args = Array.prototype.slice.call(arguments, 1);
    const fNop = function () {};
    const fBound = function () {
        const innerArgs = Array.prototype.slice.call(arguments);
        return fn.apply(this instanceof fBound ? this : context, args.concat(innerArgs));
    };
    fNop = this.prototype;
    fBound.prototype = new fNop();
    return fBound;
};

var value = 2;

var foo = {
    value: 1
};

function bar(name, age) {
    this.habit = 'shopping';
    console.log(this.value);
    console.log(name);
    console.log(age);
}
bindBar = bar.bind(foo);
bindBar();
new bindBar()