<template>
  <div v-if="dog" class="single-dog flex flex-col items-center bg-gray-100 p-4 rounded shadow">
    <h1 class="text-2xl font-bold mb-4">{{ dog.name }}</h1>
    <img :src="dog.picture" :alt="`Image of ${dog.name}`" class="w-64 h-64 object-cover mb-4 rounded shadow"/>
    <p class="text-lg mb-2"><strong>Age:</strong> {{ dog.age }}</p>
    <p class="text-lg"><strong>Breed:</strong> {{ dog.breed }}</p>
    <router-link :to="`/edit-dog/${dog.id}`" tag="button" class="edit-button">Edit</router-link>
  </div>
  <div v-else>Loading...</div>
</template>

<script>
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';

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
</style>