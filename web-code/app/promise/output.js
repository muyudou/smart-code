const promise = new Promise((resolve, reject) => {
    console.log(1)
    resolve()
    console.log(2)
})
promise.then(() => {
    console.log(3)
})
console.log(4)

let a;
const b = new Promise((resolve, reject) => {
  console.log('promise1');   
  resolve();
}).then(() => {
  console.log('promise2');
}).then(() => {
  console.log('promise3');
}).then(() => {
  console.log('promise4');
});

a = new Promise(async (resolve, reject) => {
  console.log(a);
  await b;
  console.log(a);
  console.log('after1');
  await a
  resolve(true);
  console.log('after2');
});

console.log('end');


// promise1  promising... end  promise2  promise3  promise4 promising  after1


const promise = new Promise((resolve, reject) => {
    resolve('success1');
    reject('error');
    resolve('success2');
});

promise
.then((res) => {
    console.log('then: ', res);
})
.catch((err) => {
    console.log('catch: ', err);
});

const order = promises => {
    return promises.reduce((acc, cur) => {
        return acc.then(() => cur());
    }, Promise.resolve());
};
