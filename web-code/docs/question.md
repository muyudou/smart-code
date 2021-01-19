1. 网络：http1.1 http2.0 区别：多路复用、头部压缩、二进制传输， https区别
2. 浏览器缓存
3. cookie 跨域
4. web安全：csrf和xss
5. 跨域方案: jsonp, CORS, 代理，postMessage，document.domain
6. jsonp实现原理
7. 性能优化
    + 减少http请求和资源大小：
        + 内容合并：公共库合并，打包开启tree-shaking，压缩：HTML压缩、CSS、JS压缩，图片压缩
        + 图片使用雪碧图，base64，字体图标代替图片，webp图片
        + 利用浏览器缓存
        + 不使用 css @import
        + 避免使用空的src和href
    + 优化网络链接
        + 静态资源使用cdn
        + DNS预解析：dns-prefecth
        + keep-alive，http2
    + 资源加载优化
        + css放在head，js放在最后
        + 图片懒加载
        + 异步加载js：async defer
        + 模块按需加载
        + 预加载，预渲染
        + 服务端渲染
    + 减少重绘和回流
        + css能实现的使用css实现
        + 批量更新css，class
        + dom离线操作
        + 防抖和节流
        + 动画元素可以设置position：absolute成为独立层
        + 开启硬件加速：translate3d，rotate3d，scale3d， translate3d(0, 0, 0);
8. 性能指标：FP，FCP，FMP，TTI
    + https://smartprogram.baidu.com/docs/develop/performance/performance_measure/

9. webpack原理
10. rollup
    + https://zhuanlan.zhihu.com/p/75717476
11. 事件队列
12. 实现一个请求并发的类 Promise.all
13. 非波拉契队列
14. 节流防抖，还要实现isleading