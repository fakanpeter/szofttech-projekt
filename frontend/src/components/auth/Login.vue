<template>
  <div class="global-container">
    <h1 class="title">Bejelentkezés</h1>
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
    <router-link to="/register" tag="button" class="button">Regisztrálás</router-link>
  </div>
</template>

<script>
import { axios, apiURL } from '@/axiosConfig.js';
import { mapState } from "vuex";

export default {
  name: 'Login',
  computed: {
    ...mapState(['registrationSuccess']),
  },
  methods: {
    async login() {
      try {
        const response = await axios.post(apiURL + '/login', {
          username: this.username,
          password: this.password
        });

        if (response.status === 200) {
          this.$store.commit('setToken', response.data.token);

          const redirect = this.$route.query.redirect;
          if (redirect) {
            this.$router.push(redirect);
          } else {
            this.$router.push('/');
          }
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