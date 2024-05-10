<template>
  <section class="hero">
    <img class="hero-image"
         src="https://images.pexels.com/photos/356378/pexels-photo-356378.jpeg?auto=compress&cs=tinysrgb&h=350"
         alt="Hero Image">
    <h1>Üdv a Vau-Vau Alapítvány weboldalán!</h1>
    <p>Találd meg a tökéletes háziállatod még ma!</p>
  </section>
</template>

<style scoped lang="postcss">
.hero {
  @apply bg-cover bg-center py-20 text-center flex flex-col items-center justify-center;
}

.hero-image {
  @apply my-4;
}
</style>