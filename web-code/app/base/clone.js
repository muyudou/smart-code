// 深拷贝
const deepClone = (o, hash = new WeakMap()) => {
    const Constructor = o.constructor;
    switch(Constructor) {
        case RegExp:
            return new Constructor(o);
        case Date:
            return new Constructor(o.getTime());
        default:
            if (hash.has(o)) {
                return hash.get(o);
            }
            let result = Object.prototype.toString.call(o) === "[object Object]" ? {} : [];
            Object.keys(o).forEach(key => {
                if (typeof o[key] === 'object') {
                    result[key] = deepClone(o[key], hash);
                } else {
                    result[key] = o[key];
                }  
            });
            hash.set(o, result);
            return result;
    }
    
};

const jsonDeepClone = o => JSON.parse(JSON.stringify(o));

// 非递归算法
const deepClone2 = (o, hash = new WeakMap()) => {
    const Constructor = o.constructor;
    switch(Constructor) {
        case RegExp:
            return new Constructor(o);
        case Date:
            return new Constructor(o.getTime());
        default:
            const root = {};
            let stack = [{
                parent: root,
                key: undefined,
                data: o
            }];
            while(stack.length) {
                let obj = stack.pop();
                const parent = node.parent;
                const key = node.key;
                const data = node.data;
                let res = parent;
                if (typeof key !== 'undefined') {
                    res = parent[key] = {}
                }
                if (hash.has(o)) {
                    parent[key] = hash.get(o);
                    continue;
                }
                hash.set(obj, res);
                Object.keys(obj).forEach(key => {
                    if (typeof obj[key] === 'object') {
                        stack.push({
                            key: key,
                            data: obj[key]
                        });
                    } else {
                        res[key] = obj[key];
                    }  
                });
            }
            return root;
    }
};
obj = {a: 1, b: 2, c: [2, 3], d: {a:1, b:1}}
deepClone2(obj);

const isEqual = (obj1, obj2) => {
    
};