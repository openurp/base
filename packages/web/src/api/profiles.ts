import { getApiUrl } from '@/config'
import { authService } from '@/utils/auth'
import { assertApiOk } from './client'

export interface AdminProfile {
  id: string
  name: string
  properties?: Record<string, unknown>
}

export interface ProfilesPayload {
  profileId: string
  profiles: AdminProfile[]
}

const PROFILES_PATH = '/base/profiles.json'

interface RawProfile {
  id?: string | number
  name?: string
  properties?: Record<string, unknown>
}

const parseProfiles = (payload: unknown): ProfilesPayload => {
  const record = payload && typeof payload === 'object' ? (payload as Record<string, unknown>) : {}
  const rawList = Array.isArray(record.profiles) ? (record.profiles as RawProfile[]) : []
  const profiles = rawList
    .filter((item) => item != null && item.id != null)
    .map((item) => ({
      id: String(item.id),
      name: item.name || '',
      properties: item.properties && typeof item.properties === 'object' ? item.properties : {},
    }))
  const profileId =
    record.profileId != null && String(record.profileId).trim() !== ''
      ? String(record.profileId)
      : profiles[0]?.id || ''
  return { profileId, profiles }
}

export async function getProfiles(): Promise<ProfilesPayload> {
  const response = await fetch(getApiUrl(PROFILES_PATH, authService.getToken()), {
    method: 'GET',
    credentials: 'include',
    headers: { Accept: 'application/json' },
    cache: 'no-store',
  })
  assertApiOk(response, '业务场景请求失败')
  return parseProfiles(await response.json())
}

/** Read portal-compatible `contextProfileId` from the current URL. */
export function readContextProfileIdFromUrl(): string | null {
  if (typeof location === 'undefined' || !location.search) return null
  try {
    const value = new URLSearchParams(location.search).get('contextProfileId')
    if (value != null && value.trim() !== '') return value.trim()
  } catch {
    /* ignore */
  }
  return null
}

/** localStorage prefix cleared when switching profile (same as ems-shell). */
export const EMS_CONTEXT_STORAGE_PREFIX = 'beangle.ems.context.'

export function clearContextLocalStorage(): void {
  if (typeof localStorage === 'undefined') return
  try {
    const keys: string[] = []
    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i)
      if (key != null && key.startsWith(EMS_CONTEXT_STORAGE_PREFIX)) keys.push(key)
    }
    for (const key of keys) localStorage.removeItem(key)
  } catch {
    /* ignore */
  }
}

/**
 * Resolve the active profile id: URL `contextProfileId` → API `profileId` → first profile.
 */
export function resolveProfileId(
  profiles: AdminProfile[],
  apiProfileId?: string | null,
  urlProfileId?: string | null,
): string {
  const candidates = [urlProfileId, apiProfileId].filter(
    (id): id is string => id != null && String(id).trim() !== '' && String(id) !== 'null',
  )
  for (const candidate of candidates) {
    if (profiles.some((p) => p.id === String(candidate))) return String(candidate)
  }
  return profiles[0]?.id || ''
}
