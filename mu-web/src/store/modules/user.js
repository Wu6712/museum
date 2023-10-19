import { login, getInfo, register, deleteUsers, updateUser, updatePassword } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    id: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_ID: (state, id) => {
    state.id = id
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password.trim() }).then(response => {
        const { data } = response
        console.log('login res')
        console.log(data)
        commit('SET_TOKEN', data)
        setToken(data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  // user register
  register(state, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      register({ username: username.trim(), password: password}).then(response => {
        console.log(response)

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response
        console.log('getInfo-------------')
        console.log(data)

        if (!data) {
          return reject('验证失败，请重新登录')
        }

        commit('SET_NAME', data.username)
        commit('SET_ID', data.id)

        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve) => {
      //logout(state.token).then(() => {
      //  removeToken() // must remove  token  first
      //  resetRouter()
      //  commit('RESET_STATE')
      //  resolve()
      //}).catch(error => {
      //  reject(error)
      //})
      removeToken()
      resetRouter()
      commit('RESET_STATE')
      resolve()
    })
  },
  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },
  // delete users
  deleteUsers(state, deleteData) {
    return new Promise((resolve, reject) => {
      deleteUsers(deleteData).then(response => {
        console.log(response)
        if (response.code !== 200) {
          return reject("删除失败")
        }
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // update user
  updateUser(state, formData) {
    return new Promise((resolve, reject) => {
      updateUser(formData).then(response => {

        if (response.code !== 200) {
          return reject(response.message)
        }

        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // change password
  updatePassword(state, data) {
    return new Promise((resolve, reject) => {
      updatePassword(data).then(response => {

        if (response.code !== 200) {
          return reject(response.message)
        }

        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

