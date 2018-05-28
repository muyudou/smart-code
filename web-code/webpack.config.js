let HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    devtool: 'eval-source-map',
    entry: __dirname + '/app/main.js',
    output: {
        path: __dirname + '/dist/',
        filename: 'bundle.js'
    },
    devServer: {
        contentBase: __dirname + '/dist/',
        compress: true,
        port: 9000,
        inline: true,
        historyApiFallback: true,
        open: true //是否运行成功后直接打开页面
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['env'],
                        plugins: [
                            'transform-class-properties',
                            ['transform-object-rest-spread', {useBuiltIns: true}],
                            'transform-decorators-legacy'
                        ]
                    }
                },
                exclude: __dirname + 'node_modules',
                include: __dirname + 'src'
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: __dirname + "/public/index.html",
            filename: __dirname + "/dist/index.html",
            inject:true,    //允许插件修改哪些内容，包括head与body
            hash:false,    //为静态资源生成hash值
            minify:{    //压缩HTML文件
                removeComments:true,    //移除HTML中的注释
                collapseWhitespace:false    //删除空白符与换行符
            }
        })
    ],
}