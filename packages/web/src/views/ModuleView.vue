<template>
  <section class="page">
    <h2>{{ title }}</h2>
    <p class="hint">{{ t('module.hint') }}</p>
    <ul v-if="module" class="link-list">
      <li v-for="item in module.links" :key="item.href">
        <a :href="item.href">{{ item.label }}</a>
      </li>
    </ul>
  </section>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { useRoute } from 'vue-router'
import { findModule } from '@/modules'

const { t } = useI18n()
const route = useRoute()
const module = computed(() => findModule(route.path))
const title = computed(() => (module.value ? t(module.value.titleKey) : ''))
</script>
