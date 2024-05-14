<template>
  <div class="global-container">
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
      const usernameRegex = /^\w{5,20}$/; // username can contain numbers, upper and lowercase characters
      const passwordRegex = /^(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,20}$/; // password must contain at least one uppercase letter and one digit

      if (!username.value) {
        errorMessage.value = 'Felhasználónév megadása kötelező';
        return false;
      }

      if (!username.value.match(usernameRegex)) {
        errorMessage.value = 'Érvénytelen felhasználónév. A felhasználónévnek legalább 5 és legfeljebb 20 karakter hosszúnak kell lennie.';
        return false;
      }

      if (!password.value) {
        errorMessage.value = 'Jelszó megadása kötelező';
        return false;
      }

      if (!password.value.match(passwordRegex)) {
        errorMessage.value = 'Érvénytelen jelszó. A jelszónak legalább 8 és legfeljebb 20 karakter hosszúnak kell lennie, és tartalmaznia kell legalább egy nagybetűt és egy számot.';
        return false;
      }

      errorMessage.value = '';
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
          errorMessage.value = 'Regisztráció sikertelen: ' + error.response.data;
        } else {
          errorMessage.value = 'Regisztráció sikertelen: ' + error.message;
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