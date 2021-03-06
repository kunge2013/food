<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<el-header class="admin-header">
	<div class="admin-logo">后台管理系统</div>
	<div class="admin-header-menu">
		<img :src="curSysUser.image" height="40px;" style="vertical-align: middle;">
		<el-dropdown @command="clickSysUserMenu">
 				<span class="el-dropdown-link">
   				{{curSysUser.realName}}<i class="el-icon-arrow-down el-icon--right"></i>
 				</span>
 				<el-dropdown-menu slot="dropdown">
			    <el-dropdown-item command="showMyInfo">个人信息</el-dropdown-item>
			    <el-dropdown-item command="showChangePassword">修改密码</el-dropdown-item>
			    <el-dropdown-item command="logout">注销</el-dropdown-item>
			</el-dropdown-menu>
		</el-dropdown>
	</div>
	<el-dialog :inline="true" title="个人信息" :visible.sync="setMyInfoFormVisible" width="400px">
  		<el-form :model="setMyInfoEntity" ref="setMyInfoEntity" :rules="setMyInfoRules" label-width="80px" label-position="right">
    		<el-form-item label="用户名" prop="username">
				<el-input v-model="setMyInfoEntity.username" placeholder="用户名" readonly></el-input>
			</el-form-item>
			<el-form-item label="真实姓名" prop="realName">
				<el-input v-model="setMyInfoEntity.realName" placeholder="真实姓名"></el-input>
			</el-form-item>
			<el-form-item label="头像" prop="image">
				<el-upload class="avatar-uploader" :action="uploadMyInfoUrl"
					:multiple="false" accept="image/*" :limit="1024" :auto-upload="true"
  					:show-file-list="false" :on-success="handleUploadMyImageSuccess">
  					<img v-if="setMyInfoEntity.image" :src="setMyInfoEntity.image" class="avatar">
  					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
			</el-form-item>
			<el-form-item label="性别" prop="sex">
				<el-radio-group v-model="setMyInfoEntity.sex">
      				<el-radio label="M">男</el-radio>
      				<el-radio label="F">女</el-radio>
      				<el-radio label="U">保密</el-radio>
    			</el-radio-group>
			</el-form-item>
			<el-form-item label="出生年月" prop="birthday">
				<el-date-picker v-model="setMyInfoEntity.birthday" type="date" :editable="false" placeholder="选择日期"></el-date-picker>
			</el-form-item>
			<el-form-item label="手机号" prop="telephone">
				<el-input v-model="setMyInfoEntity.telephone" placeholder="手机号"></el-input>
			</el-form-item>
			<el-form-item label="邮箱地址" prop="email">
				<el-input v-model="setMyInfoEntity.email" placeholder="邮箱地址"></el-input>
			</el-form-item>
  		</el-form>
  		<div slot="footer" class="dialog-footer">
    		<el-button type="primary" @click="handleSetMyInfo()">确 定</el-button>
    		<el-button @click="setMyInfoFormVisible = false">取 消</el-button>
  		</div>
	</el-dialog>
	<el-dialog :inline="true" title="修改密码" :visible.sync="changePasswordEntityFormVisible" width="400px">
  		<el-form :model="changePasswordEntity" ref="changePasswordEntity" :rules="changePasswordEntityRules" label-width="80px" label-position="right">
    		<el-form-item label="用户名" prop="username">
				<el-input v-model="changePasswordEntity.username" placeholder="用户名" readonly></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="password">
				<el-input type="password" v-model="changePasswordEntity.password" placeholder="密码"></el-input>
			</el-form-item>
			<el-form-item label="确认密码" prop="repassword">
				<el-input type="password" v-model="changePasswordEntity.repassword" placeholder="确认密码"></el-input>
			</el-form-item>
  		</el-form>
  		<div slot="footer" class="dialog-footer">
    		<el-button type="primary" @click="handleChangePassword()">确 定</el-button>
    		<el-button @click="setPasswordEntityFormVisible = false">取 消</el-button>
  		</div>
	</el-dialog>
</el-header>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            