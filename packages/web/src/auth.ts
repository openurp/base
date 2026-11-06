import { createEmsAuth, type EmsCurrentUser } from '@openurp/sdk-vue'
import type { Ref } from 'vue'
import {
  APP_BASE_PATH,
  getCasAuthLoginUrl,
  getCasLoginUrl,
  getCasLogoutUrl,
  getEmsUrl,
} from '@/config'

const auth = createEmsAuth<EmsCurrentUser>({
  appBasePath: () => APP_BASE_PATH,
  casAuthLoginUrl: getCasAuthLoginUrl,
  casLoginUrl: getCasLoginUrl,
  casLogoutUrl: getCasLogoutUrl,
  logoutUrl: () => getEmsUrl('/cas/auth/logout'),
})
export const currentUser = auth.currentUser as Ref<EmsCurrentUser | null>
export const authReady = auth.authReady as Ref<boolean>
export const sessionService = auth.sessionService
export const initializeAuth = auth.initializeAuth
export function useAuth() {
  return { ...auth.useAuth(), currentUser, authReady }
}
