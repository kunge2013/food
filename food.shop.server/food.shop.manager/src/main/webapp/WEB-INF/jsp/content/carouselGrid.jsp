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
					<el-form-item label="类型" prop="type"> 
						<el-select v-model="filterParams.type" placeholder="请选择类型" @change="handleQuery">
							<el-option label="全部" value=""></el-option>
							<el-option v-for="typeItem in filterOptions.type" :key="typeItem.value" :label="typeItem.text" :value="typeItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item label="平台" prop="platform"> 
						<el-select v-model="filterParams.platform" placeholder="请选择平台" @change="handleQuery"> 
							<el-option label="全部" value=""></el-option>
							<el-option v-for="platformItem in filterOptions.platform" :key="platformItem.value" :label="platformItem.text" :value="platformItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item> 
						<el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
						<el-button icon="icon iconfont icon-qingkong" @click="handleClearQuery('filterParams','table')">清空</el-button>
					</el-form-item> 
				</el-form>
	  			<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddForm('editFormEntity')">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete">删除</el-button>
				<el-table ref="table" :data="tableData" :default-sort="defaultSort" @selection-change="handleSelection" @sort-change="handleSort" stripe style="width:100%;">
					<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
					<el-table-column fixed prop="url" label="轮播图" width="100">
						<template slot-scope="scope">
							<image :src="scope.row.url" width="70" />
						</template>
					</el-table-column>
					<el-table-column prop="platform" label="平台" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.platform == 'P'">
								官网
							</template>
							<template v-else-if="scope.row.platform == 'M'">
								APP
							</template>
			      		</template>
					</el-table-column>
					<el-table-column prop="type" label="类型" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.type == 'C'">
								普通
							</template>
							<template v-else-if="scope.row.type == 'L'">
								链接
							</template>
			      		</template>
					</el-table-column>
					<el-table-column prop="targetUrl" label="目标地址" width="250" show-overflow-tooltip></el-table-column>
					<el-table-column prop="order" label="顺序" width="100" sortable="custom"></el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="160" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="185">
			      		<template slot-scope="scope">
			        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
			        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
			      		</template>
			    	</el-table-column>
				</el-table>
			   	<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" width="360px">
  					<el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
			    		<el-form-item label="平台" prop="platform">
							<el-select v-model="editFormEntity.platform" placeholder="请选择平台"> 
								<el-option v-for="platformItem in filterOptions.platform" :key="platformItem.value" :label="platformItem.text" :value="platformItem.value"></el-option> 
							</el-select>
						</el-form-item>
			    		<el-form-item label="类型" prop="type">
							<el-select v-model="editFormEntity.type" placeholder="请选择类型"> 
								<el-option v-for="typeItem in filterOptions.type" :key="typeItem.value" :label="typeItem.text" :value="typeItem.value"></el-option> 
							</el-select>
						</el-form-item>
						<el-form-item label="目标地址" prop="targetUrl" v-if="editFormEntity.type == 'L'">
							<el-input v-model="editFormEntity.targetUrl" placeholder="目标地址"></el-input>
						</el-form-item>
						<el-form-item label="轮播图" prop="url">
							<el-upload class="avatar-uploader" :action="uploadUrl"
								:multiple="false" accept="image/*" :limit="1024" :auto-upload="true"
			  					:show-file-list="false" :on-success="handleUploadSuccess">
			  					<img v-if="editFormEntity.url" :src="editFormEntity.url" class="avatar">
			  					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
							</el-upload>
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
	var defaultCarousel =  {
		id : null,
		type : "C",
		platform : "M",
		targetUrl : null,
		url : null,
		order : 0
	};
	
	// 创建分页表格
	var app = new Table({
		data : function() {
			return {
				defaultActive : "carousel/grid.do",
				filterOptions : {// 表格下拉过滤参数
					type : [
	          			{text : "普通", value : "C"},
	          			{text : "链接", value : "L"}
	          		],
	          		platform : [
	          			{text : "官网", value : "P"},
	          			{text : "APP", value : "M"}
	          		]
	          	},
	          	filterParams : {// 表单过滤参数
	          		type : null,
	          		platform : null
	          	},
	          	rules : {// 校验规则
	        		type : [
	          			{ required : true, message: '请选择轮播图类型！', trigger: 'change' }
	          		],
	          		platform : [
	          			{ required : true, message: '请选择平台！', trigger: 'change' }
	          		],
	          		url : [
	          			{ required : true, message: '请上传轮播图！', trigger: 'blur' }
	          		],
	          		order : [
	          			{ required : true, message: '请输入顺序！', trigger: 'change' }
	          		],
	          		targetUrl : [
	          			{ validator : function(rule, value, callback) {
	    	  				if (app.editFormEntity.type == "L") {
	    	  					// 链接轮播图
	    	  					if (value == null || value.trim() == "") {
	    	  						callback(new Error('请输入目标地址!'));
	    	  					} else if (!Vue.checkUrl(value)) {
	    	  						callback(new Error('请输入正确的目标地址!'));
	    	  					} else {
		    	  			 		callback();
		    	  			 	}
	    	  			 	} else {
	    	  			 		callback();
	    	  			 	}
	    	  			}, trigger: 'change' }
	          		]
	          	},
	          	editFormEntity : Vue.copy(defaultCarousel, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultCarousel,// 默认编辑表单实体
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}carousel/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}carousel/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}carousel/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}carousel/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}carousel/delete.do",// 删除请求地址
	          	toggleUrl : "${urlPath}carousel/toggleStatus.do",// 切换状态请求地址
	          	uploadUrl : "${urlPath}carousel/uploadImage.do",// 上传请求地址
	          	moduleName : "轮播图"// 模块名称
			}
		},
		methods : {
			handleUploadSuccess : function(res) {// 处理上传成功事件
				if (res.success) {// 成功
			        this.editFormEntity.url = res.data;
				} else {// 失败
					this.$message.error(res.message);
				}
	      	}
		}
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>