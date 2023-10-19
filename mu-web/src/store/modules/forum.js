import { getForumList, deleteForums, insertForum } from "@/api/forum";

const actions = {
  // get forumList
  getForumList(state, data) {
    return new Promise((resolve, reject) => {
      getForumList(data).then(response => {

        if (response.code !== 200) {
          return reject('获取菜单数据失败')
        }

        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // delete notices
  deleteForums(state, deleteData) {
    return new Promise((resolve, reject) => {
      deleteForums(deleteData).then(response => {

        if (response.code !== 200) {
          return reject("删除失败")
        }
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // insert notices
  insertForum(state, noticeData) {
    return new Promise((resolve, reject) => {
      insertForum(noticeData).then(response => {
        console.log(response)

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
