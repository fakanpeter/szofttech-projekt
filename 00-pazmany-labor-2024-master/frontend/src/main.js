import './index.css'
import {createRouter, createWebHistory} from 'vue-router'
import HomePage from './components/HomePage.vue'
import AllDogs from './components/dogs/AllDogs.vue'
import Contact from './components/common/Contact.vue'
import FAQ from './components/common/FAQ.vue'
import SingleDog from "@/components/dogs/SingleDog.vue";
import Login from "@/components/auth/Login.vue";
import Register from "@/components/auth/Register.vue";
import EditDog from "@/components/dogs/EditDog.vue";
import axios from "axios";

import {createApp} from 'vue';
import store from './store';
import App from './App.vue';

const routes = [
    {path: '/', component: HomePage},
    {path: '/dogs', component: AllDogs},
    {path: '/contact', component: Contact},
    {path: '/faq', component: FAQ},
    {path: '/dog/:id', name: 'SingleDog', component: SingleDog},
    {path: '/edit-dog/:id', name: 'EditDog', component: EditDog, meta: {requiresAuth: true}},
    {path: '/login', component: Login},
    {path: '/register', component: Register},
    {path: '/:pathMatch(.*)*', redirect: '/'}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

const loggedIn = false;

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // This route requires auth, check if logged in, if not, redirect to login page.
        if (!loggedIn) {
            next({
                path: '/login',
                query: {redirect: to.fullPath}
            })
        } else {
            next()
        }
    } else {
        next()
    }
})

// axios.interceptors.request.use(
//     (config) => {
//         if (store.state.token) {
//             config.headers.Authorization = `Bearer ${store.state.token}`;
//         }
//         return config;
//     },
//     (error) => {
//         return Promise.reject(error);
//     }
// );

createApp(App).use(router).use(store).mount('#app');