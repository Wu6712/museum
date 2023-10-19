import { getBookingList, deleteBookings, insertBooking } from "@/api/booking";

const actions = {
  // get bookingList
  getBookingList(state, data) {
    return new Promise((resolve, reject) => {
      getBookingList(data).then(response => {

        if (response.code !== 200) {
          return reject('获取菜单数据失败')
        }

        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // delete bookings
  deleteBookings(state, deleteData) {
    return new Promise((resolve, reject) => {
      deleteBookings(deleteData).then(response => {

        if (response.code !== 200) {
          return reject("删除失败")
        }
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // insert booking
  insertBooking(state, noticeData) {
    return new Promise((resolve, reject) => {
      insertBooking(noticeData).then(response => {
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
