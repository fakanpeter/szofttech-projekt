<template>
  <div class="login-container">
    <h1 class="title">Regisztráció</h1>
    <div v-if="errorMessage" class="alert alert-error">
      {{ errorMessage }}
    </div>
    <form @submit.prevent="validateAndRegister">
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
import { axios, apiURL } from '@/axiosConfig.js';
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'Register',
  setup() {
    const username = ref('');
    const password = ref('');
    const errorMessage = ref('');
    const store = useStore();
    const router = useRouter();

    const validateAndRegister = () => {
      if (validateForm()) {
        register();
      }
    };

    const validateForm = () => {
      const usernameRegex = /^[a-zA-Z0-9]+$/;
      const minLength = 5;

      if (!username.value) {
        errorMessage.value = 'Felhasználónév megadása kötelező';
        return false;
      }

      if (username.value.length < minLength) {
        errorMessage.value = 'A felhasználónév legalább 5 karakter hosszú kell legyen';
        return false;
      }

      if (!username.value.match(usernameRegex)) {
        errorMessage.value = 'Felhasználónév csak betűket és számokat tartalmazhat';
        return false;
      }

      if (!password.value) {
        errorMessage.value = 'Jelszó megadása kötelező';
        return false;
      }

      if (password.value.length < minLength) {
        errorMessage.value = 'A jelszó legalább 5 karakter hosszú kell legyen';
        return false;
      }

      errorMessage.value = '';  // Clear the error message if all fields are valid
      return true;
    };

    const register = async () => {
      try {
        const response = await axios.post(apiURL + '/register', {
          username: username.value,
          password: password.value
        });

        // If registration is successful, redirect to login page
        if (response.status === 201) {
          store.commit('setRegistrationSuccess', true);
          router.push('/login');
        }
      } catch (error) {
        // Handle error
        console.log(error);

        // Display error message
        if (error.response && error.response.data) {
          errorMessage.value = 'Registration failed: ' + error.response.data;
        } else {
          errorMessage.value = 'Registration failed: ' + error.message;
        }
      }
    };

    return {
      username,
      password,
      errorMessage,
      validateAndRegister,
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
  @apply px-4 py-2 mt-4 text-sm font-medium text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none mb-8;
}

.title {
  @apply text-4xl font-bold mb-8 text-center p-4;
}

.alert-error {
  @apply bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4;
}
</style>