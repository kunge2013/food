<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>${title }</title>
<!-- 引入ElementUI样式库 -->
<link rel="stylesheet" href="${urlPath }css/element-ui.css">
<link rel="stylesheet" href="${urlPath }css/display.css">
<!-- 阿里巴巴矢量图标库 -->
<link rel="stylesheet" href="${urlPath }css/iconfont/iconfont.css">
<!-- 系统公共样式 -->
<link rel="stylesheet" href="${urlPath }css/base.css">
</head>
<body>
<div id="app">
	<el-container>
		<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
		<el-container :style="containerStyle2">
			<jsp:include page="/WEB-INF/jsp/common/menu.jsp" />
	  		<el-main>
				<el-form :inline="true" :model="filterParams" ref="filterParams" label-width="80px" label-position="left" @keyup.native.enter="handleQuery">
					<el-form-item label="用户名" prop="username">
						<el-input v-model="filterParams.username" placeholder="用户名" ></el-input>
					</el-form-item>
					<el-form-item label="姓名" prop="realName">
						<el-input v-model="filterParams.realName" placeholder="姓名"></el-input>
					</el-form-item>
					<el-form-item label="性别" prop="sex"> 
						<el-select v-model="filterParams.sex" placeholder="请选择性别" @change="handleQuery"> 
							<el-option v-for="sexItem in filterOptions.sex" :key="sexItem.value" :label="sexItem.text" :value="sexItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item label="状态" prop="status"> 
						<el-select v-model="filterParams.status" placeholder="请选择状态" @change="handleQuery"> 
							<el-option v-for="statusItem in filterOptions.status" :key="statusItem.value" :label="statusItem.text" :value="statusItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item label="手机号" prop="telephone">
						<el-input v-model="filterParams.telephone" placeholder="手机号"></el-input>
					</el-form-item>
					<el-form-item label="邮箱地址" prop="email">
						<el-input v-model="filterParams.email" placeholder="邮箱地址"></el-input>
					</el-form-item>
					<el-form-item> 
						<el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
						<el-button icon="icon iconfont icon-qingkong" @click="handleClearQuery('filterParams','table')">清空</el-button>
					</el-form-item> 
				</el-form>
				<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddForm('editFormEntity')">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete">删除</el-button>
				<el-table stripe style="width:100%;" ref="table" :data="tableData" :default-sort="defaultSort"
						@selection-change="handleSelection" @sort-change="handleSort">
					<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
					<el-table-column fixed prop="username" label="用户名" width="150"></el-table-column>
					<el-table-column prop="image" label="头像" width="80">
						<template slot-scope="scope">
							<image :src="scope.row.image" width="70" />
						</template>
					</el-table-column>
					<el-table-column prop="realName" label="真实姓名" width="120"></el-table-column>
					<el-table-column prop="sex" label="性别" width="80">
						<template slot-scope="scope">
							<template v-if="scope.row.sex == 'M'">
								男
							</template>
							<template v-else-if="scope.row.sex == 'F'">
								女
							</template>
							<template v-else>
								保密
							</template>
						</template>
					</el-table-column>
					<el-table-column prop="status" label="状态" width="80">
						<template slot-scope="scope">
							<template v-if="scope.row.status == 'Y'">
								激活
							</template>
							<template v-else>
								禁用
							</template>
						</template>
					</el-table-column>
					<el-table-column prop="birthday" label="出生年月" width="150" sortable="custom"></el-table-column>
					<el-table-column prop="telephone" label="手机号" width="150"></el-table-column>
					<el-table-column prop="email" label="邮箱地址" width="200" show-overflow-tooltip></el-table-column> 
					<el-table-column prop="creatorUsername" label="创建者" width="120"></el-table-column> 
					<el-table-column prop="createTime" label="创建时间" width="180" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="410">
			      		<template slot-scope="scope">
			        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
			        		<el-button size="mini" type="primary" icon="icon iconfont icon-roleSet" @click="openSetRoleForm(scope.row)">设置角色</el-button>
			        		<el-button size="mini" type="success" icon="icon iconfont icon-jihuopeizhi" @click="handleEnable(scope.row.id)" v-if="scope.row.status == 'N'">激活</el-button>
			        		<el-button size="mini" type="danger" icon="icon iconfont icon-jinyong" @click="handleDisable(scope.row.id)" v-if="scope.row.status == 'Y'">禁用</el-button>
			        		<el-button size="mini" type="danger" icon="icon iconfont icon-zhongzhimima" @click="handleResetPassword(scope.row.id)">重置密码</el-button>
			        		<!-- <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button> -->
			      		</template>
			    	</el-table-column>
				</el-table>
				<el-pagination background highlight-current-row
					@size-change="handleSizeChange" @current-change="handleCurChange"
					:current-page="curPage" :page-size="pageSize" :page-sizes="pageSizes" :total="total" 
			     	layout="prev, pager, next, total, sizes, jumper">
			   	</el-pagination>
			   	<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible">
			  		<el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
			    		<el-form-item label="用户名" prop="username">
							<el-input v-model="editFormEntity.username" placeholder="用户名" :readonly="editFormEntity.id!=null"></el-input>
						</el-form-item>
						<template v-if="editFormEntity.id==null">
			    		<el-form-item label="密码" prop="password">
							<el-input type="password" v-model="editFormEntity.password" placeholder="密码"></el-input>
						</el-form-item>
						<el-form-item label="确认密码" prop="repassword">
							<el-input type="password" v-model="editFormEntity.repassword" placeholder="确认密码"></el-input>
						</el-form-item>
						</template>
						<el-form-item label="真实姓名" prop="realName">
							<el-input v-model="editFormEntity.realName" placeholder="真实姓名"></el-input>
						</el-form-item>
						<el-form-item label="头像" prop="image">
							<el-upload class="avatar-uploader" :action="uploadUrl"
								:multiple="false" accept="image/*" :limit="1024" :auto-upload="true"
			  					:show-file-list="false" :on-success="handleUploadSuccess">
			  					<img v-if="editFormEntity.image" :src="editFormEntity.image" class="avatar">
			  					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
							</el-upload>
						</el-form-item>
						<el-form-item label="性别" prop="sex">
							<el-radio-group v-model="editFormEntity.sex">
			      				<el-radio label="M">男</el-radio>
			      				<el-radio label="F">女</el-radio>
			      				<el-radio label="U">保密</el-radio>
			    			</el-radio-group>
						</el-form-item>
						<el-form-item label="出生年月" prop="birthday">
							<el-date-picker v-model="editFormEntity.birthday" type="date" :editable="false" placeholder="选择日期"></el-date-picker>
						</el-form-item>
						<el-form-item label="手机号" prop="telephone">
							<el-input v-model="editFormEntity.telephone" placeholder="手机号"></el-input>
						</el-form-item>
						<el-form-item label="邮箱地址" prop="email">
							<el-input v-model="editFormEntity.email" placeholder="邮箱地址"></el-input>
						</el-form-item>
			  		</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEdit('editFormEntity')">确 定</el-button>
			    		<el-button @click="dialogFormVisible = false">取 消</el-button>
			  		</div>
				</el-dialog>
				<el-dialog :inline="true" title="设置角色" :visible.sync="setRoleFormVisible" width="400px">
			  		<el-form :model="setRoleFormEntity" ref="setRoleFormEntity" :rules="setRoleRules" label-width="80px" label-position="right">
			    		<el-form-item label="用户名" prop="username">
							<el-input v-model="setRoleFormEntity.username" placeholder="用户名" readonly></el-input>
						</el-form-item>
						<el-form-item label="角色" prop="roleIds">
							<el-select v-model="setRoleFormEntity.roleIds" placeholder="请选择角色" multiple style="width:100%;">
			    				<el-option v-for="roleItem in roleOptions" :key="roleItem.value" :label="roleItem.label" :value="roleItem.value"></el-option>
			  				</el-select>
						</el-form-item>
			  		</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleSetRole()">确 定</el-button>
			    		<el-button @click="setRoleFormVisible = false">取 消</el-button>
			  		</div>
				</el-dialog>
			</el-main>
		</el-container>
	</el-container>
</div>
<!-- 引入Vue库 -->
<script type="text/javascript" src="${urlPath }js/vue.2.5.13.js"></script>
<!-- 引入ElementUI库 -->
<script type="text/javascript" src="${urlPath }js/element-ui.min.js"></script>
<!-- 引入Ajax库 -->
<script type="text/javascript" src="${urlPath }js/axios.min.js"></script>
<script type="text/javascript" src="${urlPath }js/qs.js"></script>
<!-- 引入基础库 -->
<script type="text/javascript" src="${urlPath }js/global.js"></script>
<script type="text/javascript">
	// 表单默认参数
	var defaultSysUser =  {
		id : null,
	  	username : null,
	  	password : null,
	  	repassword : null,
	  	image : null,
	  	realName : null,
	  	birthday : null,
	  	sex : "M",
	  	telephone : null,
	  	email : null
	};
	
	//系统用户验证规则
	var SYS_USER_RULES = {
	  		username : [
	  			{ required : true, message: '请输入用户名！', trigger: 'change' },
	            { min : 6, max : 20, message: '用户名必须是6到20个字符！', trigger: 'change' }
	  		],
	  		password : [
	  			{ required : true, message: '请输入密码！', trigger: 'change' },
	            { min : 6, max : 20, message: '密码必须是6到20个字符！', trigger: 'change' }
	  		],
	  		repassword : [
	  			{ required: true, message: '请再次输入密码！', trigger: 'change' },
	  			{ min : 6, max : 20, message: '密码必须是6到20个字符！', trigger: 'change' },
	  			{ validator : function(rule, value, callback) {
	  				if (value != app.editFormEntity.password) {
	  					callback(new Error('两次输入密码不一致!'));
	  			 	} else {
	  			 		callback();
	  			 	}
	  			}, trigger: 'change' }
	  		],
	  		image : [
	  			{ required : true, message: '请选择头像！', trigger: 'blur' }
	  		],
	  		realName : [
	  			{ required : true, message: '请输入真实姓名！', trigger: 'change' },
	            { min : 1, max : 20, message: '真实姓名必须是1到10个字符！', trigger: 'change' }
	  		],
	  		birthday : [
	  			{ required: true, message: '请选择出生年月！', trigger: 'change' }
	  		],
	  		sex : [
	  			{ required: true, message: '请选择性别！', trigger: 'change' }
	  		],
	  		telephone : [
	  			{ required : true, message: '请输入手机号码！', trigger: 'change' },
	            { min : 11, max : 11, message: '手机号码必须是11个字符！', trigger: 'change' }
	  		],
	  		email : [
	  			{ required : true, message: '请输入邮箱地址！', trigger: 'change' },
	  			{ min : 1, max : 100, message: '邮箱地址必须是1到100个字符！', trigger: 'change' },
	  			{ type: 'email', required: true, message: '请输入合法邮箱地址！', trigger: 'change' }
	  		]
	  	};
	
	// 创建分页表格
	var app = new PageTable({
		data : function() {
			return {
				defaultActive : "sysUser/grid.do",
				filterOptions : {// 表格下拉过滤参数
	          		sex : [
	          			{text : "全部", value : ""},
	          			{text : "男", value : "M"},
	          			{text : "女", value : "F"},
	          			{text : "保密", value : "U"}
	          		],
	          		status : [
	          			{text : "全部", value : ""},
	          			{text : "激活", value : "Y"},
	          			{text : "禁用", value : "N"}
	          		]
	          	},
	          	filterParams : {// 表单过滤参数
	          		username : "",
	              	realName : "",
	              	telephone : "",
	              	email : "",
	              	sex : [],
	      			status : []
	          	},
	          	rules : SYS_USER_RULES,
	          	editFormEntity : Vue.copy(defaultSysUser, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultSysUser,// 默认编辑表单实体
	          	setRoleFormVisible : false,// 设置角色表单是否显示
	          	setRoleFormEntity : {
	          		id : null,// 用户ID
	          		username : null,// 用户名称
	          		roleIds : []// 角色ID列表
	          	},
	          	roleOptions : [],// 角色选项列表
	          	setRoleRules : {// 设置角色校验规则
	          		
	          	},
	          	baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}sysUser/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}sysUser/get.do",// 获取实体请求地址
	          	addUrl : "${urlPath}sysUser/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}sysUser/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}sysUser/delete.do",// 删除请求地址
	          	toggleUrl : "${urlPath}sysUser/toggleStatus.do",// 切换状态请求地址
	          	uploadUrl : "${urlPath}sysUser/uploadImage.do",// 上传请求地址
	          	getRolesUrl : "${urlPath}sysUser/getSysUserRoles.do",// 获取角色请求地址
	          	setRolesUrl : "${urlPath}sysUser/setSysUserRoles.do",// 设置角色请求地址
	          	resetPasswordUrl : "${urlPath}sysUser/resetPassword.do",// 重置系统用户密码
	          	moduleName : "系统用户"// 模块名称
			}
		},
		methods : {// 方法
			handleUploadSuccess : function(res) {// 处理上传成功事件
				if (res.success) {// 成功
			        this.editFormEntity.image = res.data;
				} else {// 失败
					this.$message.error(res.message);
				}
	      	},
	      	openSetRoleForm : function(row) {// 打开设置角色表单
	      		// 获取实例对象
	      		var _this = this;
	      		
	      		// 重置表单
				if (_this.$refs["setRoleFormEntity"]) {
					_this.$refs["setRoleFormEntity"].resetFields();
				}
	      	
	      		// 请求角色数据
	      		_this.doGetData(_this.getRolesUrl, {id : row.id}, function(r) {
	      			// 重置表单数据
	      			var data = r.data;
					_this.setRoleFormEntity = Vue.copy({id : data.id, username : data.username, roleIds : data.roleIds});
		      		_this.roleOptions = data.roleOptions; 
					// 显示表单
					_this.setRoleFormVisible = true;
	      		})
	      	},
			handleSetRole : function() {
				// 获取实例对象
	      		var _this = this;
				
				// 获取表单数据
				var params = _this.$data["setRoleFormEntity"];
				
				// 发送请求
				_this.doPostData(_this.setRolesUrl, params, function(r) {
					// 关闭对话框
					_this.setRoleFormVisible = false;
				});
			},
			handleResetPassword : function(sysUserId) {
				// 获取实例对象
	      		var _this = this;
				
				_this.confirm("确认该系统用户重置密码？", function(){
					_this.doPostData(_this.resetPasswordUrl, {sysUserId : sysUserId});
				});
			}
		}
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>