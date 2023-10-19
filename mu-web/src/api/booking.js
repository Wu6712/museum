import request from "@/utils/request";

export function getBookingList(data) {
  return request({
    url: '/reservation/list2',
    method: 'post',
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize
    }
  })
}

export function deleteBookings(deleteData) {
  return request({
    url: `/reservation/remove/batch/${deleteData}`,
    method: 'post',
  })
}


export function insertBooking(noticeData) {
  return request({
    url: '/reservation/add',
    method: 'post',
    data: JSON.stringify(noticeData),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function updateBookingById(noticeData) {
  return request({
    url: '/reservation/mod',
    method: 'post',
    data: JSON.stringify(noticeData),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function getMuseumList() {
  return request({
    url: '/museum/list',
    method: 'post',
    params: {
      pageNum: 1,
      pageSize: 20
    }
  })
}

export function getTotal() {
  return request({
    url: '/reservation/total',
    method: 'get',
  })
}
