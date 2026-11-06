import { ref } from 'vue'
import { getStoredLocale } from '@beangle/bui-vue'

export type AppLocale = 'zh-CN' | 'en-US'

/**
 * Reactive locale used by pickLocalized / isEnglishLocale.
 * Kept in sync with vue-i18n via setAppLocale (i18n/index.ts).
 */
export const currentLocale = ref<AppLocale>('zh-CN')

declare global {
  interface Window {
    $wujie?: {
      props?: Record<string, unknown>
      bus?: {
        $on?: (event: string, handler: (...args: unknown[]) => void) => void
        $off?: (event: string, handler: (...args: unknown[]) => void) => void
      }
    }
  }
}

const ENGLISH = new Set(['en', 'en-us', 'en_us', 'en-US', 'en_US'])
const CHINESE = new Set(['zh', 'zh-cn', 'zh_cn', 'zh-CN', 'zh_CN', 'zh-hans', 'zh_hans'])

/** Portal uses request_locale=zh_CN|en_US; normalize to app locales. */
export function normalizeLocale(raw: unknown): AppLocale | null {
  if (raw == null) return null
  const value = String(raw).trim()
  if (!value) return null
  const lower = value.toLowerCase().replace(/_/g, '-')
  if (ENGLISH.has(value) || ENGLISH.has(lower) || lower.startsWith('en')) return 'en-US'
  if (CHINESE.has(value) || CHINESE.has(lower) || lower.startsWith('zh')) return 'zh-CN'
  return null
}

function readCookie(name: string): string | null {
  if (typeof document === 'undefined') return null
  const match = document.cookie.match(new RegExp(`(?:^|; )${name}=([^;]*)`))
  return match ? decodeURIComponent(match[1]) : null
}

function readQueryLocale(): unknown {
  if (typeof window === 'undefined') return null
  const params = new URLSearchParams(window.location.search)
  return params.get('request_locale') || params.get('locale') || params.get('lang') || null
}

function readWujieLocale(): unknown {
  const props = window.$wujie?.props
  if (!props) return null
  return props.locale ?? props.lang ?? props.request_locale ?? null
}

function readStorageLocale(): unknown {
  const fromBeangle = getStoredLocale()
  if (fromBeangle) return fromBeangle

  try {
    if (typeof localStorage !== 'undefined') {
      const fromStorage = localStorage.getItem('locale') || localStorage.getItem('lang')
      if (fromStorage) return fromStorage
    }
  } catch {
    // ignore
  }
  return readCookie('locale') || readCookie('lang')
}

/** Resolve portal language: wujie props → URL → cookie/storage → zh-CN. */
export function detectPortalLocale(): AppLocale {
  return (
    normalizeLocale(readWujieLocale()) ||
    normalizeLocale(readQueryLocale()) ||
    normalizeLocale(readStorageLocale()) ||
    'zh-CN'
  )
}

export function syncCurrentLocale(locale: AppLocale): void {
  currentLocale.value = locale
}

export function isEnglishLocale(locale: AppLocale = currentLocale.value): boolean {
  return locale === 'en-US'
}

export function applyDocumentLocale(locale: AppLocale): void {
  if (typeof document === 'undefined') return
  document.documentElement.lang = locale === 'en-US' ? 'en' : 'zh-CN'
}

export function pickLocalized(
  name?: string | null,
  enName?: string | null,
  locale?: AppLocale,
): string {
  const active = locale ?? currentLocale.value
  const zh = name == null ? '' : String(name).trim()
  const en = enName == null ? '' : String(enName).trim()
  if (isEnglishLocale(active)) return en || zh
  return zh || en
}
