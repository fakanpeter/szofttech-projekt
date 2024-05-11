<template>
  <div id="app">
    <Header />
    <router-view class="main-content" />
    <Footer />
  </div>
</template>

<script>
import Header from './components/common/Header.vue'
import Footer from './components/common/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    Footer
  },
  created() {
    const meta = document.createElement('meta');
    meta.name = 'viewport';
    meta.content = 'width=device-width, initial-scale=1.0';
    document.head.appendChild(meta);
  },
}
</script>

<style scoped lang="postcss">
#app {
  @apply min-h-screen flex flex-col;
}

.main-content {
  @apply flex-grow;
}
</style>