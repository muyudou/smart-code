// 问题：给定一个 URL 数组，如何实现接口的继发和并发？

// 继发1
async function loadData() {
    const res1 = await fetch(url1);
    const res2 = await fetch(url2);
    const res3 = await fetch(url3);
}

// 继发2
async function loadData(urls) {
    for (let i = 0; i < urls.length; i++) {
        const response = await fetch(url[i]);
    }
}

const makePromise = url => {
    return new Promise((resolve, reject) => {
        fetch(url)
    });
}

// 并发1
async function loadData(urls) {
    let arr = [];
    for (let i = 0; i < urls.length; i++) {
        arr.push(fetch(url[i]));
    }
    await Promise.all(arr);
}

// 并发2
async function loadData(urls) {
    let result = []
    result = urls.map(url => {
        const response = await fetch(url);
        return response.text();
    });
}

// 题目：红灯三秒亮一次，绿灯一秒亮一次，黄灯2秒亮一次；如何让三个灯不断交替重复亮灯？（用 Promse 实现）
var light = val => console.log(val);

var makePromise = (val, time) => () => new Promise((resolve, reject) => 
    setTimeout(() => {
        light(val);
        resolve();
    }, time));

var red = makePromise('red', 3000);
var green = makePromise('green', 2000);
var yellow = makePromise('yellow', 1000);

var run = () => {
    Promise.resolve()
        .then(() => red())
        .then(() => green())
        .then(() => yellow())
        .then(() => run());
};

// 将一个函数promisify
const promisify = fn => {
    const outArgs = Array.from(arguments);
    return () => {
        const args = [...outArgs, ...arguments];
        args.push((error, data) => {
            if (error) {
                reject(error);
            }
            resolve(data);
        });
        return new Promise((resolve, reject) => {
            fn.apply(this, args);
        });
    }
};

// 并发做异步请求，限制频率
var urls = [
    'https://www.kkkk1000.com/images/getImgData/getImgDatadata.jpg',
    'https://www.kkkk1000.com/images/getImgData/gray.gif',
    'https://www.kkkk1000.com/images/getImgData/Particle.gif',
    'https://www.kkkk1000.com/images/getImgData/arithmetic.png',
    'https://www.kkkk1000.com/images/getImgData/arithmetic2.gif',
    'https://www.kkkk1000.com/images/getImgData/getImgDataError.jpg',
    'https://www.kkkk1000.com/images/getImgData/arithmetic.gif',
    'https://www.kkkk1000.com/images/wxQrCode2.png',
  ];
  
  function loadImg(url) {
    return new Promise((resolve, reject) => {
      const img = new Image();
      img.onload = () => {
        console.log('一张图片加载完成', url);
        resolve();
      };
      img.onerror = reject;
      img.src = url;
    });
  }
  
const limitLoad = (urls, limit) => {
    let promises = [];
    let count = 0;
    while(urls.length) {
        if (count <= limit) {
            promises.push(loadImg(urls[count++]))
            urls.unshift();
        }
        await Promise.all(promises);
        promises = [];
        count = 0;
    }
}