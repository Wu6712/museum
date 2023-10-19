import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import notice from './modules/notice'
import forum from './modules/forum'
import booking from './modules/booking'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    notice,
    forum,
    booking
  },
  getters
})

export default store
