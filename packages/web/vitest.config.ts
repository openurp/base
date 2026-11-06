import { fileURLToPath, URL } from 'node:url'
import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vitest/config'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: { '@': fileURLToPath(new URL('./src', import.meta.url)) },
  },
  test: {
    environment: 'jsdom',
    include: ['tests/unit/**/*.spec.ts'],
    server: {
      deps: {
        inline: [
          '@openurp/sdk-vue',
          '@beangle/ems-app',
          '@beangle/bui-vue',
          'tdesign-vue-next',
          'tdesign-icons-vue-next',
        ],
      },
    },
  },
})
