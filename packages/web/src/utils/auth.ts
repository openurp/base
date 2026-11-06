import { sessionService } from '@/auth'

/** Adapter used by API modules so both frontends share one SDK session. */
export const authService = {
  getToken: () => sessionService.getToken(),
  getAuthHeaders: () => sessionService.getAuthHeaders(''),
  requireLogin: (redirectUrl?: string) => sessionService.requireLogin(redirectUrl),
}
