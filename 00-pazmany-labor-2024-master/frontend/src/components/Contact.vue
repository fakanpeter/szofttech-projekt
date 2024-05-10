<template>
  <div class="cta">
    <h2>Kapcsolat</h2>
    <p>Ha kérdése vagy észrevétele van, ne habozzon felvenni velünk a kapcsolatot.</p>
    <div class="contact-info">
      <p>Cím: 1234 Kutya utca, Kutyaváros</p>
      <p>Telefon: +36 1 234 5678</p>
      <p>Email: info@vauvau.hu</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Contacts'
}
</script>

<style scoped lang="postcss">
.cta {
  @apply text-center py-8 px-6 mb-8 bg-blue-500 rounded-xl shadow-lg;
}

.cta h2 {
  @apply text-white text-lg mb-4;
}

.cta p {
  @apply text-white mb-2;
}

.contact-info p {
  @apply text-white mb-2;
}
</style>
