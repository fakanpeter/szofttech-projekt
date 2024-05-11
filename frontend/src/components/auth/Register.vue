<template>
  <div class="login-container">
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
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';

export default {
  name: 'Register',
  data() {
    return {
      username: '',
      password: '',
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
          alert('Registration failed: ' + error.response.data);
        } else {
          alert('Registration failed: ' + error.message);
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
</style>