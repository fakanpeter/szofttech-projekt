<template>
  <div class="cta">
    <h2>Készen állsz hogy megtaláld a tökéletes kutyát számodra?</h2>
    <router-link to="/dogs" class="cta-button">Összes kutya megtekintése</router-link>
  </div>
</template>

<script>
export default {
  name: 'CallToAction'
}
</script>

<style scoped lang="postcss">
.cta {
  @apply text-center py-8 px-6 mb-8 bg-blue-500 rounded-xl shadow-lg;
}

.cta h2 {
  @apply text-white text-lg mb-4;
}

.cta-button {
  @apply inline-block px-8 py-4 text-lg font-bold text-white bg-orange-500 rounded-lg shadow-lg hover:bg-orange-600 active:bg-orange-700;
}
</style>
