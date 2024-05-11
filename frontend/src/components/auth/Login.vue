<template>
  <div class="login-container">
    <div v-if="registrationSuccess" class="alert alert-success">
      Regisztráció sikeres! Most már bejelentkezhetsz.
    </div>
    <div v-if="error" class="alert alert-error">
      {{ error }}
    </div>
    <form @submit.prevent="login">
      <div class="input-group">
        <label for="username">Felhasználónév</label>
        <input id="username" v-model="username" type="text" required autocomplete="username">
      </div>
      <div class="input-group">
        <label for="password">Jelszó</label>
        <input id="password" v-model="password" type="password" required autocomplete="current-password">
      </div>
      <button type="submit">Bejelentkezés</button>
    </form>
    <p>Még nem vagy regisztrálva?</p>
    <router-link to="/register" tag="button" class="register-button">Regisztrálás</router-link>
  </div>
</template>

<script>
import axios from 'axios';
import {mapState} from "vuex";

axios.defaults.baseURL = 'http://localhost:8080';

export default {
  name: 'Login',
  computed: {
    ...mapState(['registrationSuccess']),
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/api/login', {
          username: this.username,
          password: this.password
        });

        if (response.status === 200) {
          this.$store.commit('setToken', response.data.token);
          this.$router.push('/');
        }
      } catch (error) {
        this.error = 'Helytelen felhasználónév vagy jelszó';
        this.$store.commit('setRegistrationSuccess', false);
      }
    },
  },
  data() {
    return {
      username: '',
      password: '',
      error: '',
    };
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

p {
  @apply mt-10;
}

.register-button {
  @apply px-4 py-2 mt-4 text-sm font-medium text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none;
}

.alert-success {
  @apply bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4;
}

.alert-error {
  @apply bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4;
}
</style>