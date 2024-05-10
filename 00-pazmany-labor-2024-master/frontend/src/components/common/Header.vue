<template>
  <nav>
    <h1 class="title">Vau-Vau Alapítvány</h1>
    <ul>
      <li><router-link to="/" class="nav-link">Főoldal</router-link></li>
      <li><router-link to="/dogs" class="nav-link">Kutyák</router-link></li>
      <li><router-link to="/contact" class="nav-link">Elérhetőség</router-link></li>
      <li><router-link to="/faq" class="nav-link">Gyakori kérdések</router-link></li>
      <li><router-link to="/login" class="nav-link login-link">Bejelentkezés</router-link></li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'Header'
}
</script>

<style scoped lang="postcss">
.title {
  @apply mb-4;
}
.nav-link {
  @apply inline-block px-4 py-2 text-white bg-orange-500 rounded-lg hover:bg-orange-600 active:bg-orange-700;
}

nav {
  @apply flex justify-between items-center p-4 bg-blue-500 text-white;
}

nav a {
  @apply mr-4;
}

.login-link {
  @apply bg-green-500 hover:bg-green-600 active:bg-green-700;
}
</style>