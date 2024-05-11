<template>
  <div class="edit-dog-container">
    <h1>Kutya hozzáadása</h1>
    <form @submit.prevent="addDog">
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
import axios from 'axios';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      dog: {},
    };
  },
  computed: {
    ...mapState(['token']),
  },
  methods: {
    async addDog() {
      const config = {
        headers: { Authorization: `Bearer ${this.token}` },
      };
      await axios.post(`/api/newdog`, this.dog, config);
      this.$router.push(`/dogs`);
    },
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