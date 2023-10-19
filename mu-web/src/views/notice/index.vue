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

      <!--表格数据-->
      <el-table
        :data="noticeList"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55px">
        </el-table-column>
        <el-table-column
          prop="id"
          label="ID"
          width="180">
        </el-table-column>
        <el-table-column
          prop="content"
          label="公告内容"
          width="200">
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

        <el-form :model="insertFormData">
          <el-form-item label="公告内容" label-width="80px">
            <el-input v-model="insertFormData.content"></el-input>
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

        <el-form :model="updateFormData">
          <el-form-item label="id" label-width="80px">
            <el-input v-model="updateFormData.id" readonly></el-input>
          </el-form-item>
          <el-form-item label="公告内容" label-width="80px">
            <el-input v-model="updateFormData.content"></el-input>
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
import {updateNoticeById} from "@/api/notice";

export default {
  name: "Notice",
  data() {
    return {
      deleteArr: [],
      noticeList: [],
      pageNum: 1,
      pageSize: 20,
      insertDialogVisible: false,
      insertFormData: {},
      updateDialogVisible: false,
      updateFormData: {},
    }
  },
  methods: {
    deleteNotices() {
      this.$confirm('确认删除', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '再想想',
        type: 'warning'
      }).then(() => {
        let deleteData = this.deleteArr.map(item => {
          return item.id
        })

        this.$store.dispatch('notice/deleteNotices', deleteData).then((response) => {
          this.$message.success(response.message)
          this.getNoticeList()
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
    getNoticeList() {
      this.$store.dispatch('notice/getNoticeList',{
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then((response) => {
        this.noticeList = response.data.data
      }).catch(error => {
        this.$message.error(error)
      })
    },
    cancelInsert() {
      this.insertFormData = {}
      this.insertDialogVisible = false
    },
    confirmInsert() {
      this.$store.dispatch('notice/insertNotice', this.insertFormData).then(response => {
        this.$message.success(response.message)
        this.getNoticeList()
        this.insertDialogVisible = false
      }).catch(error => {
        this.$message.error(error)
      })
    },
    updateNotice(row) {
      this.updateFormData = row
      this.updateDialogVisible = true
    },
    cancelUpdate() {
      this.getNoticeList()
      this.updateDialogVisible = false
      this.updateFormData = {}
    },
    confirmUpdate() {
      updateNoticeById(this.updateFormData).then(response => {
        if(response.code !== 200) return this.$message.error(response.message)
        this.$message.success(response.message)
        this.getNoticeList()
        this.updateDialogVisible = false
      }).catch(error => {
        this.$message.error(error)
      })
    }
  },
  mounted() {
    this.getNoticeList()
  }
}
</script>

<style scoped>

</style>
