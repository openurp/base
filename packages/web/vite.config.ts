import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv } from 'vite'
import Components from 'unplugin-vue-components/vite'
import { TDesignResolver } from 'unplugin-vue-components/resolvers'

// base：https://vite.dev/config/shared-options.html#base
export default defineConfig(({ mode }) => {
  const packageDir = fileURLToPath(new URL('.', import.meta.url))
  const repoRoot = fileURLToPath(new URL('../..', import.meta.url))
  // Prefer package env over repo root; never depend on process.cwd().
  const env = { ...loadEnv(mode, repoRoot, ''), ...loadEnv(mode, packageDir, '') }
  const apiTarget = env.VITE_API_BASE
  return {
    plugins: [
      vue(),
      Components({
        resolvers: [TDesignResolver({ library: 'vue-next' })],
      }),
    ],
    envDir: packageDir,
    // 页面部署在 /base/，与原先 webapp 上下文一致。
    base: env.VITE_APP_BASE || '/base/',
    resolve: {
      alias: { '@': fileURLToPath(new URL('./src', import.meta.url)) },
      // sdk-vue 自带一份 vue/tdesign 依赖，去重到本包避免出现两份运行时
      dedupe: ['vue', 'vue-router', 'tdesign-vue-next', 'tdesign-icons-vue-next'],
    },
    server: {
      ...(apiTarget
        ? {
            proxy: {
              '/api': { target: apiTarget, changeOrigin: true },
              // starter 的配置、主题、权限、业务场景挂在 /base，不能把整个 /base 代理走，否则开发服务器自己的页面也会被转走。
              '^/base/(configs|themes|profiles|permissions)': { target: apiTarget, changeOrigin: true },
            },
          }
        : {}),
    },
    build: {
      chunkSizeWarningLimit: 1400,
      rollupOptions: {
        output: {
          manualChunks(id) {
            if (!id.includes('node_modules')) return
            if (id.includes('tdesign')) return 'tdesign'
            if (
              id.includes('/vue/') ||
              id.includes('@vue/') ||
              id.includes('vue-router') ||
              id.includes('vue-i18n')
            ) {
              return 'vue-vendor'
            }
            return 'vendor'
          },
        },
      },
    },
  }
})
