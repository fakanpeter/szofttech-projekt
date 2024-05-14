<template>
  <section class="hero">
    <img class="hero-image"
         :src="heroImage"
         alt="Hero Image">
    <h1>Üdv a Vau-Vau Alapítvány weboldalán!</h1>
    <p>Találd meg a tökéletes háziállatod még ma!</p>
  </section>
</template>

<script>
import heroImage from '@/assets/hero_kutya.jpeg'

export default {
  data() {
    return {
      heroImage
    }
  }
}
</script>

<style scoped lang="postcss">
.hero {
  @apply bg-cover bg-center py-20 text-center flex flex-col items-center justify-center;
}

.hero-image {
  @apply my-4;
}
</style>