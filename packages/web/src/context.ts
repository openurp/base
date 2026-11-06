import { computed, ref } from 'vue'
import { isWujieMicroApp } from '@openurp/sdk-vue'
import {
  clearContextLocalStorage,
  getProfiles,
  readContextProfileIdFromUrl,
  resolveProfileId,
  type AdminProfile,
} from '@/api/profiles'

export const ready = ref(false)
export const contextError = ref('')
export const profiles = ref<AdminProfile[]>([])
export const profileId = ref('')
export const currentProfile = computed(() => profiles.value.find((x) => x.id === profileId.value))

/** Post-login init. Profiles power the standalone AppShell switcher. */
export async function initializeContext() {
  try {
    if (isWujieMicroApp()) return
    const payload = await getProfiles()
    profiles.value = payload.profiles
    profileId.value = resolveProfileId(
      payload.profiles,
      payload.profileId,
      readContextProfileIdFromUrl(),
    )
  } catch (e) {
    contextError.value = e instanceof Error ? e.message : '初始化失败'
  } finally {
    ready.value = true
  }
}

/** Switch business profile: clear EMS context cache, reload with portal-compatible query. */
export function switchProfile(id: string | number) {
  const nextId = String(id)
  if (!nextId || nextId === profileId.value) return
  clearContextLocalStorage()
  const url = new URL(location.href)
  url.searchParams.set('contextProfileId', nextId)
  location.assign(url.toString())
}
