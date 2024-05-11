<template>
  <nav>
    <h1 class="title">Vau-Vau Alapítvány</h1>
    <ul>
      <li><router-link to="/" class="nav-link" active-class="active-link">Főoldal</router-link></li>
      <li><router-link to="/dogs" class="nav-link" active-class="active-link">Kutyák</router-link></li>
      <li><router-link to="/contact" class="nav-link" active-class="active-link">Elérhetőség</router-link></li>
      <li><router-link to="/faq" class="nav-link" active-class="active-link">Gyakori kérdések</router-link></li>
      <li v-if="!token">
        <router-link to="/login" class="nav-link login-link" active-class="active-link-login">Bejelentkezés</router-link>
      </li>
      <li v-else>
        <router-link to="#" @click.prevent="logout" class="nav-link logout-link login-link">Kijelentkezés</router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'Header',
  computed: {
    ...mapState(['token']),
  },
  methods: {
    logout() {
      if (window.confirm('Biztosan ki szeretnél jelentkezni?')) {
        this.$store.commit('clearToken');
        this.$router.push('/login');
      }
    },
  },
};
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

.logout-link {
  @apply bg-red-500 hover:bg-red-600 active:bg-red-700;
}

.active-link {
  @apply bg-orange-700;
}

.active-link-login {
  @apply bg-green-700;
}

.active-link:hover {
  @apply bg-orange-700;
}
</style>