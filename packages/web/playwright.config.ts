import { defineConfig } from '@playwright/test'

export default defineConfig({
  testDir: './tests/e2e',
  timeout: 30_000,
  fullyParallel: true,
  reporter: [['list'], ['html', { open: 'never' }]],
  use: {
    baseURL: 'http://localhost:4173/base/',
    trace: 'retain-on-failure',
    screenshot: 'only-on-failure',
    expect: { timeout: 15_000 },
  },
  webServer: {
    command: 'pnpm exec vite --host 0.0.0.0 --port 4173',
    url: 'http://localhost:4173/base/',
    reuseExistingServer: false,
    timeout: 60_000,
  },
})
