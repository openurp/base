<template>
  <section class="page">
    <h2>{{ t('nav.overview') }}</h2>
    <p class="lead">{{ t('overview.lead') }}</p>
    <div class="quick-grid">
      <router-link v-for="item in adminModules" :key="item.path" :to="item.path" class="quick-card">
        <b>{{ t(item.titleKey) }}</b>
        <p>{{ item.links.length }}</p>
      </router-link>
    </div>
  </section>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n'
import { adminModules } from '@/modules'

const { t } = useI18n()
</script>
