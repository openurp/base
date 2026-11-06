import { describe, expect, it } from 'vitest'
import { resolveProfileId, type AdminProfile } from '@/api/profiles'
import { normalizeLocale } from '@/i18n/locale'

const profiles: AdminProfile[] = [
  { id: '1', name: '本科' },
  { id: '2', name: '研究生' },
]

describe('resolveProfileId', () => {
  it('prefers the profile id from the URL when it exists', () => {
    expect(resolveProfileId(profiles, '1', '2')).toBe('2')
  })

  it('falls back to the api profile, then the first profile', () => {
    expect(resolveProfileId(profiles, '1', 'missing')).toBe('1')
    expect(resolveProfileId(profiles, null, null)).toBe('1')
  })
})

describe('normalizeLocale', () => {
  it('accepts portal locale codes', () => {
    expect(normalizeLocale('zh_CN')).toBe('zh-CN')
    expect(normalizeLocale('en_US')).toBe('en-US')
    expect(normalizeLocale('')).toBeNull()
  })
})
