<template>
  <nav>
    <router-link to="/" class="title">Vau-Vau Alapítvány</router-link>
    <ul>
      <li v-for="(item, index) in menuItems" :key="index">
        <router-link :to="item.route" class="nav-link" active-class="active-link">{{ item.name }}</router-link>
      </li>
      <li v-if="!token">
        <router-link to="/login" class="nav-link login-link" active-class="active-link-login">Bejelentkezés
        </router-link>
      </li>
      <li v-else>
        <router-link to="#" @click.prevent="logout" class="nav-link logout-link login-link">Kijelentkezés</router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
import {mapState} from 'vuex';

export default {
  name: 'Header',
  computed: {
    ...mapState(['token']),
  },
  data() {
    return {
      menuItems: [
        {name: 'Főoldal', route: '/'},
        {name: 'Kutyák', route: '/dogs'},
        {name: 'Elérhetőség', route: '/contact'},
        {name: 'Gyakori kérdések', route: '/faq'},
      ]
    };
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
  @apply mb-4 sm:mb-0 flex flex-col sm:flex-row justify-between items-center p-4 bg-blue-500 text-white;
}

.nav-link {
  @apply inline-block px-4 py-2 text-white bg-orange-500 rounded-lg hover:bg-orange-600 active:bg-orange-700;
}

nav {
  @apply p-4 bg-blue-500 text-white;
}

nav ul {
  @apply flex flex-col sm:flex-row;
}

nav a {
  @apply mr-4 mb-2 sm:mb-0;
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

@media (min-width: 768px) {
  .title {
    @apply mb-4 text-center sm:mb-0 sm:text-left;
  }

  .nav-link {
    @apply inline-block px-4 py-2 text-white bg-orange-500 rounded-lg hover:bg-orange-600 active:bg-orange-700;
  }

  nav {
    @apply p-4 bg-blue-500 text-white flex flex-wrap items-center justify-between;
  }

  nav ul {
    @apply flex flex-wrap items-center justify-center sm:justify-start;
  }

  nav a {
    @apply mr-4 mb-2;
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
}
</style>
