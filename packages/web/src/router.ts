import { createRouter, createWebHistory } from 'vue-router'
import { adminModules } from '@/modules'

const router = createRouter({
  history: createWebHistory(import.meta.env.VITE_APP_BASE || import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: () => import('@/views/Overview.vue'), meta: { titleKey: 'nav.overview' } },
    ...adminModules.map((item) => ({
      path: item.path,
      component: () => import('@/views/ModuleView.vue'),
      meta: { titleKey: item.titleKey },
    })),
  ],
})

export default router
