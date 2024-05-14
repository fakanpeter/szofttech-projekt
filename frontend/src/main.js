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

import {createApp} from 'vue';
import store from './store';
import App from './App.vue';
import AddDog from "@/components/dogs/AddDog.vue";

const routes = [
    {path: '/', component: HomePage},
    {path: '/dogs', component: AllDogs},
    {path: '/contact', component: Contact},
    {path: '/faq', component: FAQ},
    {path: '/dog/:id', name: 'SingleDog', component: SingleDog},
    {path: '/edit-dog/:id', name: 'EditDog', component: EditDog, meta: {requiresAuth: true}},
    {path: '/login', component: Login, name: 'Login'},
    {path: '/register', component: Register},
    {path: '/add-dog', name: 'AddDog', component: AddDog, meta: {requiresAuth: true}},
    {path: '/:pathMatch(.*)*', redirect: '/'}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.requiresAuth) {
        store.dispatch('checkAuthenticationStatus')
            .then(isAuthenticated => {
                if (!isAuthenticated) {
                    next({ name: 'Login', query: { redirect: to.fullPath } });
                } else {
                    next();
                }
            });
    } else {
        next();
    }
});

createApp(App).use(router).use(store).mount('#app');