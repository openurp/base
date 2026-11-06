<template>
  <t-config-provider :global-config="tdesignGlobalConfig">
    <component :is="isWujie ? MicroShell : StandaloneShell" />
  </t-config-provider>
</template>
<script setup lang="ts">
import { ConfigProvider as TConfigProvider } from 'tdesign-vue-next'
import { isWujieMicroApp, MicroShell } from '@openurp/sdk-vue'
import StandaloneShell from '@/components/StandaloneShell.vue'
import { tdesignGlobalConfig } from '@/i18n'

const isWujie = isWujieMicroApp()
</script>
