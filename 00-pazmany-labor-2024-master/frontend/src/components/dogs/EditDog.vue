<template>
  <div class="edit-dog-container">
    <form @submit.prevent="editDog">
      <div class="input-group">
        <label for="name">Dog Name</label>
        <input id="name" v-model="dog.name" type="text" required>
      </div>
      <div class="input-group">
        <label for="breed">Breed</label>
        <input id="breed" v-model="dog.breed" type="text" required>
      </div>
      <div class="input-group">
        <label for="age">Age</label>
        <input id="age" v-model="dog.age" type="number" required>
      </div>
      <button type="submit">Save</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';

export default {
  name: 'EditDog',
  data() {
    return {
      dog: {
        name: '',
        breed: '',
        age: '',
      },
    };
  },
  methods: {
    async editDog() {
      try {
        const response = await axios.post('/dogs/' + this.dog.id + '/edit', this.dog);

        // Handle response
        console.log(response);
      } catch (error) {
        // Handle error
        console.log(error);
      }
    },
    async fetchDogData() {
      try {
        const response = await axios.get('/dogs/' + this.$route.params.id);
        this.dog = response.data;
      } catch (error) {
        console.error(error);
      }
    },
  },
  created() {
    // Fetch the dog data when the component is created
    this.fetchDogData();
  },
};
</script>

<style lang="postcss">
.edit-dog-container {
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