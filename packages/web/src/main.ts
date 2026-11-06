import { createApp } from 'vue'
import 'tdesign-vue-next/es/style/index.css'
// @openurp/sdk-vue 的组件样式（SemesterPicker/AppShell 等）打包在 dist/index.css，
// 需显式引入（0.0.2 起 package.json exports 已声明该入口）。
import '@openurp/sdk-vue/dist/index.css'
import '@beangle/bui-vue/styles/theme.css'
import '@beangle/bui-vue/styles/table.css'
import { bindPortalUiSync, registerWujieApp } from '@openurp/sdk-vue'
import { initializeAuth } from '@/auth'
import { loadRuntimeConfig } from '@/config'
import { initializeContext } from '@/context'
import { initializePermissions } from '@/permissions'
import { setAppFontSize, setupFontSize } from '@/font'
import { setAppLocale, setupI18n } from '@/i18n'
import { setAppTheme, setupTheme } from '@/theme'
import App from './App.vue'
import router from './router'
import './styles.css'

let app: ReturnType<typeof createApp> | null = null
let unbindPortalUi: (() => void) | null = null

async function mount() {
  if (app) return
  await loadRuntimeConfig()
  if (!(await initializeAuth())) return
  await Promise.all([initializeContext(), initializePermissions()])
  setupTheme()
  setupFontSize()
  app = createApp(App)
  setupI18n(app)
  unbindPortalUi = bindPortalUiSync({
    onLocale: (locale) => setAppLocale(locale),
    onThemeMode: (mode) => setAppTheme(mode),
    onFontSize: (size) => setAppFontSize(size),
    applyInitial: false,
  })
  app.use(router)
  app.mount('#app')
}

function unmount() {
  unbindPortalUi?.()
  unbindPortalUi = null
  app?.unmount()
  app = null
}

registerWujieApp({ mount, unmount })
