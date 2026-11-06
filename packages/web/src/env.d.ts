/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_APP_BASE?: string
  readonly VITE_API_BASE?: string
  readonly VITE_CAS_BASE?: string
  readonly VITE_CAS_AUTH_LOGIN_URL?: string
  readonly VITE_CAS_LOGIN_URL?: string
  readonly VITE_CAS_LOGOUT_URL?: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
