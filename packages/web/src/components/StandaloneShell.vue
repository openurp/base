<template>
  <AppShell
    :title="t('app.title')"
    :brand-mark="t('app.brandMark')"
    :logo-url="logoUrl"
    :subtitle="t('app.brandSub')"
    :menus="shellMenus"
    :active-path="active"
    :user-name="currentUser?.name || t('app.user')"
    :nav-section="t('app.navSection')"
    :logout-label="t('app.logout')"
    :profiles="profiles"
    :profile-id="profileId"
    :locale="appLocale"
    :theme-mode="appTheme"
    @logout="onLogout"
    @profile-change="onProfileChange"
    @update:locale="onLocaleChange"
    @update:theme-mode="onThemeChange"
  >
    <router-view />
  </AppShell>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { useRoute } from 'vue-router'
import { AppShell, type AppShellMenuItem } from '@openurp/sdk-vue'
import {
  DashboardIcon,
  BuildingIcon,
  CalendarIcon,
  EducationIcon,
  UserIcon,
  UsergroupIcon,
  HomeIcon,
  SettingIcon,
} from 'tdesign-icons-vue-next'
import { useAuth } from '@/auth'
import { getLogoUrl } from '@/config'
import { profileId, profiles, switchProfile } from '@/context'
import { appLocale, setAppLocale } from '@/i18n'
import { appTheme, setAppTheme } from '@/theme'

const { t } = useI18n()
const route = useRoute()
const { currentUser, handleLogout } = useAuth()
const logoUrl = getLogoUrl()

async function onLogout() {
  await handleLogout()
}

function onProfileChange(id: string | number) {
  switchProfile(id)
}

function onLocaleChange(locale: string) {
  if (locale === 'zh-CN' || locale === 'en-US') setAppLocale(locale)
}

function onThemeChange(mode: 'light' | 'dark') {
  setAppTheme(mode)
}

const menus = [
  { path: '/', labelKey: 'nav.overview', icon: DashboardIcon },
  { path: '/org', labelKey: 'nav.org', icon: BuildingIcon },
  { path: '/calendar', labelKey: 'nav.calendar', icon: CalendarIcon },
  { path: '/edu', labelKey: 'nav.edu', icon: EducationIcon },
  { path: '/hr', labelKey: 'nav.hr', icon: UserIcon },
  { path: '/std', labelKey: 'nav.std', icon: UsergroupIcon },
  { path: '/resource', labelKey: 'nav.resource', icon: HomeIcon },
  { path: '/system', labelKey: 'nav.system', icon: SettingIcon },
]

const shellMenus = computed<AppShellMenuItem[]>(() =>
  menus.map((item) => ({
    path: item.path,
    title: t(item.labelKey),
    icon: item.icon,
  })),
)

const active = computed(() => route.path)
</script>
