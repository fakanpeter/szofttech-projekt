<template>
  <div v-if="dog" class="single-dog flex flex-col items-center bg-gray-100 p-4 rounded shadow">
    <h1 class="text-2xl font-bold mb-4">{{ dog.name }}</h1>
    <img :src="dog.picture" :alt="`${dog.name} képe`" class="w-64 h-64 object-cover mb-4 rounded shadow"/>
    <p class="text-lg mb-2"><strong>Kor:</strong> {{ dog.age }}</p>
    <p class="text-lg"><strong>Faj:</strong> {{ dog.breed }}</p>
    <router-link :to="`/edit-dog/${dog.id}`" tag="button" class="edit-button">Szerkesztés</router-link>
    <button @click="deleteDog" class="delete-button">Törlés</button>
  </div>
  <div v-else>Betöltés...</div>
</template>

<script>
import axios from '@/axiosConfig.js';

export default {
  name: 'SingleDog',
  data() {
    return {
      dog: null,
    };
  },
  async created() {
    const id = this.$route.params.id;
    try {
      const response = await axios.get(`/api/dogs/${id}`);
      this.dog = response.data;
    } catch (error) {
      console.error(error);
      // Handle error
    }
  },
  methods: {
    async deleteDog() {
      if (window.confirm('Biztosan törölni akarod ezt a kutyát?')) {
        const config = {
          headers: { Authorization: `Bearer ${this.token}` },
        };
        await axios.delete(`/api/dogs/${this.$route.params.id}`, config);
        this.$router.push(`/dogs`);
      }
    },
  },
};
</script>

<style scoped lang="postcss">
.single-dog {
  @apply flex flex-col items-center bg-blue-200 p-4 rounded shadow;
}

h1 {
  @apply text-2xl font-bold mb-4;
}

img {
  @apply w-64 h-64 object-cover mb-4 rounded shadow bg-white;
}

p {
  @apply text-lg mb-2;
}

.edit-button {
  @apply px-4 py-2 mt-4 text-sm font-medium text-white bg-blue-500 rounded hover:bg-blue-700 focus:outline-none;
}

.delete-button {
  @apply px-4 py-2 mt-4 text-sm font-medium text-white bg-red-500 rounded hover:bg-red-700 focus:outline-none;
}
</style>