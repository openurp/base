import {
  createEmsRuntimeConfig,
  isWujieMicroApp,
  loadEmsRuntimeConfigs,
} from '@openurp/sdk-vue'

/** 页面部署路径，对应原先 webapp 的上下文 /base。 */
export const APP_BASE_PATH = import.meta.env.VITE_APP_BASE || import.meta.env.BASE_URL
export const runtimeConfig = createEmsRuntimeConfig({
  appBasePath: APP_BASE_PATH,
  apiBaseUrl: import.meta.env.VITE_API_BASE || '',
  casBaseUrl: import.meta.env.DEV ? import.meta.env.VITE_CAS_BASE || '' : '',
  casAuthLoginUrl: import.meta.env.VITE_CAS_AUTH_LOGIN_URL || '',
  casLoginUrl: import.meta.env.VITE_CAS_LOGIN_URL || '',
  casLogoutUrl: import.meta.env.VITE_CAS_LOGOUT_URL || '',
})
const runtime = runtimeConfig

export async function loadRuntimeConfig() {
  const configs = await loadEmsRuntimeConfigs({
    appConfigPath: '/base/configs.json',
    // 仅独立模式（非 Wujie）加载主题配置；Wujie 下主题由宿主门户同步。
    themeConfigPath: isWujieMicroApp() ? null : '/base/themes.json',
    apiBaseUrl: runtime.apiBaseUrl,
  })
  if (configs.appConfig) runtime.applyConfig(configs.appConfig)
}

export const getLogoUrl = () => runtime.logoUrl
export const getApiUrl = (path: string, sid?: string | null) => runtime.getApiUrl(path, sid)
export const getEmsUrl = (path: string, sid?: string | null) => {
  const base = `${runtime.emsBaseUrl.replace(/\/+$/, '')}/${path.replace(/^\/+/, '')}`
  if (!sid || !runtime.isCrossOriginUrl(base)) return base
  const url = new URL(base)
  url.searchParams.set('URP_SID', sid)
  return url.toString()
}
export const isCrossOriginUrl = (url: string) => runtime.isCrossOriginUrl(url)
export const getCasAuthLoginUrl = () => runtime.getCasAuthLoginUrl()
export const getCasLoginUrl = () => runtime.getCasLoginUrl()
export const getCasLogoutUrl = () => runtime.getCasLogoutUrl()

/** 打开仍由 Scala 渲染的管理页。开发时若设置了 VITE_API_BASE，拼到后端源站。 */
export function backendPage(path: string): string {
  const origin = (import.meta.env.VITE_API_BASE || '').replace(/\/+$/, '')
  const normalized = path.startsWith('/') ? path : `/${path}`
  return `${origin}${normalized}`
}
