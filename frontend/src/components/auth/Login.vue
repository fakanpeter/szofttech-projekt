<template>
  <div class="login-container">
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
// import MockAdapter from 'axios-mock-adapter';
//
// const mock = new MockAdapter(axios);
// mock.onPost('/api/auth/login').reply(200, {
//   token: 'test_token',
// });

axios.defaults.baseURL = 'http://localhost:8080';

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/api/auth/login', {
          username: this.username,
          password: this.password
        });

        // Handle response
        console.log(response);
      } catch (error) {
        // Handle error
        console.log(error);
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

p {
  @apply mt-10;
}

.register-button {
  @apply px-4 py-2 mt-4 text-sm font-medium text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none;
}
</style>