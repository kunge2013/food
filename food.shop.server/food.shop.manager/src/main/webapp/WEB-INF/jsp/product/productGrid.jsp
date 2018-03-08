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
.intro .el-form-item__content {
	line-height: unset;
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
					<el-form-item label="名称" prop="name"> 
						<el-input v-model="filterParams.name" placeholder="产品名称"></el-input>
					</el-form-item>
					<el-form-item label="分类" prop="type"> 
						<el-select v-model="filterParams.type" @change="handleQuery">
							<el-option v-for="typeItem in filterOptions.type" :key="typeItem.value" :label="typeItem.text" :value="typeItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item label="在售" prop="sale"> 
						<el-select v-model="filterParams.sale" @change="handleQuery">
							<el-option v-for="saleItem in filterOptions.sale" :key="saleItem.value" :label="saleItem.text" :value="saleItem.value"></el-option> 
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
				<el-table ref="table" :data="tableData" :default-sort="defaultSort" @selection-change="handleSelection" @sort-change="handleSort" stripe style="width:100%;">
					<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
					<el-table-column fixed prop="name" label="产品名称" width="120"></el-table-column>
					<el-table-column prop="imageUrl" label="产品图片" width="80">
						<template slot-scope="scope">
			        		<image :src="scope.row.imageUrl" width="70" />
			      		</template>
					</el-table-column>
					<el-table-column fixed prop="typeName" label="所属分类" width="120"></el-table-column>
					<el-table-column prop="sale" label="是否在售" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.sale">
								在售
							</template>
							<template v-else>
								下架
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
					<el-table-column prop="salesVolume" label="销量" width="100" sortable="custom"></el-table-column>
					<el-table-column prop="order" label="顺序" width="100" sortable="custom"></el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="160" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="300">
			      		<template slot-scope="scope">
			        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
			        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
			      		</template>
			    	</el-table-column>
				</el-table>
			   	<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" fullscreen @open="initUEditor" @close="closeUEditor">
  					<el-form :inline="true" :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
			    		<el-form-item label="产品名称" prop="name">
							<el-input v-model="editFormEntity.name" placeholder="产品名称"></el-input>
						</el-form-item>
						<!-- 产品分类 -->
						<el-form-item label="产品分类" prop="type">
							<el-select v-model="editFormEntity.type" placeholder="请选择产品分类" style="width:100%;">
			    				<el-option v-for="typeItem in typeOptions" :key="typeItem.id" :label="typeItem.name" :value="typeItem.id"></el-option>
			  				</el-select>
						</el-form-item>
						<el-form-item label="顺序" prop="order">
							<el-input-number v-model="editFormEntity.order" :min="0" :max="1000" label="顺序"></el-input-number>
						</el-form-item>
						<el-form-item label="是否在售" prop="sale">
    						<el-switch v-model="editFormEntity.sale"></el-switch>
						</el-form-item>
						<el-form-item label="是否促销" prop="promotion">
    						<el-switch v-model="editFormEntity.promotion"></el-switch>
						</el-form-item>
						<!-- 产品图片上传 -->
						<el-form-item label="上传图片" prop="imageUrls">
							<el-upload :action="uploadUrl" accept="image/*" list-type="picture-card" :file-list="productImageList"
								:on-preview="handlePictureCardPreview" :on-remove="handleRemove" :on-success="handleUploadSuccess">
  								<i class="el-icon-plus"></i>
							</el-upload>
							<!-- 产品图片预览列表 -->
							<el-dialog :visible.sync="dialogImageVisible" size="tiny">
  								<img width="100%" :src="dialogImageUrl">
							</el-dialog>
						</el-form-item>
						<!-- 介绍 -->
						<el-form-item label="产品简介" class="intro" prop="intro">
							<script id="ueditor" type="text/plain">
        						{{editFormEntity.intro}}
    						</script>
						</el-form-item>
  					</el-form>
  					<!-- 产品规格 -->
  					<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddSpecForm">添加产品规格</el-button>
					<el-table ref="specTable" :data="specs" stripe style="width:100%;">
						<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
						<el-table-column fixed prop="unit" label="规格单位" width="160"></el-table-column>
						<el-table-column fixed prop="price" label="规格单价" width="160"></el-table-column>
						<el-table-column fixed prop="stock" label="规格库存" width="160"></el-table-column>
						<el-table-column prop="measureUnit" label="计量单位" width="160"></el-table-column>
						<el-table-column prop="measurePrice" label="计量单价" width="160"></el-table-column>
						<!-- <el-table-column prop="measureStock" label="计量库存" width="160"></el-table-column> -->
						<el-table-column prop="sale" label="是否在售" width="100">
							<template slot-scope="scope">
				        		<template v-if="scope.row.sale">
									在售
								</template>
								<template v-else>
									下架
								</template>
				      		</template>
						</el-table-column>
						<el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
						<el-table-column fixed="right" label="操作" width="185">
				      		<template slot-scope="scope">
				        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditSpecForm(scope.row)">编辑</el-button>
				        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDeleteSpec(scope.row.id)">删除</el-button>
				      		</template>
				    	</el-table-column>
					</el-table>
					<!-- 产品规格参数 -->
  					<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddSpecParamForm">添加规格参数</el-button>
					<el-table ref="specParamTable" :data="specParams" stripe style="width:100%;">
						<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
						<el-table-column fixed prop="paramName" label="参数名" width="160"></el-table-column>
						<el-table-column fixed prop="paramValue" label="参数值" width="160"></el-table-column>
						<el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
						<el-table-column fixed="right" label="操作" width="185">
				      		<template slot-scope="scope">
				        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditSpecParamForm(scope.row)">编辑</el-button>
				        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDeleteSpecParam(scope.row.id)">删除</el-button>
				      		</template>
				    	</el-table-column>
					</el-table>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEdit('editFormEntity')">确 定</el-button>
			    		<el-button @click="dialogFormVisible = false">取 消</el-button>
			  		</div>
				</el-dialog>
				<!-- 产品规格对话框 -->
				<el-dialog :title="dialogSpecFormTitle" :visible.sync="dialogSpecFormVisible" width="380px">
  					<el-form :model="editSpecFormEntity" ref="editSpecFormEntity" :rules="specRules" label-width="80px" label-position="right">
						<el-form-item label="规格单位" prop="unit">
							<el-input v-model="editSpecFormEntity.unit" placeholder="规格单位"></el-input>
						</el-form-item>
						<el-form-item label="规格单价" prop="price">
							<el-input v-model="editSpecFormEntity.price" placeholder="规格单价"></el-input>
						</el-form-item>
						<el-form-item label="规格库存" prop="stock">
							<el-input-number v-model="editSpecFormEntity.stock" :min="0" :step="100" placeholder="规格库存"></el-input-number>
						</el-form-item>
						<el-form-item label="计量单位" prop="measureUnit">
							<el-input v-model="editSpecFormEntity.measureUnit" placeholder="计量单位"></el-input>
						</el-form-item>
						<el-form-item label="计量单价" prop="measurePrice">
							<el-input v-model="editSpecFormEntity.measurePrice" placeholder="计量单价"></el-input>
						</el-form-item>
						<!-- <el-form-item label="计量库存" prop="measureStock">
							<el-input-number v-model="editSpecFormEntity.measureStock" :min="0" :step="100" placeholder="计量库存"></el-input-number>
						</el-form-item> -->
						<el-form-item label="是否在售" prop="sale">
    						<el-switch v-model="editSpecFormEntity.sale"></el-switch>
						</el-form-item>
  					</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEditSpec">确 定</el-button>
			    		<el-button @click="dialogSpecFormVisible = false">取 消</el-button>
			  		</div>
				</el-dialog>
				<!-- 产品规格参数对话框 -->
				<el-dialog :title="dialogSpecParamFormTitle" :visible.sync="dialogSpecParamFormVisible" width="380px">
  					<el-form :model="editSpecParamFormEntity" ref="editSpecParamFormEntity" :rules="specParamRules" label-width="80px" label-position="right">
						<el-form-item label="参数名" prop="paramName">
							<el-input v-model="editSpecParamFormEntity.paramName" placeholder="参数名"></el-input>
						</el-form-item>
						<el-form-item label="参数值" prop="paramValue">
							<el-input v-model="editSpecParamFormEntity.paramValue" placeholder="参数值"></el-input>
						</el-form-item>
  					</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEditSpecParam">确 定</el-button>
			    		<el-button @click="dialogSpecParamFormVisible = false">取 消</el-button>
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
<!-- UEditor富文本编辑器 -->
<script type="text/javascript" src="${urlPath }ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${urlPath }ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
	// 表单默认参数
	var defaultProduct =  {
		id : null,
		name : null,
		intro : null,
		type : null,
		imageUrl : null,
		imageUrls : [],
		sale : true,
		promotion : false,
		order : 0
	};
	
	var defaultSpec = {
		id : null,
		unit : null,// 规格单位
		price : null,// 规格单价	
		stock : null,// 库存，规格数量
		measureUnit : null,// 计量单位
		measurePrice : null,// 计量单价
		measureStock : null,// 计量数量
		sale : true// 是否销售
	};
	
	var defaultSpecParam = {
		id : null,
		paramName : null,// 规格参数名
		paramValue : null// 规格参数值	
	};
	
	// 创建分页表格
	var app = new Table({
		data : function() {
			return {
				defaultActive : "product/grid.do",
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
	          			{ required : true, message: '请输入产品名称！', trigger: 'change' },
	          			{ min : 1, max : 50, message: '产品名称必须是1到50个字符！', trigger: 'change' }
	          		],
	          		type : [
	          			{ required : true, message: '请选择产品分类！', trigger: 'change' }
	          		],
	          		intro : [
	          			{ required : true, message: '请输入产品介绍！', trigger: 'blur' },
	          			{ min : 1, max : 65535, message: '产品介绍必须是1到65535个字符！', trigger: 'blur' }
	          		],
	          		imageUrls : [
	          			{ required : true, message: '请上传产品图片！', trigger: 'change' }
	          		],
	          		order : [
	          			{ required : true, message: '请输入顺序！', trigger: 'change' }
	          		]
	          	},
	          	breadcrumbOptions : [
	          		{"id" : null, "text" : "Root"}
	          	],
	          	editFormEntity : Vue.copy(defaultProduct, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultProduct,// 默认编辑表单实体
	          	typeOptions : [],// 产品分类选项
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}product/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}product/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}product/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}product/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}product/delete.do",// 删除请求地址
	          	toggleUrl : "${urlPath}product/toggleStatus.do",// 切换状态请求地址
	          	uploadUrl : "${urlPath}product/uploadImage.do",// 上传请求地址
	          	listProductTypeUrl : "${urlPath}product/listProductType.do",// 查询产品分类列表请求地址
	          	dialogImageVisible : false,
	          	dialogImageUrl : null,
	          	productImageList : [],
	          	editSpecFormEntity : Vue.copy(defaultSpec, {}),
				defaultEditSpecFormEntity : defaultSpec,
	          	dialogSpecFormTitle : "",// 产品规格表单标题
	          	dialogSpecFormVisible : false,// 产品规格表单是否显示
	          	specRules : {// 产品规格验证参数
	          		unit : [
	          			{ required : true, message: '请输入规格单位！', trigger: 'change' },
	          			{ min : 1, max : 10, message: '规格单位必须是1到10个字符！', trigger: 'change' }
	          		],
	          		price : [
	          			{ required : true, message: '请输入规格单价！', trigger: 'change' },
	          			{ validator : function(rule, value, callback) {
	    	  				if (!Vue.checkMoney(value)) {
	    	  					callback(new Error('规格单价格式不正确!'));
	    	  			 	} else {
	    	  			 		callback();
	    	  			 	}
	    	  			}, trigger: 'change' }
	          		],
	          		stock : [
	          			{ required : true, message: '请输入规格库存！', trigger: 'change' }
	          		],
	          		measureUnit : [
	          			{ required : true, message: '请输入计量单位！', trigger: 'change' },
	          			{ min : 1, max : 10, message: '计量单位必须是1到10个字符！', trigger: 'change' }
	          		],
	          		measurePrice : [
	          			{ required : true, message: '请输入计量单价！', trigger: 'change' },
	          			{ validator : function(rule, value, callback) {
	    	  				if (!Vue.checkMoney(value)) {
	    	  					callback(new Error('计量单价格式不正确!'));
	    	  			 	} else {
	    	  			 		callback();
	    	  			 	}
	    	  			}, trigger: 'change' }
	          		]
	          	},
	    		specs : [],// 产品规格列表
	    		editSpecParamFormEntity : Vue.copy(defaultSpecParam, {}),
				defaultEditParamAttaFormEntity : defaultSpecParam,
	          	dialogSpecParamFormTitle : "",// 产品规格参数表单标题
	          	dialogSpecParamFormVisible : false,// 产品规格参数表单是否显示
	          	specParamRules : {// 产品规格验证参数
	          		paramName : [
	          			{ required : true, message: '请输入规格参数名！', trigger: 'change' },
	          			{ min : 1, max : 10, message: '规格参数名必须是1到10个字符！', trigger: 'change' }
	          		],
	          		paramValue : [
	          			{ required : true, message: '请输入规格参数值！', trigger: 'change' },
	          			{ min : 1, max : 100, message: '规格参数值必须是1到100个字符！', trigger: 'change' }
	          		]
	          	},
	    		specParams : [],// 产品规格参数列表
	          	moduleName : "产品"// 模块名称
			}
		},
		methods : {
			handleUploadSuccess : function(res, file, fileList) {// 处理上传成功事件
				if (res.success) {// 成功
					this.editFormEntity.imageUrls.push(res.data);
				} else {// 失败
					this.$message.error(res.message);
				}
	      	},
	      	handlePictureCardPreview : function(file) {
	      		this.dialogImageUrl = file.url;
	            this.dialogImageVisible = true;
	      	},
	      	handleRemove : function(file, fileList) {
	      		var imageUrls = [];
	      		if (fileList.length > 0) {
	      			for (var i = 0; i < fileList.length;i ++) {
	      				imageUrls.push(fileList[i].response.data);
	      			}
	      		}
	      		this.editFormEntity.imageUrls = imageUrls;
	      	},
	      	openAddForm : function(formName) {// 打开添加产品表单
	      		// 获取实例
	      		var _this = this;
	      		// 重置表单数据
				_this.productImageList = [];
				_this.editFormEntity = Vue.copy(_this.defaultEditFormEntity);
				// 重置表单
				_this.resetForm(formName);
	      		// 请求产品分类选项数据
				_this.doGetData(_this.listProductTypeUrl, {}, function(r) {
					_this.typeOptions = r.data;
				});
				// 表单标题
				_this.dialogFormTitle = "添加" + _this.moduleName;
				// 显示表单
				_this.dialogFormVisible = true;
			},
			openEditForm : function(formName, row) {// 打开编辑产品表单
				// 获取实例
	      		var _this = this;
	      		// 请求产品分类选项数据
				_this.doGetData(_this.listProductTypeUrl, {}, function(r) {
					_this.typeOptions = r.data;
				});
				// 重置表单
				_this.resetForm(formName);
				// 请求数据
				_this.doGetData(_this.getUrl, {id : row.id}, function(r) {// 请求数据成功回调
					// 初始化表单数据
					_this.editFormEntity = Vue.copy(r.data);
					_this.specs = r.data.specs;
					_this.specParams = r.data.params;
					_this.productImageList = [];
					for (var i = 0; i < _this.editFormEntity.imageUrls.length; i++) {
						_this.productImageList.push({
							"name" : _this.editFormEntity.imageUrls[i], 
							"url" : _this.editFormEntity.imageUrls[i], 
							"response" : {
								"data" : _this.editFormEntity.imageUrls[i]
							}
						});
					}
					// 表单标题
					_this.dialogFormTitle = "编辑" + _this.moduleName;
					// 显示表单
					_this.dialogFormVisible = true;
				});
			},
			handleEdit : function(formName) {// 保存产品信息
				// 获取实例对象
				var _this = this;
				// 获取表单数据
				var params = _this.$data[formName];
				// 获取富文本编辑器内容
				params.intro = _this.ue.getContent();
				// 验证表单数据
				_this.validate(formName, function(valid) {
	          		if (valid) {// 验证通过
	          			// 添加规格参数
	          			if (_this.specs.length == 0) {
	          				_this.confirm("至少要添加一个产品规格！");
	          				return false;
	          			}
	          			if (_this.specParams.length == 0) {
	          				_this.confirm("至少要添加一个产品规格参数！");
	          				return false;
	          			}
	          			params.specs = _this.specs;
	          			params.specParams = _this.specParams;
	              		if (params.id) {// 更新
	              			_this.doPostData(_this.updateUrl, params);
	              		} else {// 添加
	              			_this.doPostData(_this.addUrl, params);
	              		}
		            } else {// 验证失败
		              	return false;
		            }
	          	});
			},
			initUEditor : function() {// 初始化Baidu UEditor编辑器
				this.ue = UE.getEditor('ueditor', {
        			"serverUrl" : "${urlPath}controller.jsp",
        			"readonly" : false,
        			"zIndex" : 2002
        		});
			},
			closeUEditor : function() {
				
			},
			openAddSpecForm : function() {// 打开添加规格表单
				// 获取实例
	      		var _this = this;
	  			// 重置表单
	  			var formName = "editSpecFormEntity";
				_this.editSpecFormEntity = Vue.copy(_this.defaultEditSpecFormEntity);
				// 重置表单
				_this.resetForm(formName);
				// 表单标题
				_this.dialogSpecFormTitle = "添加产品规格";
				// 显示表单
				_this.dialogSpecFormVisible = true;
			},
			openEditSpecForm : function(row) {// 打开编辑规格表单
				// 获取实例
	      		var _this = this;
	  			// 重置表单
	  			var formName = "editSpecFormEntity";
				_this.editSpecFormEntity = Vue.copy(row);
				_this.specs = _this.specs || [];
				// 重置表单
				_this.resetForm(formName);
				// 表单标题
				_this.dialogSpecFormTitle = "修改产品规格";
				// 显示表单
				_this.dialogSpecFormVisible = true;
			},
			handleEditSpec : function() {// 提交规格表单事件
				// 获取实例对象
				var _this = this;
				// 获取表单数据
	  			var formName = "editSpecFormEntity";
				var params = _this.$data[formName];
				// 验证表单数据
				_this.validate(formName, function(valid) {
	          		if (valid) {// 验证通过
	          			// 更新行记录
	          			_this.specs = Vue.updateRow(_this.specs, params, "id");
	          			// 隐藏表单
	    				_this.dialogSpecFormVisible = false;
		            } else {// 验证失败
		              	return false;
		            }
	          	});
			},
			handleDeleteSpec : function(id) {
				// 获取实例对象
				var _this = this;
				// 删除行记录
				Vue.deleteRow(_this.specs, id, "id");
			},
			openAddSpecParamForm : function() {// 打开添加规格参数表单
				// 获取实例
	      		var _this = this;
	  			// 重置表单
	  			var formName = "editSpecParamFormEntity";
				_this.editSpecParamFormEntity = Vue.copy(_this.defaultEditSpecParamFormEntity);
				// 重置表单
				_this.resetForm(formName);
				// 表单标题
				_this.dialogSpecParamFormTitle = "添加产品规格参数";
				// 显示表单
				_this.dialogSpecParamFormVisible = true;
			},
			openEditSpecParamForm : function(row) {// 打开编辑规格参数表单
				// 获取实例
	      		var _this = this;
	  			// 重置表单
	  			var formName = "editSpecParamFormEntity";
				_this.editSpecParamFormEntity = Vue.copy(row);
				_this.specParams = _this.specParams || [];
				// 重置表单
				_this.resetForm(formName);
				// 表单标题
				_this.dialogSpecParamFormTitle = "修改产品规格参数";
				// 显示表单
				_this.dialogSpecParamFormVisible = true;
			},
			handleEditSpecParam : function() {// 提交规格表单事件
				// 获取实例对象
				var _this = this;
				// 获取表单数据
	  			var formName = "editSpecParamFormEntity";
				var params = _this.$data[formName];
				// 验证表单数据
				_this.validate(formName, function(valid) {
	          		if (valid) {// 验证通过
	          			// 更新行记录
	          			_this.specParams = Vue.updateRow(_this.specParams, params, "id");
	          			// 隐藏表单
	    				_this.dialogSpecParamFormVisible = false;
		            } else {// 验证失败
		              	return false;
		            }
	          	});
			},
			handleDeleteSpecParam : function(id) {
				// 获取实例对象
				var _this = this;
				// 删除行记录
				Vue.deleteRow(_this.specParams, id, "id");
			}
		}
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>