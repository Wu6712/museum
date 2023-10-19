import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    params: {
      username: data.username,
      password: data.password
    }
  })
}

export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    params: {
      username: data.username,
      password: data.password
    }
  })
}

export function getInfo() {
  return request({
    url: '/user',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function getUserList() {
  return request({
    url: '/user/list',
    method: 'get',
    params: {
      pageNum: 1,
      pageSize: 10
    }
  })
}

export function getListByName(data) {
  return request({
    url: '/user/fuzzyList',
    method: 'get',
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: data.username
    }
  })
}

export function deleteUsers(deleteData) {
  return request({
    url: `/user/remove/batch/${deleteData}`,
    method: 'post',
  })
}

export function updateUser(data) {
  return request({
    url: `/user`,
    method: 'put',
    data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function updatePassword(data) {
  return request({
    url: '/user/info/passwordById',
    method: 'post',
    params: {
      id: data.id,
      oldPassword: data.oldPassword,
      newPassword: data.newPassword
    }
  })
}
