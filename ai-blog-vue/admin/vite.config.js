import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueDevTools(),
    ],
    resolve: {
        alias: { // 配置别名
            '@': path.resolve(__dirname, './src')
        }
    },
    server: {
        open: true,
        // host: 'http://127.0.0.1:8080',
        port: 9000,
        hmr: { overlay: false },
        proxy: {
            '/api': {
                target: 'http://127.0.0.1:8080',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            }
        },
    },
// process is not defined
    define: {
        'process.env': {
            VUE_APP_BASE_API: '127.0.0.1:8080'
        }
    }
})
