<template>
  <div class="login-container">
    <h1 class="title">Regisztráció</h1>
    <div v-if="error" class="alert alert-error">
      {{ error }}
    </div>
    <form @submit.prevent="register">
      <div class="input-group">
        <label for="username">Felhasználónév</label>
        <input id="username" v-model="username" type="text" required autocomplete="username">
      </div>
      <div class="input-group">
        <label for="password">Jelszó</label>
        <input id="password" v-model="password" type="password" required autocomplete="current-password">
      </div>
      <button type="submit">Regisztrálás</button>
    </form>
  </div>
</template>

<script>
import axios from '@/axiosConfig.js';

export default {
  name: 'Register',
  data() {
    return {
      username: '',
      password: '',
      error: '',
    };
  },
  methods: {
    async register() {
      try {
        const response = await axios.post('/api/register', {
          username: this.username,
          password: this.password
        });

        // If registration is successful, redirect to login page
        if (response.status === 201) {
          this.$store.commit('setRegistrationSuccess', true);
          this.$router.push('/login');
        }
      } catch (error) {
        // Handle error
        console.log(error);

        // Display error message
        if (error.response && error.response.data) {
          this.error = 'Registration failed: ' + error.response.data;
        } else {
          this.error = 'Registration failed: ' + error.message;
        }
      }
    },
  },
};
</script>

<style lang="postcss">
.login-container {
  @apply flex flex-col items-center justify-center min-h-screen bg-blue-200;
}

.input-group {
  @apply mb-4;
}

label {
  @apply block mb-2 text-sm font-bold text-gray-700;
}

input {
  @apply w-full px-3 py-2 text-sm leading-tight text-gray-700 border rounded shadow appearance-none;
}

button {
  @apply px-4 py-2 mt-4 text-sm font-medium text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none;
}

.title {
  @apply text-4xl font-bold mb-8 text-center p-4;
}

.alert-error {
  @apply bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4;
}
</style>