const CompressionPlugin = require('compression-webpack-plugin');
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        host: '0.0.0.0',
        port: 8080,
        proxy: {
            'zhou': {
                target: 'http://127.0.0.1:8081',
                ws: true,
                changeOrigin: true,//是否开启跨域
            }
        }
    },
    productionSourceMap: false,
    configureWebpack: {
        plugins: [
            new CompressionPlugin({
                test: /\.js$|\.html$|\.css/,
                threshold: 10240
            })
        ]
    }
})