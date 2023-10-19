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
        :data="forumList"
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
          prop="userId"
          label="发布者ID"
          width="180">
        </el-table-column>
        <el-table-column
          prop="content"
          label="论坛内容"
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
          <el-form-item label="论坛内容" label-width="80px">
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
import {updateForumById} from "@/api/forum";

export default {
  name: "Forum",
  data() {
    return {
      deleteArr: [],
      forumList: [],
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

        this.$store.dispatch('forum/deleteForums', deleteData).then((response) => {
          this.$message.success(response.message)
          this.getForumList()
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
    getForumList() {
      this.$store.dispatch('forum/getForumList',{
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then((response) => {
        this.forumList = response.data.data
      }).catch(error => {
        this.$message.error(error)
      })
    },
    cancelInsert() {
      this.insertFormData = {}
      this.insertDialogVisible = false
    },
    confirmInsert() {
      this.insertFormData.userId = this.$store.state.user.id
      this.$store.dispatch('forum/insertForum', this.insertFormData).then(response => {
        this.$message.success(response.message)
        this.getForumList()
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
      this.getForumList()
      this.updateDialogVisible = false
      this.updateFormData = {}
    },
    confirmUpdate() {
      updateForumById(this.updateFormData).then(response => {
        if(response.code !== 200) return this.$message.error(response.message)
        this.$message.success(response.message)
        this.getForumList()
        this.updateDialogVisible = false
      }).catch(error => {
        this.$message.error(error)
      })
    }
  },
  mounted() {
    this.getForumList()
  }
}
</script>

<style scoped>

</style>
