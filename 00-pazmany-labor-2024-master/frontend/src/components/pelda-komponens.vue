<script setup>
import {ref} from "vue";
import axios from 'axios';

const isLoading = ref(true);
const aFact = ref('');

function getAFact() {
  isLoading.value = true;
  axios.get('https://catfact.ninja/fact').then(result => {
    aFact.value = result.data.fact;
    isLoading.value = false;
  }).catch(error => {
    console.error('cannot get data from api', error);
  });
}

getAFact();

const backendStatus = ref('unknown');
function checkBackend() {
  axios.get('/api/ok').then(result=>{
    backendStatus.value = result.data;
  }).catch(error => {
    console.error('cannot get backend OK api endpoint', error);
    backendStatus.value = 'error';
  })
}
</script>

<template>
  <div v-if="isLoading" class="loading">Loading...</div>
  <template v-else>
    <button @click="getAFact">Next fact</button>
    <div class="a-fact">{{ aFact }}</div>
  </template>
  <button @click="checkBackend">Check Backend</button>
  <p>Backend status is: {{backendStatus}}</p>
</template>

<style scoped>
.loading {
  color: lightgray;
}
.a-fact {
  font-weight: bold;
}
</style>