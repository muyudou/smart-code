var moveZeroes = function(nums) {
    const len = nums.length;
    if (len < 2) {
        return nums;
    }
    let index = 0;
    for (let i = 0; i < len; i++) {
        if (nums[i]) {
            nums[index++] = nums[i];
        }
    }
    for (let i = index; i < len; i++) {
        nums[i] = 0;
    }
    return nums;
};