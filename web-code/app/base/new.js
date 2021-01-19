function objectFactory(fn) {
    const o = {};
    o.__proto__ = fn.prototye;
    const res = o.apply(fn, Array.prototype.slice.call(arguments, 1));
    return Object.prototype.toString.call(res) !== '[object Object]' ? o : res;
}

Function.prototype.call2 = function (context) {
    context.fn = this || window;
    const args = [];
    let result;
    for (let i = 1; i < arguments.length; i++) {
        args.push('arguments[' + i + ']');
    }
    if (!args) {
        result = context.fn();
    }
    result = eval('context.fn(' + args + ')');
    delete context.fn;
    return result;
};

Function.prototype.apply2 = function (context, arr) {
    context = context || window;
    context.fn = this;
    const args = [];
    let result;
    for (let i = 1; i < arguments.length; i++) {
        args.push('arr[' + i + ']');
    }
    console.log(args, '' + args);
    if (!args) {
        result = context.fn();
    }
    result = eval('context.fn(' + args + ')');
    delete context.fn;
    return result;
};
