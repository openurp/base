import { getApiUrl, isCrossOriginUrl } from '@/config'
import { authService } from '@/utils/auth'

export class ApiError extends Error {
  constructor(
    message: string,
    public status?: number,
  ) {
    super(message)
    this.name = 'ApiError'
  }
}

/** Redirect to CAS when the session has expired. */
export function handleUnauthorized(response: Response): void {
  if (response.status !== 401) return
  authService.requireLogin()
  throw new ApiError('请先登录', 401)
}

export function assertApiOk(response: Response, message = '请求失败'): void {
  handleUnauthorized(response)
  if (!response.ok) {
    throw new ApiError(`${message} (${response.status})`, response.status)
  }
}

export async function apiFetch<T>(path: string, params: Record<string, string> = {}): Promise<T> {
  const query = new URLSearchParams(params)
  const queryString = query.toString()
  const baseUrl = getApiUrl(path, authService.getToken())
  const url = `${baseUrl}${queryString ? `${baseUrl.includes('?') ? '&' : '?'}${queryString}` : ''}`
  const headers = isCrossOriginUrl(url) ? { Accept: 'application/json' } : authService.getAuthHeaders()

  const response = await fetch(url, {
    method: 'GET',
    credentials: 'include',
    headers,
    cache: 'no-store',
  })

  assertApiOk(response)
  return response.json() as Promise<T>
}
