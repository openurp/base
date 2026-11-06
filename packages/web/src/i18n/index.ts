import { computed, ref, watch } from 'vue'
import { createI18n } from 'vue-i18n'
import type { App } from 'vue'
import type { GlobalConfigProvider } from 'tdesign-vue-next'
import { setStoredLocaleAndNotify } from '@beangle/bui-vue'
import enConfig from 'tdesign-vue-next/es/locale/en_US'
import zhConfig from 'tdesign-vue-next/es/locale/zh_CN'
import {
  applyDocumentLocale,
  detectPortalLocale,
  normalizeLocale,
  syncCurrentLocale,
  type AppLocale,
} from './locale'
import enUS from './messages/en-US'
import zhCN from './messages/zh-CN'

export type { AppLocale }
export { currentLocale, detectPortalLocale, isEnglishLocale, normalizeLocale, pickLocalized } from './locale'

const messages = {
  'zh-CN': zhCN,
  'en-US': enUS,
}

const initialLocale = detectPortalLocale()
syncCurrentLocale(initialLocale)

export const i18n = createI18n({
  legacy: false,
  locale: initialLocale,
  fallbackLocale: 'zh-CN',
  messages,
})

export const appLocale = ref<AppLocale>(initialLocale)

export const tdesignGlobalConfig = computed<GlobalConfigProvider>(
  () => (appLocale.value === 'en-US' ? enConfig : zhConfig) as unknown as GlobalConfigProvider,
)

export function setAppLocale(locale: AppLocale): void {
  const changed = appLocale.value !== locale
  setStoredLocaleAndNotify(locale)
  appLocale.value = locale
  syncCurrentLocale(locale)
  if (i18n.global.locale.value !== locale) {
    i18n.global.locale.value = locale
  }
  applyDocumentLocale(locale)
  if (changed && typeof document !== 'undefined') {
    document.title = String(i18n.global.t('app.title'))
  }
}

function bindUrlLocaleEvents(): void {
  if (typeof window === 'undefined') return
  window.addEventListener('popstate', () => {
    const next = normalizeLocale(
      new URLSearchParams(window.location.search).get('request_locale') ||
        new URLSearchParams(window.location.search).get('locale') ||
        new URLSearchParams(window.location.search).get('lang'),
    )
    if (next) setAppLocale(next)
  })
}

export function setupI18n(app: App): void {
  applyDocumentLocale(appLocale.value)
  app.use(i18n)
  setAppLocale(detectPortalLocale())
  bindUrlLocaleEvents()
  watch(appLocale, (locale) => {
    syncCurrentLocale(locale)
    if (i18n.global.locale.value !== locale) {
      i18n.global.locale.value = locale
    }
    applyDocumentLocale(locale)
  })
}
