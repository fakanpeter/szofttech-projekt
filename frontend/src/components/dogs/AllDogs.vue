<template>
  <div class="global-container">
    <router-link to="/add-dog" class="button">Új kutya hozzáadása</router-link>
    <div v-if="!loaded">
      Betöltés...
    </div>
    <div v-else-if="errorMessage || fetchError || !hasDogs" class="alert alert-error">
      {{ errorMessage || (fetchError ? 'Could not fetch dogs.' : 'No dogs found.') }}
    </div>
    <div v-else class="dog-item-container">
      <div v-for="dog in dogs" :key="dog.id" class="dog-item" @click="viewDog(dog.id)">
        <img v-if="dog"
             :src="dog.picture ? 'data:image/jpeg;base64,' + dog.picture : 'data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' viewBox=\'0 0 50 50\'%3E%3Ctext y=\'.9em\' font-size=\'20\'%3E' + dog.id + '%3C/text%3E%3C/svg%3E'"
             :alt="`Image of ${dog.name}`" class="dog-image"/>
        <h2 v-if="dog" class="dog-name">{{ dog.name }}</h2>
      </div>
    </div>
  </div>
</template>


<script>
import { axios, apiURL } from '@/axiosConfig.js';

export default {
  name: 'AllDogs',
  data() {
    return {
      dogs: [],
      fetchError: false,
      errorMessage: '',
      loaded: false,
    };
  },
  computed: {
    hasDogs() {
      return this.dogs.length > 0;
    },
  },
  async created() {
    try {
      const response = await axios.get(apiURL + '/dogs', {timeout: 5000}); // 5 seconds timeout
      if (response.status === 200) {
        if (response.data && response.data.length > 0) {
          this.dogs = response.data;
        } else {
          this.errorMessage = 'Nem találhatóak kutyák.';
        }
      } else {
        this.fetchError = true;
        this.errorMessage = 'Nem sikerült lekérni a kutyákat.';
      }
    } catch (error) {
      console.error(error);
      this.fetchError = true;
      if (error.message.includes('timeout')) {
        this.errorMessage = 'A kutyák lekérdezése túllépte az időkorlátot. Próbáld újra később.';
      } else {
        this.errorMessage = 'Nem sikerült lekérni a kutyákat.';
      }
    } finally {
      this.loaded = true;
    }
  },
  methods: {
    viewDog(id) {
      this.$router.push({name: 'SingleDog', params: {id}});
    },
  },
};
</script>

<style scoped lang="postcss">

.dog-item-container {
  @apply flex flex-wrap justify-around p-4 rounded;
}

.dog-item {
  @apply flex flex-col items-center bg-white m-4 p-4 rounded shadow w-1/4 min-w-64 min-h-64;
}

.dog-image {
  @apply w-full h-64 object-cover mb-4 rounded min-w-64 min-h-64;
}

.dog-name {
  @apply text-lg font-bold;
}

</style>