const swap = (arr, i, j) => {
    const tmp = arr[j];
    arr[j] = arr[i];
    arr[i] = tmp;
}

const bubleSort = arr => {
    let j = arr.length;
    while (j > 1) {
        for (let i = 0; i < j; i++) {
            if (arr[i] > arr[i + 1]) {
                swap(arr, i, i + 1);
            }
        }
        j--;
    }
    return arr;
};

bubleSort([3, 2, 1]);

// const quickSort = (arr, left, right) => {
//     const pivot = arr[0];
//     const mid = (right - left) / 2;
//     let i = left;
//     let j = right;
//     swap(arr, 0, mid);
//     while (i < j) {
//         while (arr[i++] < pivot && i < mid) {
//             ;
//         }
//         while(arr[j--] > pivot && j > mid) {
//             ;
//         }
//         swap(arr, i, j);
//     }
//     quickSort(arr, left, mid - 1);
//     quickSort(arr, mid + 1, right);
// };