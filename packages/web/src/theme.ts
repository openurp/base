import { ref } from 'vue'
import {
  applyDocumentTheme,
  getStoredTheme,
  setStoredThemeAndNotify,
  type UiTheme,
} from '@beangle/bui-vue'

export type AppTheme = UiTheme

const initialTheme: AppTheme = getStoredTheme() || 'light'

export const appTheme = ref<AppTheme>(initialTheme)

applyDocumentTheme(initialTheme)

export function setAppTheme(theme: AppTheme): void {
  setStoredThemeAndNotify(theme)
  if (appTheme.value === theme) {
    applyDocumentTheme(theme)
    return
  }
  appTheme.value = theme
  applyDocumentTheme(theme)
}

/** Apply stored theme (or light) before/at mount. Portal bus/storage sync is via sdk. */
export function setupTheme(): void {
  setAppTheme(getStoredTheme() || 'light')
}
