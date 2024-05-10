<template>
  <div class="single-dog">
    <h1>{{ dog.name }}</h1>
    <img :src="dog.image" alt="Dog image" />
    <!-- Add more dog details here -->
  </div>
</template>

<script>
import axios from 'axios';

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
/* Add your styles here */
</style>