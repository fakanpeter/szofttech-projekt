import { createStore } from 'vuex';
import VueCookies from 'vue-cookies';

export default createStore({
    state: {
        token: VueCookies.get('auth-token') || null,
        registrationSuccess: false,
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
        setRegistrationSuccess(state, success) {
            state.registrationSuccess = success;
        }
    },
});