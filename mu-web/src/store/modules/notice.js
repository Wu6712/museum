import { getNoticeList, deleteNotices, insertNotice } from "@/api/notice";

const actions = {
  // get forumList
  getNoticeList(state, data) {
    return new Promise((resolve, reject) => {
      getNoticeList(data).then(response => {

        if (response.code !== 200) {
          return reject('获取菜单数据失败')
        }

        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // delete forums
  deleteNotices(state, deleteData) {
    return new Promise((resolve, reject) => {
      deleteNotices(deleteData).then(response => {

        if (response.code !== 200) {
          return reject("删除失败")
        }
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // insert forum
  insertNotice(state, forumData) {
    return new Promise((resolve, reject) => {
      insertNotice(forumData).then(response => {

        if (response.code !== 200) {
          return reject('添加失败')
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
  actions
}
