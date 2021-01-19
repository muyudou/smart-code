
nums1 = [1,2,3,0,0,0];
nums2 = [2,5,6];
m = 3;
n = 3;

nums1 = [-1, -1, 0, 0, 0, 0];
m = 4;
nums2 = [-1, 0];
n = 2;

var merge = function(nums1, m, nums2, n) {
    let i = 0;
    let j = 0;
    let p = 0;
    const nums1Copy = nums1.slice(0, m);
    while (i < m && j < n) {
        if (nums1Copy[i] < nums2[j]) {
            nums1[p++] = nums1Copy[i++];
        } else {
            nums1[p++] = nums2[j++];
        }
    }
    while (i < m) {
        nums1[p++] = nums1Copy[i++];
    }
    while (j < n) {
        nums1[p++] = nums2[j++];
    }
};

merge(nums1, m, nums2, n)