import { ref } from 'vue'
import {
  detectPortalFontSize,
  portalFontSizeToCss,
  type PortalFontSize,
} from '@openurp/sdk-vue'

export type AppFontSize = PortalFontSize

const FONT_SIZE_STORAGE_KEY = 'beangle.ui.font-size'
const FONT_SIZE_CHANGE_EVENT = 'beangle.ui.fontsizechange'

const initialSize: AppFontSize = detectPortalFontSize()

export const appFontSize = ref<AppFontSize>(initialSize)

export function applyDocumentFontSize(size: AppFontSize): void {
  if (typeof document === 'undefined') return
  document.documentElement.style.setProperty('font-size', portalFontSizeToCss(size))
}

function persistFontSize(size: AppFontSize): void {
  try {
    if (typeof localStorage === 'undefined') return
    const prev = localStorage.getItem(FONT_SIZE_STORAGE_KEY)
    localStorage.setItem(FONT_SIZE_STORAGE_KEY, size)
    if (prev === size || typeof window === 'undefined') return
    window.dispatchEvent(new CustomEvent(FONT_SIZE_CHANGE_EVENT, { detail: size }))
  } catch {
    // ignore
  }
}

/** Apply font size to document; persist when changed (standalone / local toggle). */
export function setAppFontSize(size: AppFontSize): void {
  persistFontSize(size)
  appFontSize.value = size
  applyDocumentFontSize(size)
}

/** Apply stored / detected font size before mount. Portal sync is via sdk. */
export function setupFontSize(): void {
  setAppFontSize(detectPortalFontSize())
}
