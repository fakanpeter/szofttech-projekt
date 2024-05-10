import './index.css'
import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import HomePage from './components/HomePage.vue'
import AllDogs from './components/AllDogs.vue'
import Contact from './components/Contact.vue'
import FAQ from './components/FAQ.vue'
import SingleDog from "@/components/SingleDog.vue";

const routes = [
    { path: '/', component: HomePage },
    { path: '/dogs', component: AllDogs },
    { path: '/contact', component: Contact },
    { path: '/faq', component: FAQ },
    { path: '/dog/:id', component: SingleDog}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

createApp(App).use(router).mount('#app')