import { createEmsPermissions, type EmsRoutePermission } from '@openurp/sdk-vue'
import { getApiUrl, isCrossOriginUrl } from '@/config'
import { authService } from '@/utils/auth'

/**
 * 权限接口 `GET /base/permissions.json`。
 * 资源前缀按 `/base` 去掉，与原先 webapp 上下文内的功能路径对齐。
 * 请求失败时 fail-open，后端页面仍然做最终访问控制。
 */
export interface RoutePermission extends EmsRoutePermission {}

const permissions = createEmsPermissions({
  permissionsPath: '/base/permissions.json',
  resourcePrefix: '/base',
  routeAliases: [],
  grantAll: false,
  getApiUrl,
  isCrossOriginUrl,
  getToken: () => authService.getToken(),
  getAuthHeaders: () => authService.getAuthHeaders(),
  requireLogin: (redirectUrl) => authService.requireLogin(redirectUrl),
})

export const canRoute = permissions.canRoute
export const can = permissions.can
export const initializePermissions = permissions.initialize
export const ensurePermissions = permissions.ensureLoaded
