<template>
  <div class="cta-container">
    <div class="cta">
      <h2>Gyakran Ismételt Kérdések</h2>
      <div class="faq-item">
        <h3>Mikor vannak nyitvatartási időink?</h3>
        <p>Nyitvatartási időink minden nap 9:00 és 18:00 között vannak.</p>
      </div>
      <div class="faq-item">
        <h3>Hogyan lehet örökbefogadni?</h3>
        <p>Az örökbefogadásra személyesen van lehetőség a menhelyen. Kérjük, látogasson el hozzánk, hogy találkozhasson a kutyákkal és részletes információt kapjon az örökbefogadási folyamatról.</p>
      </div>
      <div class="faq-item">
        <h3>Milyen ételt adhatok a kutyámnak?</h3>
        <p>A kutyák számára ajánlott minőségi tápot válasszunk, amely megfelel az ő koruknak, méretüknek és aktivitási szintjüknek.</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FAQ'
}
</script>

<style scoped lang="postcss">
.cta-container {
  @apply flex justify-center;
}

.cta {
  @apply text-center py-8 px-6 mb-8 bg-blue-500 rounded-xl shadow-lg;
  @apply max-w-screen-md;
}

.cta h2 {
  @apply text-white text-lg mb-4;
}

.faq-item {
  @apply text-white mb-4 relative;
}

.faq-item h3 {
  @apply text-white text-lg mb-2;
}

.faq-item p {
  @apply text-white mb-2;
}

.faq-item::after {
  @apply block h-px w-1/2 bg-white mt-2 mb-4 mx-auto content-[''];
}
</style>
