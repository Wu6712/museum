<template>
  <div class="app-container">
    <div>
      <!--批量删除-->
      <el-button type="danger" round style="margin-left: 10px;"
                 :disabled="deleteArr.length === 0"
                 @click="deleteNotices">
        批量删除
      </el-button>

      <!--新增-->
      <el-button icon="el-icon-plus" type="primary" plain
                 @click="insertDialogVisible = true">
        新增
      </el-button>

      <div style="margin: 10px 0 10px 10px">
        预约总数: {{total}}
      </div>

      <!--表格数据-->
      <el-table
        :data="bookingList"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55px">
        </el-table-column>
        <el-table-column
          prop="id"
          label="ID"
          width="150">
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="name"
          label="博物馆名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="start_time"
          label="开始时间"
          width="250">
        </el-table-column>
        <el-table-column
          prop="end_time"
          label="结束时间"
          width="250">
        </el-table-column>
        <el-table-column
          label="操作">
          <template scope="scope">
            <el-button type="success" size="mini"
                       @click="updateNotice(scope.row)">
              修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--新增Dialog对话框-->
      <el-dialog
        title="新增"
        :visible.sync="insertDialogVisible"
        width="50%">

        <el-form v-model="insertFormData" label-position="left">
          <el-form-item label="地点">
            <el-select v-model="insertFormData.museumId"
                       placeholder="请选择"
                       clearable>
              <el-option
                v-for="item in museumList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间段">
            <el-date-picker
              v-model="timeInterval"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy:MM:dd HH:mm">
            </el-date-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelInsert">取 消</el-button>
          <el-button type="primary" @click="confirmInsert">确 定</el-button>
        </div>

      </el-dialog>

      <!--修改Dialog对话框-->
      <el-dialog
        title="修改"
        :visible.sync="updateDialogVisible"
        width="50%">

        <el-form v-model="updateFormData" label-position="left">
          <el-form-item label="地点">
            <el-select v-model="updateFormData.museum_id"
                       placeholder="请选择"
                       clearable>
              <el-option
                v-for="item in museumList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间段">
            <el-date-picker
              v-model="timeInterval"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="[updateFormData.start_time, updateFormData.end_time]"
              value-format="yyyy:MM:dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelUpdate">取 消</el-button>
          <el-button type="primary" @click="confirmUpdate">确 定</el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import { updateBookingById, getMuseumList, getTotal } from "@/api/booking";

export default {
  name: "Booking",
  data() {
    return {
      deleteArr: [],
      bookingList: [],
      museumList: [],
      pageNum: 1,
      pageSize: 20,
      insertDialogVisible: false,
      insertFormData: {},
      updateDialogVisible: false,
      updateFormData: {},
      timeInterval: '',
      total: ''
    }
  },
  methods: {
    getTotal() {
      getTotal().then(res => {
        this.total = res.data
      })
    },
    deleteNotices() {
      this.$confirm('确认删除', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '再想想',
        type: 'warning'
      }).then(() => {
        let deleteData = this.deleteArr.map(item => {
          return item.id
        })

        this.$store.dispatch('booking/deleteBookings', deleteData).then((response) => {
          this.$message.success(response.message)
          this.getBookingList()
        }).catch(error => {
          this.$message.error(error.message)
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    handleSelectionChange(val) {
      this.deleteArr = val
    },
    getBookingList() {
      this.$store.dispatch('booking/getBookingList',{
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then((response) => {
        this.bookingList = response.data.data
      }).catch(error => {
        this.$message.error(error)
      })
    },
    cancelInsert() {
      this.insertFormData = {}
      this.timeInterval = ''
      this.insertDialogVisible = false
    },
    confirmInsert() {
      this.insertFormData.startTime = this.timeInterval[0]
      this.insertFormData.endTime = this.timeInterval[1]
      this.insertFormData.userId = this.$store.state.user.id
      this.insertFormData.status = 1
      this.$store.dispatch('booking/insertBooking', this.insertFormData).then(response => {
        this.$message.success(response.message)
        this.getBookingList()
        this.cancelInsert()
      }).catch(error => {
        this.$message.error(error)
      })
    },
    updateNotice(row) {
      this.updateFormData = row
      this.updateDialogVisible = true
    },
    cancelUpdate() {
      this.getBookingList()
      this.updateDialogVisible = false
      this.updateFormData = {}
    },
    confirmUpdate() {
      const confirmUpdateDate = {}
      confirmUpdateDate.id = this.updateFormData.id
      confirmUpdateDate.startTime = this.timeInterval[0]
      confirmUpdateDate.endTime = this.timeInterval[1]
      confirmUpdateDate.status = this.updateFormData.status
      confirmUpdateDate.userId = this.updateFormData.user_id
      confirmUpdateDate.museumId = this.updateFormData.museum_id
      updateBookingById(confirmUpdateDate).then(response => {
        if(response.code !== 200) return this.$message.error(response.message)
        this.$message.success(response.message)
        this.getBookingList()
        this.updateDialogVisible = false
      }).catch(error => {
        this.$message.error(error)
      })
    },
    getMuseumList() {
      getMuseumList().then(({ data: response }) => {
        this.museumList = response.data
      })
    }
  },
  mounted() {
    this.getBookingList()
    this.getMuseumList()
    this.getTotal()
  }
}
</script>

<style scoped>

</style>
