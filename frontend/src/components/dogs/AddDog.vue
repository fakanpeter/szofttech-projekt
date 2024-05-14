<template>
  <div class="global-container">
    <h1>Kutya hozzáadása</h1>
    <div v-if="errorMessage" class="alert alert-error">
      {{ errorMessage }}
    </div>
    <form @submit.prevent="validateAndAddDog" enctype="multipart/form-data">
      <div class="input-group">
        <label for="name">Neve</label>
        <input id="name" v-model="dog.name" type="text" required>
      </div>
      <div class="input-group">
        <label for="breed">Faja</label>
        <input id="breed" v-model="dog.breed" type="text" required>
      </div>
      <div class="input-group">
        <label for="age">Kora</label>
        <input id="age" v-model="dog.age" type="number" required>
      </div>
      <button type="submit">Mentés</button>
    </form>
  </div>
</template>

<script>
import { axios, apiURL } from '@/axiosConfig.js';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      dog: {},
      errorMessage: '',
    };
  },
  computed: {
    ...mapState(['token']),
  },
  methods: {
    validateAndAddDog() {
      if (this.validateForm()) {
        this.addDog();
      }
    },
    validateForm() {
      const name = this.dog.name;
      const breed = this.dog.breed;
      const age = this.dog.age;
      const regex = /^[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ]*$/;

      if (!name.match(regex)) {
        this.errorMessage = "Helytelen név. Csak betűket tartalmazhat.";
        return false;
      }

      if (!breed.match(regex)) {
        this.errorMessage = "Helytelen fajta. Csak betűket tartalmazhat.";
        return false;
      }

      if (age < 0) {
        this.errorMessage = "Helytelen kor. Nem lehet negatív.";
        return false;
      }

      this.errorMessage = '';  // Clear the error message if all fields are valid
      return true;
    },
    async addDog() {
      const config = {
        headers: { Authorization: `Bearer ${this.token}` },
      };
      await axios.post(apiURL + '/newdog', this.dog, config);
      this.$router.push(`/dogs`);
    },
  },
};
</script>