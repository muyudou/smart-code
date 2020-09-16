// 中序遍历：左 根 右   前序遍历： 根 左 右  后序遍历 左 右 根
var inorder = (root, res) => {
    if (root === null) {
        return res;
    }
    inorder(root.left, res)
    res.push(root.val);
    inorder(root.right, res);
};
var inorderTraversal = function(root) {
    let res = [];
    inorder(root, res);
    return res;
};

// 递归层序遍历
var levelOrderInner = (root, res, level) => {
    if (root === null) {
        return res;
    }
    if (!res[level]) {
        res[level] = [];
    }
    res[level].push(root.val);
    if (root.left) {
        levelOrderInner(root.left, res, level + 1);
    }
    if (root.right) {
        levelOrderInner(root.right, res, level + 1);
    }
};

var levelOrder = function(root) {
    let queue = [];
    levelOrderInner(root, queue, 0);
    return queue;
};

// 非递归层序遍历
var levelOrder2 = root => {
    let queue = [];
    let res = [];
    let level = 0;
    if (root) {
        queue.push(root);
    }
    while(queue.length) {
        let node = queue.shift();
        if (!res[level]) {
            res[level] = [];
        }
        res[level].push(node.val);
        if (node.left || node.right) {
            level++;
        }
        if (node.left) {
            queue.push(node.left);
        }
        if (node.right) {
            queue.push(node.right);
        }
    }
    return res;
}

// 二叉树的最大深度
const maxDepthInner = (root, depth) => {
    if (!root) {
        return depth;
    }
    depth++;
    let leftDepth = maxDepthInner(root.left, depth);
    let rightDepth = maxDepthInner(root.right, depth);
    return leftDepth > rightDepth ? leftDepth : rightDepth;
};

const maxDepth = root => {
    return maxDepthInner(root, 0);
}

// 递归验证二叉搜索树
const isValidBSTInner = (root, lower, higher) => {
    if (!root) {
        return true;
    }
    if (root.val <= lower || root.val >= higher) {
        return false;
    }
    return isValidBSTInner(root.left, lower, root.val) && isValidBSTInner(root.right, root.val, higher);
};
var isValidBST = function(root) {
    return isValidBSTInner(root, -Infinity, Infinity);
};

// 非递归验证二叉搜索树
var isValidBST = root => {
    let stack = [];
    let node = root;
    let prev = -Infinity;
    
    while (stack.length || node !== null) {
        while (node) {
            stack.push(node);
            node = node.left;
        }
        let node = stack.pop();
        if (node.val <= prev) {
            return false;
        }
        prev = node.val;
        if (node.right) {
            stack.push(node);
        }
    }
    return true;
}