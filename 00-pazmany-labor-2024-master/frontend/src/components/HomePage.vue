<template>
  <div class="home-page">
    <HeroSection />
    <CallToAction />
  </div>
</template>

<script>
import HeroSection from './HeroSection.vue'
import CallToAction from './CallToAction.vue'

export default {
  name: 'HomePage',
  components: {
    HeroSection,
    CallToAction
  }
}
</script>

<style scoped lang="postcss">
.home-page {
  @apply flex flex-col items-center justify-center;
}
</style>