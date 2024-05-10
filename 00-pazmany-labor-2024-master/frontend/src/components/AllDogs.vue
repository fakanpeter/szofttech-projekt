<template>
  <div class="dog-list-container">
    <div v-if="errorMessage || fetchError || !hasDogs" class="error-message">
      {{ errorMessage || (fetchError ? 'Could not fetch dogs.' : 'No dogs found.') }}
    </div>
    <div v-else class="dog-item-container">
      <div v-for="dog in dogs" :key="dog.id" class="dog-item" @click="viewDog(dog.id)" v-if="dog">
        <img :src="dog.image" alt="Dog image" class="dog-image" v-if="dog.image"/>
        <h2 class="dog-name">{{ dog.name }}</h2>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AllDogs',
  data() {
    return {
      dogs: [],
      fetchError: false,
      errorMessage: '',
    };
  },
  computed: {
    hasDogs() {
      return this.dogs.length > 0;
    },
  },
  async created() {
    try {
      const response = await axios.get('/api/dogs', { timeout: 5000 }); // 5 seconds timeout
      if (response.status === 200) {
        if (response.data && response.data.length > 0) {
          this.dogs = response.data;
        } else {
          this.errorMessage = 'No dogs found.';
        }
      } else {
        this.fetchError = true;
        this.errorMessage = 'Could not fetch dogs.';
      }
    } catch (error) {
      console.error(error);
      this.fetchError = true;
      if (error.message.includes('timeout')) {
        this.errorMessage = 'API request timed out.';
      } else {
        this.errorMessage = 'Could not fetch dogs.';
      }
    }
  },
  methods: {
    viewDog(id) {
      this.$router.push({ name: 'SingleDog', params: { id } });
    },
  },
};
</script>

<style scoped lang="postcss">
.dog-list-container {
  @apply flex flex-col justify-center items-center h-full bg-red-100; /* Adjust background color as needed */
}

.dog-item-container {
  @apply flex flex-wrap justify-center p-4 bg-white rounded;
}

.dog-item {
  @apply flex flex-col items-center m-4 p-4 bg-white rounded shadow;
}

.dog-image {
  @apply w-full h-64 object-cover mb-4 rounded;
}

.dog-name {
  @apply text-lg font-bold;
}

.error-message {
  @apply p-4 bg-red-200 text-red-800 rounded;
}
</style>