import { createStore } from 'vuex';
import VueCookies from 'vue-cookies';

export default createStore({
    state: {
        token: VueCookies.get('auth-token') || null,
    },
    mutations: {
        setToken(state, token) {
            state.token = token;
            VueCookies.set('auth-token', token, '1d');
        },
        clearToken(state) {
            state.token = null;
            VueCookies.remove('auth-token');
        },
        logout(state) {
            state.token = null;
            VueCookies.remove('auth-token');
        },
    },
});