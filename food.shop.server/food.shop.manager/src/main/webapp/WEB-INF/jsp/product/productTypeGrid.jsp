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
<style type="text/css">
.el-breadcrumb {
	margin-top: 10px;
	height: 14px;
}
</style>
</head>
<body>
<div id="app">
	<el-container>
		<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
		<el-container :style="containerStyle2">
			<jsp:include page="/WEB-INF/jsp/common/menu.jsp" />
	  		<el-main>
	  			<el-form :inline="true" :model="filterParams" ref="filterParams" label-width="80px" label-position="left" @keyup.native.enter="handleQuery">
					<el-form-item label="显示" prop="show"> 
						<el-select v-model="filterParams.show" @change="handleQuery">
							<el-option v-for="showItem in filterOptions.show" :key="showItem.value" :label="showItem.text" :value="showItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item label="促销" prop="promotion"> 
						<el-select v-model="filterParams.promotion" @change="handleQuery">
							<el-option v-for="promotionItem in filterOptions.promotion" :key="promotionItem.value" :label="promotionItem.text" :value="promotionItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item> 
						<el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
						<el-button icon="icon iconfont icon-qingkong" @click="handleClearQuery('filterParams','table')">清空</el-button>
					</el-form-item> 
				</el-form>
	  			<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddForm('editFormEntity')">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete">删除</el-button> 
				<el-breadcrumb separator="/">
					<el-breadcrumb-item v-for="item in breadcrumbOptions" @click.native="to(item.id)">{{item.text}}</el-breadcrumb-item> 
				</el-breadcrumb>
				<el-table ref="table" :data="tableData" :default-sort="defaultSort" @selection-change="handleSelection" @sort-change="handleSort" stripe style="width:100%;">
					<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
					<el-table-column fixed prop="name" label="产品分类" width="120"></el-table-column>
					<el-table-column prop="imageUrl" label="分类图标" width="80">
						<template slot-scope="scope">
			        		<image :src="scope.row.imageUrl" width="70" />
			      		</template>
					</el-table-column>
					<el-table-column prop="show" label="是否显示" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.show">
								显示
							</template>
							<template v-else>
								隐藏
							</template>
			      		</template>
					</el-table-column>
					<el-table-column prop="promotion" label="是否促销" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.promotion">
								是
							</template>
							<template v-else>
								否
							</template>
			      		</template>
					</el-table-column>
					<el-table-column prop="order" label="顺序" width="100" sortable="custom"></el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="160" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="300">
			      		<template slot-scope="scope">
			        		<el-button size="mini" type="primary" icon="icon iconfont icon-zilei" @click="queryChildType(scope.row)">查看子类</el-button>
			        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
			        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
			      		</template>
			    	</el-table-column>
				</el-table>
			   	<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" width="450px">
  					<el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
			    		<el-form-item label="分类名称" prop="name">
							<el-input v-model="editFormEntity.name" placeholder="分类名称"></el-input>
						</el-form-item>
						<el-form-item label="分类图标" prop="url">
							<el-upload class="avatar-uploader" :action="uploadUrl"
								:multiple="false" accept="image/*" :limit="1024" :auto-upload="true"
			  					:show-file-list="false" :on-success="handleUploadSuccess">
			  					<img v-if="editFormEntity.imageUrl" :src="editFormEntity.imageUrl" class="avatar">
			  					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
							</el-upload>
						</el-form-item>
						<el-form-item label="是否显示" prop="show">
    						<el-switch v-model="editFormEntity.show"></el-switch>
						</el-form-item>
						<el-form-item label="是否促销" prop="promotion">
    						<el-switch v-model="editFormEntity.promotion"></el-switch>
						</el-form-item>
						<el-form-item label="顺序" prop="order">
							<el-input-number v-model="editFormEntity.order" :min="0" :max="1000" label="顺序"></el-input-number>
						</el-form-item>
  					</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEdit('editFormEntity')">确 定</el-button>
			    		<el-button @click="dialogFormVisible = false">取 消</el-button>
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
	var defaultProductType =  {
		id : null,
		parentId : null,
		imageUrl : null,
		show : true,
		promotion : false,
		order : 0
	};
	
	// 创建分页表格
	var app = new Table({
		data : function() {
			return {
				defaultActive : "productType/grid.do",
				filterOptions : {// 表格下拉过滤参数
					show : [
						{text : "全部", value : null},
	          			{text : "显示", value : true},
	          			{text : "隐藏", value : false}
	          		],
	          		promotion : [
	          			{text : "全部", value : null},
	          			{text : "是", value : true},
	          			{text : "否", value : false}
	          		]
	          	},
	          	filterParams : {// 表单过滤参数
	          		parentId : null,
	          		show : null,
	          		promotion : null
	          	},
	          	rules : {// 校验规则
	          		name : [
	          			{ required : true, message: '请输入分类名称！', trigger: 'change' },
	          			{ min : 1, max : 10, message: '分类名称必须是1到10个字符！', trigger: 'change' }
	          		],
	          		imageUrl : [
	          			{ required : true, message: '请上传分类图标！', trigger: 'change' }
	          		],
	          		order : [
	          			{ required : true, message: '请输入顺序！', trigger: 'change' }
	          		]
	          	},
	          	breadcrumbOptions : [
	          		{"id" : null, "text" : "Root"}
	          	],
	          	editFormEntity : Vue.copy(defaultProductType, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultProductType,// 默认编辑表单实体
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}productType/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}productType/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}productType/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}productType/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}productType/delete.do",// 删除请求地址
	          	toggleUrl : "${urlPath}productType/toggleStatus.do",// 切换状态请求地址
	          	uploadUrl : "${urlPath}productType/uploadImage.do",// 上传请求地址
	          	moduleName : "产品分类"// 模块名称
			}
		},
		methods : {
			handleUploadSuccess : function(res) {// 处理上传成功事件
				if (res.success) {// 成功
			        this.editFormEntity.imageUrl = res.data;
				} else {// 失败
					this.$message.error(res.message);
				}
	      	},
	      	queryChildType : function(row) {
	      		this.filterParams.parentId = row.id;
	      		this.defaultEditFormEntity.parentId = row.id;
      			this.breadcrumbOptions.push({"id" : row.id, "text" : row.name});
				this.refreshData();
	      	},
	      	to : function(id) {
	      		var index = this.breadcrumbOptions.length - 1;
	      		for (var i = 0; i < this.breadcrumbOptions.length; i++) {
	      			if (this.breadcrumbOptions[i].id == id) {
	      				index = i;
	      				break;
	      			}
	      		}
	      		this.breadcrumbOptions.splice(index + 1, this.breadcrumbOptions.length - 1);
	      		this.filterParams.parentId = id;
	      		this.defaultEditFormEntity.parentId = id;
	      		this.refreshData();
	      	}
		}
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>