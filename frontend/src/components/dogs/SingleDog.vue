<template>
  <div v-if="dog" class="global-container">
    <h1 class="text-2xl font-bold mb-4">{{ dog.name }}</h1>
    <img v-if="dog"
         :src="dog.picture ? 'data:image/jpeg;base64,' + dog.picture : 'data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' viewBox=\'0 0 50 50\'%3E%3Ctext y=\'.9em\' font-size=\'20\'%3E' + dog.id + '%3C/text%3E%3C/svg%3E'"
         :alt="`Image of ${dog.name}`" class="dog-image"/>
    <p class="text-lg mb-2"><strong>Kor:</strong> {{ dog.age }}</p>
    <p class="text-lg"><strong>Faj:</strong> {{ dog.breed }}</p>
    <router-link :to="`/edit-dog/${dog.id}`" tag="button" class="button">Szerkesztés</router-link>
    <button @click="deleteDog" class="button delete-button ">Törlés</button>
  </div>
  <div v-else>Betöltés...</div>
</template>

<script>
import { axios, apiURL } from '@/axiosConfig.js';
import {mapState} from "vuex";

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
      const response = await axios.get(apiURL + `/dogs/${id}`);
      this.dog = response.data;
    } catch (error) {
      console.error(error);
      // Handle error
    }
  },
  computed: {
    ...mapState(['token']),
  },
  methods: {
    async deleteDog() {
      if (window.confirm('Biztosan törölni akarod ezt a kutyát?')) {
        const config = {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        };
        try {
          await axios.delete(apiURL + `/dogs/${this.$route.params.id}`, config);
          this.$router.push(`/dogs`);
        } catch (error) {
          if (error.response && error.response.status === 401) {
            this.$router.push('/login');
          } else if (error.response && error.response.status === 404) {
            // Handle "Not Found" error
            console.error('Dog not found:', error);
          } else {
            // Handle other types of errors
            console.error('Error deleting dog:', error);
          }
        }
      }
    }
  },
};
</script>

<style scoped lang="postcss">
img {
  @apply w-64 h-64 object-cover mb-4 rounded shadow bg-white;
}

.delete-button {
  @apply bg-red-500 hover:bg-red-700;
}
</style>