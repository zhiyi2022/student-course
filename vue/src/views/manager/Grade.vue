<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 260px; margin-right: 10px" v-model="data.courseName" placeholder="请输入课程查询名称" prefix-icon="Search"></el-input>
      <el-input style="width: 260px; margin-right: 10px" v-model="data.studentName" placeholder="请输入学生姓名查询" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData" style="width: 100%;">
        <el-table-column prop="id" label="序号" width="80px; " />
        <el-table-column prop="courseName" label="课程名称"  />
        <el-table-column prop="studentName" label="学生名称" />
        <el-table-column prop="score" label="分数" />
        <el-table-column prop="comment" label="评语" />
        <el-table-column prop="feedback" label="反馈" />
        <el-table-column label="操作" width="180px">
          <template v-slot="scope">
            <el-button type="primary" @click="handleEdit(scope.row)" v-if="data.user.role === 'ADMIN'">编辑</el-button>
            <el-button type="danger" @click="del(scope.row.id)" v-if="data.user.role === 'ADMIN'">删 除</el-button>
            <el-button type="primary" @click="handleEdit(scope.row)" v-if="data.user.role === 'STUDENT'">反 馈</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next" :total="data.total"
                     v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                     @current-change="handleCurrentChange"/>   <!-- 分页查询组件 -->
    </div>

    <el-dialog title="反馈信息" width="35%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="评分" v-if="data.user.role === 'ADMIN'">
          <el-input v-model="data.form.score" autocomplete="off" />
        </el-form-item>
        <el-form-item label="评语" v-if="data.user.role === 'ADMIN'">
          <el-input type="textarea" v-model="data.form.comment" autocomplete="off" />
        </el-form-item>
        <el-form-item label="反馈" v-if="data.user.role === 'STUDENT'">
          <el-input type="textarea" v-model="data.form.feedback" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="data.formVisible = false">取 消</el-button>
            <el-button type="primary" @click="save" >保 存</el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {Search} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";

request.get('/').then(res => {
  // console.log(res)
})

const data = reactive({
  form: {},
  courseName: '',
  studentName: '',
  tableData: [],
  total: 0,
  pageNum: 1,     //当前的页码
  pageSize: 5,     //每页的个数
  user: JSON.parse(localStorage.getItem('student-user') || '{}'),
  formVisible: false,  //弹窗控制
})
/**
 const load = () => {
  const {student, ...the_params} = data
  request.get('/studentCourse/selectPage',{
    params: the_params
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}*/
const load = () => {
  let params = {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    courseName: data.courseName,
    studentName: data.studentName
  }
  if (data.user.role === 'STUDENT') {   //如果登录的是学生，就查询自己的选课记录
    params.studentId = data.user.id     //当身份是学生时，拼接StudentId， 是管理员就不拼接
  }
  request.get('/grade/selectPage',{
    params: params
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

//调用方法获取后台数据
load()

const handleCurrentChange = () => {
  // 当翻页的时候重新加载数据即可
  load()
}

const reset = () => {
  data.courseName = ''
  data.studentName = ''
  load()
}

const del = (id) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/studentCourse/delete/' + id).then(res => {
      if (res.code === '200') {
        load()     // 重新获取数据
        ElMessage.success("操作成功")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

//打分
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))  //拷贝行数据到表单对象
  data.formVisible = true
}

const save = () => {
  request.put('/grade/update', data.form).then(res => {
    if (res.code === '200') {
      load()   //更新数据
      data.formVisible = false    // 关闭弹窗
      ElMessage.success("操作成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>