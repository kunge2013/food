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
	  			<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddForm('editFormEntity')">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete">删除</el-button>
				<el-button type="primary" icon="el-icon-refresh" @click="handleQuery">刷新</el-button>
				<el-table stripe style="width:100%;" ref="table" :data="tableData" :default-sort="defaultSort"
						@selection-change="handleSelection" @sort-change="handleSort">
					<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
					<el-table-column fixed prop="name" label="角色名" width="150"></el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="180" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="300">
			      		<template slot-scope="scope">
			        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
			        		<el-button size="mini" type="primary" icon="icon iconfont icon-quanxianfenpei" @click="openSetPermissionForm(scope.row)">分配权限</el-button>
			        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
			      		</template>
			    	</el-table-column>
				</el-table>
			   	<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" width="400px">
			  		<el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
			    		<el-form-item label="角色名" prop="name">
							<el-input v-model="editFormEntity.name" placeholder="角色名"></el-input>
						</el-form-item>
			  		</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEdit('editFormEntity')">确 定</el-button>
			    		<el-button @click="dialogFormVisible = false">取 消</el-button>
			  		</div>
				</el-dialog>
				<el-dialog :inline="true" title="分配权限" :visible.sync="setPermissionFormVisible" width="400px">
			  		<el-form :model="setPermissionFormEntity" ref="setPermissionFormEntity" :rules="setPermissionRules" label-width="80px" label-position="right">
			  			<el-form-item label="角色名" prop="name">
							<el-input v-model="setPermissionFormEntity.name" placeholder="角色名"></el-input>
						</el-form-item>
						<el-form-item label="权限" prop="permission">
							<el-tree ref="permissionTree" check-strictly show-checkbox default-expand-all node-key="id" :data="permissionTreeData" 
							 	 :props="defaultProps" :default-checked-keys="setPermissionFormEntity.permissionIds">
							</el-tree>
						</el-form-item>
			  		</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleSetPermission()">确 定</el-button>
			    		<el-button @click="setPermissionFormVisible = false">取 消</el-button>
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
	var defaultRole =  {
		id : null,
	  	name : null
	};
	
	// 创建分页表格
	var app = new Table({
		data : function() {
			return {
				defaultActive : "role/grid.do",
				filterOptions : {// 表格下拉过滤参数
	          	},
	          	filterParams : {// 表单过滤参数
	          		name : null
	          	},
	          	rules : {// 校验规则
	          		name : [
	          			{ required : true, message: '请输入角色名！', trigger: 'change' },
	                    { min : 1, max : 10, message: '角色名必须是1到10个字符！', trigger: 'change' }
	          		]
	          	},
	          	editFormEntity : Vue.copy(defaultRole, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultRole,// 默认编辑表单实体
	          	setPermissionFormVisible : false,// 分配权限表单是否显示
	          	setPermissionFormEntity : {// 分配权限表单参数
	          		id : "",// 角色ID
	          		name : null,// 角色名称
	          		permissionIds : []// 权限ID列表
	          	},
	          	setPermissionRules : {// 分配权限校验规则
	          		
	          	},
	          	permissionTreeData : [// 权限数
					
				],
				defaultProps : {
		        	children : "children",
			        label : "name"
		        },
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}role/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}role/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}role/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}role/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}role/delete.do",// 删除请求地址
	          	getPermissionsUrl : "${urlPath}role/getRolePermissions.do",// 查询权限请求URL
	          	setPermissionsUrl : "${urlPath}role/setRolePermissions.do",// 分配权限请求URL
	          	moduleName : "角色"// 模块名称
			}
		},
		methods : {
			openSetPermissionForm : function(row) {// 打开设置权限表单
				// 获取实例对象
	      		var _this = this;
	      		
	      		// 重置表单
				if (_this.$refs["setPermissionFormEntity"]) {
					_this.$refs["setPermissionFormEntity"].resetFields();
				}
	      		
	      		// 请求角色数据
	      		_this.doGetData(_this.getPermissionsUrl, {id : row.id}, function(r) {
	      			// 重置表单数据
	      			var data = r.data;
	      			_this.setPermissionFormEntity = Vue.copy({id : data.id, name : data.name, permissionIds : data.permissionIds});
		      		_this.permissionTreeData = data.permissionTreeData;
					// 显示表单
					_this.setPermissionFormVisible = true;
	      		})
			},
			handleSetPermission : function() {// 分配权限
				// 获取实例对象
	      		var _this = this;
				// 获取树对象
				var _tree = _this.$refs.permissionTree;
				// 获取选中的节点的Key列表
				var permissionIds = _tree.getCheckedKeys();
				// 获取角色ID
				var roleId = _this.setPermissionFormEntity.id;
				// 发送请求
				_this.doPostData(_this.setPermissionsUrl, {id : roleId, permissionIds : permissionIds}, function(r){
					// 隐藏表单
					_this.setPermissionFormVisible = false;
				});
			}
		}
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>