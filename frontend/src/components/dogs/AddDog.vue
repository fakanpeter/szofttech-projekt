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
      <div class="input-group">
        <label for="dog-image">Kép</label>
        <file-pond
            id="dog-image"
            name="dogPicture"
            ref="pond"
            label-idle="Húzza ide a fájlt..."
            allow-multiple="false"
            accepted-file-types="image/jpeg, image/png"
            v-on:init="handleFilePondInit"
        />
      </div>
      <button type="submit">Mentés</button>
    </form>
  </div>
</template>

<script>
import { axios, apiURL } from '@/axiosConfig.js';
import { mapState } from 'vuex';
import vueFilePond from 'vue-filepond';
import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
import FilePondPluginFileValidateType from 'filepond-plugin-file-validate-type';
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css';
import 'filepond/dist/filepond.min.css';

const FilePond = vueFilePond(FilePondPluginFileValidateType, FilePondPluginImagePreview);

export default {
  components: {
    FilePond
  },
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
    handleFilePondInit: function() {
      console.log('FilePond has initialized');
    },
    validateAndAddDog() {
      if (this.validateForm()) {
        this.addDog();
      }
    },
    validateForm() {
      this.dog.name = this.dog.name.trim();
      this.dog.breed = this.dog.breed.trim();

      const name = this.dog.name;
      const breed = this.dog.breed;
      const age = this.dog.age;
      const regex = /^[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ ]*$/;

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
      const files = this.$refs.pond.getFiles();
      const pictureFile = files.length > 0 ? files[0].file : null;

      const dogData = {
        name: this.dog.name,
        breed: this.dog.breed,
        age: this.dog.age
      };

      const formData = new FormData();
      formData.append('dog', JSON.stringify(dogData)); // Convert dog data to JSON string
      if (pictureFile) {
        formData.append('picture', pictureFile, pictureFile.name); // Append picture as a blob only if it exists
      } else {
        formData.append('picture', null); // Append null if picture does not exist
      }

      const config = {
        headers: {
          Authorization: `Bearer ${this.token}`,
        },
      };

      try {
        await axios.post(apiURL + `/newdog`, formData, config);
        this.$router.push(`/dogs`);
      } catch (error) {
        console.error('Hiba történt a kutya hozzáadása közben:', error);
        // Handle error
      }
    },
  },
};
</script>