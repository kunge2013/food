/**
 * 复制属性
 * 
 * @param source
 *            源始对象
 * @Param target
 *            目标对象
 * @return 最终对象
 */
Vue.copy = function(soruce, target) {
	// 默认值
	target = target || {};
	// 复制源对象的属性
	for (var name in soruce) {
		if (soruce[name] == null) {// 空检查
			target[name] = null;
		} else if (typeof soruce[name] === 'object') {// 如果属性值是对象或数组
			// 初始化目标对象的对应属性
			target[name] = (soruce[name].constructor === Array) ? [] : {};
			// 迭代复制
			Vue.copy(soruce[name], target[name]);
		} else {// 如果属性值是普通值
			target[name] = soruce[name];
		}
	}
	return target;
}

/**
 * 拷贝数组
 * 
 */
Vue.copyArr = function(arr) {
	var source = { arr : arr };
	return Vue.copy(source, {}).arr;
}

// 校验URL
Vue.checkUrl = function(url) {
	if (url == null || url == "") {
		return false;
	}
	var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
	return reg.test(url);
}

// 校验金钱
Vue.checkMoney = function(number) {
	return /^\d{1,6}(\.\d{1,2}){0,1}$/.test(number);
}

// UUID
Vue.UUID = function() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
  	s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
  	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
  	s[8] = s[13] = s[18] = s[23] = "-";
	 
  	var uuid = s.join("");
  	return uuid;
}

/**
 * 更新行记录
 * 
 * @param listData
 *            数据列表
 * @param row
 *            行记录
 * @param idName
 *            ID列名
 */
Vue.updateRow = function(listData, row, idName) {
	if (row[idName] == null) {
		row[idName] = Vue.UUID();
	}
	var index = null;
	for (var i = 0; i < listData.length; i++) {
		if (listData[i][idName] === row[idName]) {
			index = i;
			break;
		}
	}
	if (index != null) {
		listData[index] = row;
		listData = Vue.copyArr(listData);
	} else {
		listData.push(Vue.copy(row));
	}
	return listData;
}

/**
 * 删除行记录
 * 
 * @param listData
 *            列表数据
 * @param id
 *            ID
 * @param idName
 *            id列名
 */
Vue.deleteRow = function(listData, id, idName) {
	var index = null;
	for (var i = 0; i < listData.length; i++) {
		if (listData[i][idName] === id) {
			index = i;
			break;
		}
	}
	if (index != null) {
		listData.remove(index, 1);
	} 
	return listData;
}

/**
 * 删除数组元素
 * 
 * @param startIdx
 *            开始索引
 * @param count
 *            删除元素的数量
 */
Array.prototype.remove = function(startIdx, count) {
	this.splice(startIdx, count);
}

// 设置Axios默认参数序列化器
axios.defaults.paramsSerializer = function(params) {
	return Qs.stringify(params, {arrayFormat : 'repeat'});
};

// 基础混合参数：支持表单、请求、提示框等模块
var BASE_MIXIN = {
	data : function() {
		return {
			baseUrl : null,// 根路径
			resetForm : function(formName) {// 重置表单：回复初始值并移除验证结果
				// 获取表单实例引用
				var form = this.$refs[formName];
				if (form) {
					// 重置表单
					form.resetFields();
				}
			},
			validate : function(formName, callback) {// 验证表单
				// 获取表单实例引用
				var form = this.$refs[formName];
				if (form) {
					// 重置表单
					form.validate(callback || new Function());
				}
			},
			submitForm : function(formName, urlOrFunc) {// 提交表单
				// 获取实例对象
				var _this = this;
				// 获取表单实例引用
				var form = _this.$refs[formName];
				// 获取表单数据
				var params = _this.$data[formName];
				// 验证表单数据
				_this.validate(formName, function(valid) {
	          		if (valid) {// 验证通过
	              		if (urlOrFunc) {
	              			if (typeof urlOrFunc == "string") {
	              				// 直接请求URL
	              				_this.doPostData(urlOrFunc, params);
	              			} else {
	              				// 执行回调函数
	              				urlOrFunc(params);
	              			}
	              		}
		            } else {// 验证失败
		              	return false;
		            }
	          	});
			},
			confirm : function(message, okFunc) {// 弹出确认框
				// 获取实例对象
				var _this = this;
				// 弹出确认框
				_this.$confirm(message, "系统提示", {
	                type: 'warning'
	      		}).then(function() {
	      			if (okFunc) {
	      				// 调用确认按钮回调函数
	      				okFunc();
	      			}
	      		}).catch(function() {});
			},
			doGetData : function(url, params, func) {// GET数据请求
				// 获取实例对象
				var _this = this;
				// 请求数据
				axios.get(url, {params : params})
					.then(function (response) {
						// 获取响应数据
						var r = response.data;
						
						if (r.success) {// 请求数据成功
							if (func) {// 回调
								func(r);
							}
						} else {// 请求数据失败
							// 提示错误信息
							_this.$message.error(r.message);
						}
				 	}).catch(function (error) {// 请求数据处理失败
				 		// 提示错误信息
				 		_this.$message.error("服务器异常！");
				 	});
			},
			doPostData : function(url, params, func) {// POST数据请求
				// 获取实例对象
				var _this = this;
				// 发送POST请求
				axios.post(url, params)
	 	 	 	 	 .then(function (response) {
	 	 	 	 		 // 获取响应数据
	 	 	 	 		 var r = response.data;
 	 	 		 	 	 if (r.success) {// 请求成功
 	 	 		 	 		 if (_this.refreshData) {
 	 	 		 	 			 // 刷新数据表格
 	 	 	 		 	 		 _this.refreshData();
 	 	 		 	 		 }
 	 	 		 	 		 // 提示请求成功
 	 	 		 	 		 _this.$message({
 	 	 		 	 			 message: r.message,
 	 	 		 	 			 type: "success"
 	 	 		 	 		 });
 	 	 		 	 		 // 隐藏提交表单
 	 	 		 	 		 _this.dialogFormVisible = false;
 	 	 		 	 		 if (func) {// 回调
 	 	 		 	 			 func(r);
 	 	 		 	 		 }
 	 	 		 	 	 } else {// 请求失败，服务器响应错误信息
 	 	 		 	 		 // 提示错误
 	 	 		 	 		 _this.$message.error(r.message);
 	 	 		 	 	 }
					 }).catch(function (error) {// 请求失败
						 // 提示错误
						 _this.$message.error("服务器异常！");
					 });
			}
			
		}
	},
	methods : {
		/**
		 * 检查开始日期区间对应的结束日期区间有效性，如果开始日期大于结束日期，则重置结束日期为开始日期
		 * 
		 * @param formName
		 *            表单引用
		 * @param startDateName
		 *            开始日期字段名
		 * @param endDateName
		 *            结束日期字段名
		 */
		checkEndDate(formName, startDateName, endDateName) {
			// 获取表单实例引用
			var form = this.$data[formName];
			// 获取表单中的开始日期
			var startDate = form[startDateName];
			// 获取表单中的结束日期
			var endDate = form[endDateName];
			// 开始日期和结束日期都有值的情况下
			if (startDate && endDate) {
				// 开始日期大于结束日期时，对结束日期纠正
				if (startDate > endDate) {
					// 将结束日期重置为开始日期
					form[endDateName] = startDate;
				}
			}
		},
		/**
		 * 检查结束日期区间对应的开始日期区间有效性，如果结束日期小于开始日期，则重置开始日期为结束日期
		 * 
		 * @param formName
		 *            表单引用
		 * @param startDateName
		 *            开始日期字段名
		 * @param endDateName
		 *            结束日期字段名
		 */
		checkStartDate(formName, startDateName, endDateName) {
			// 获取表单实例引用
			var form = this.$data[formName];
			// 获取表单中的开始日期
			var startDate = form[startDateName];
			// 获取表单中的结束日期
			var endDate = form[endDateName];
			// 开始日期和结束日期都有值的情况下
			if (startDate && endDate) {
				// 结束日期小于开始日期时，对开始日期纠正
				if (startDate > endDate) {
					// 将开始日期重置为结束日期
					form[startDateName] = endDate;
				}
			}
		}
	}
}

// 扩展基础组件：支持表单、提示框、请求等模块
var Base = Vue.extend({
	mixins: [BASE_MIXIN]
});

// 公共混合参数：继承BASE_MIXIN，支持菜单、用户信息等模块
var COMMON_MIXIN = {
	data : function() {// data属性
		return {
			containerStyle2 : {// 样式
				height : (window.innerHeight - 60) +"px"
			},
			menuData : [],// 菜单数据
			defaultEpeneds : [],// 菜单默认展开
			logout : function() {// 注销
				window.location.href = this.baseUrl + "logout.do";
			},
			getMyInfo : function(func) {// 查看个人信息
				// 获取当前实例
				var _this = this;
				// 请求个人信息
				this.doGetData(_this.baseUrl + _this.getMyInfoUrl, _this.setMyInfoEntity, function(r) {
					if (r.success) {
						func(r);// 回调
					} else {
						_this.$message.error("服务器异常！");
					}
				});
			},
			getMyInfoUrl : "sysUser/getMyInfo.do",// 获取个性信息请求URL
			setMyInfoUrl : "sysUser/update.do",// 更新个人信息请求URL
			uploadMyInfoUrl : "sysUser/uploadImage.do",// 上传请求地址
			setMyInfoFormVisible : false,// 是否显示设置个人信息界面
			setMyInfoEntity : {// 个人信息实体
				id : null,
			  	username : null,
			  	image : null,
			  	realName : null,
			  	birthday : null,
			  	sex : "M",
			  	telephone : null,
			  	email : null
			},
			curSysUser : {// 当前用户信息
				id : null,
				username : null,
			  	image : null,
			  	realName : null,
			},
			setMyInfoRules : {
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
		  				if (value != app.setMyInfoEntity.password) {
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
		  	},// 设置个人信息校验规则
			changePasswordEntity : {// 修改密码实体
				id : null,
			  	username : null,
			  	password : null,
			  	repassword : null
			},
			changePasswordEntityRules : {// 设置密码校验规则
				password : [
		  			{ required : true, message: '请输入密码！', trigger: 'change' },
		            { min : 6, max : 20, message: '密码必须是6到20个字符！', trigger: 'change' }
		  		],
		  		repassword : [
		  			{ required: true, message: '请再次输入密码！', trigger: 'change' },
		  			{ min : 6, max : 20, message: '密码必须是6到20个字符！', trigger: 'change' },
		  			{ validator : function(rule, value, callback) {
		  				if (value != app.changePasswordEntity.password) {
		  					callback(new Error('两次输入密码不一致!'));
		  			 	} else {
		  			 		callback();
		  			 	}
		  			}, trigger: 'change' }
		  		]
			},
			changePasswordEntityFormVisible : false,// 设置密码实体表单是否显示
			changePasswordUrl : "sysUser/changePassword.do",
			getMyMenuUrl : "sysUser/getMyMenu.do",
			getMyMenu : function() {// 获取我的菜单
				// 获取当前实例
				var _this = this;
				// 获取数据
				_this.doGetData(_this.baseUrl + _this.getMyMenuUrl, {platform : "P"}, function(r) {
					if (r.success) {
						_this.menuData = r.data;
						for (var i = 0; i < _this.menuData.length; i++) {
							_this.defaultEpeneds.push(_this.menuData[i].id);
						}
					} else {
						_this.$message.error("服务器异常！");
					}
				});
			},
			/**
			 * Common组件已挂载事件
			 * 
			 */
			commonMounted : function() {
				
			}
		}
	},
	/**
	 * 插件挂载事件，插件挂载后调用
	 * 
	 */
	mounted : function() {
		// 获取实例对象
		var _this = this;
		// 获取个人信息
		_this.getMyInfo(function(r) {
			_this.curSysUser = r.data;
		});
		// 获取菜单列表
		_this.getMyMenu();
		// 触发Common组件已挂载事件
		_this.commonMounted();
		// 监听窗口大小变化
		window.onresize = function() {
			// 根据高度变化调整容器高度
			_this.containerStyle2.height = (window.innerHeight - 60) + "px";
		}
	},
	methods : {
		changePage : function(index, indexPath) {// 点击菜单打开新页面
			window.location.href = this.baseUrl + index;
		},
		clickSysUserMenu : function(command) {// 点击右上角系统用户菜单触发的事件
			if (command == "logout") {// 注销
				this.logout();
			} else if (command == "showMyInfo") {// 查看个人信息
				// 获取实例
				var _this = this;
				// 获取个人信息
				_this.getMyInfo(function(r) {// 回调
					// 填充表单
					_this.setMyInfoEntity = r.data;
					// 显示表单
					_this.setMyInfoFormVisible = true;
				});
			} else if (command == "showChangePassword") {// 显示设置密码表单
				// 获取实例
				var _this = this;
				_this.resetForm("changePasswordEntity");
				_this.changePasswordEntity.username = _this.curSysUser.username;
				_this.changePasswordEntityFormVisible = true;
			}
		},
		handleSetMyInfo : function() {// 提交设置个人信息
			// 获取当前实例
			var _this = this;
			// 获取表单数据
			var params = _this.$data["setMyInfoEntity"];
			// 验证表单数据
			_this.$refs["setMyInfoEntity"].validate(function(valid) {
          		if (valid) {// 验证通过
          			// 提交个人信息
        			_this.doPostData(_this.baseUrl + _this.setMyInfoUrl, _this.setMyInfoEntity, function(r) {
        				if (r.success) {
        					// 隐藏表单
        					_this.setMyInfoFormVisible = false;
        					_this.curSysUser = Vue.copy(_this.setMyInfoEntity);
        				} else {
        					_this.$message.error("服务器异常！");
        				}
        			});
	            } else {// 验证失败
	              	return false;
	            }
          	});
		},
		handleUploadMyImageSuccess : function(res) {// 上传个人头像成功事件
			if (res.success) {// 成功
		        this.setMyInfoEntity.image = res.data;
			} else {// 失败
				this.$message.error(res.message);
			}
		},
		handleChangePassword : function() {// 修改密码事件
			// 获取当前实例
			var _this = this;
			// 获取表单数据
			var params = _this.$data["changePasswordEntity"];
			// 验证表单数据
			_this.$refs["changePasswordEntity"].validate(function(valid) {
          		if (valid) {// 验证通过
          			// 提交个人信息
        			_this.doPostData(_this.baseUrl + _this.changePasswordUrl, _this.changePasswordEntity, function(r) {
        				if (r.success) {
        					// 隐藏表单
        					_this.changePasswordEntityFormVisible = false;
        				} else {
        					_this.$message.error("服务器异常！");
        				}
        			});
	            } else {// 验证失败
	              	return false;
	            }
          	});
		}
	}
}

// 扩展公共组件：支持菜单、个人信息等模块
var Common = Vue.extend({
	mixins: [BASE_MIXIN, COMMON_MIXIN]
});

// 普通表格混合参数
var TABLE_MIXIN = {
	data : function() {// data属性
		return {
			tableData : [],// 表格数据
			total : 0,// 总记录数
			pageSize : 2147483647,// 每页默认的记录数
          	pageSizes : [10, 20, 50, 100],// 可选择的每页记录数
			curPage : 1,// 当前页码
			defaultSort : {prop : 'createTime', order: 'descending'},// 默认排序方式
          	sort : null,// 排序方式
          	selection : [],// 选中的ID列表
          	dataUrl : null,// 表格数据请求URL
          	getUrl : null,// 获取实体请求URL
          	addUrl : null,// 添加请求URL
          	updateUrl : null,// 更新请求URL
          	deleteUrl : null,// 删除请求URL
          	dialogFormTitle : "",// 对话框title
          	dialogFormVisible : false,// 对话框是否显示
          	refreshData : function() {// 表格数据刷新
				// 请求参数
				var params = {
					curPage : this.curPage,// 当前页码 
					pageSize : this.pageSize,// 每页记录数
					orderByField : "`" + this.sort.prop + "`",// 排序字段名
					asc : this.sort.order == "ascending"// 排序方式
				};
				
				// 处理自定义过滤参数
				params = Vue.copy(this.filterParams, params);
          		
				// 查询表格数据
				this.doQueryTableData(params);
			},
			doQueryTableData : function(params) {// 查询表格数据
				// 获取实例对象
				var _this = this;
				this.doGetData(this.dataUrl, params, function(r) {
					// 属性表格数据
					_this.tableData = r.data;
					// 设置当前页码
					_this.curPage = r.current;
					// 设置总记录数
					_this.total = r.total;
					// 设置页面总记录数
					_this.pageSize = r.pageSize;
				})
			},
			/**
			 * Common组件已挂载事件
			 * 
			 */
			commonMounted : function() {
				this.tableMounted();
			},
			/**
			 * Table组件已挂载事件
			 * 
			 */
			tableMounted : function() {
				
			}
		}
	},
	methods : {
		handleSort : function(params) {// 处理排序事件
			// 计算排序方式：如果没有排序取默认排序方式
			this.sort = Vue.copy((params == null || params.column == null) ? this.defaultSort : params, this.sort);
			// 刷新表格数据
			this.refreshData();
		},
		handleSelection : function(selection) {// 处理选中复选框事件
			this.selection = selection;
		},
		handleQuery : function(event) {// 查询
			// 刷新表格数据
			this.refreshData();
		},
		handleClearQuery : function(formName, tableName) {// 清空条件查询
			// 重置查询表单
			this.$refs[formName].resetFields();
			// 重置表格排序条件
			this.$refs[tableName].clearSort();
			// 重置sort条件
			this.sort = Vue.copy(this.defaultSort, this.sort);
			// 刷新表格数据
			this.refreshData();
		},
      	handleDelete : function(entityId) {// 删除
      		// 获取实例对象
      		var _table = this;
      		// 弹出确认框
      		_table.$confirm("确认删除" + _table.moduleName + "？", "系统提示", {
                type: 'warning'
      		}).then(function() {
      			// 点击确认按钮触发POST请求
      			_table.doPostData(_table.deleteUrl, {ids : [entityId]});
      		}).catch(function() {});
  		},
  		handleBatchDelete : function() {// 批量删除
  			// 获取实例对象
  			var _table = this;
  			// 弹出确认框
  			_table.$confirm("确认删除" + _table.moduleName + "？", "系统提示", {
                type: 'warning'
      		}).then(function() {// 点击确认按钮触发
      			// 没有选中记录，提示错误
      			if (_table.selection.length == 0) {
      				_table.$message.error("没有选中任何要删除的行记录！");
      				return false;
      			}
      			// 选中的记录ID列表
      			var entityIds = [];
      			// 遍历获取选择的记录ID
      			for (var index in _table.selection) {
      				// 获取选中的记录ID
      				var entityId = _table.selection[index].id;
      				// 添加到数组中
      				entityIds.push(entityId);
      			}
      			// 发送POST请求
      			_table.doPostData(_table.deleteUrl, {ids : entityIds});
      		}).catch(function() {});
  		},
		handleEdit : function(formName) {// 编辑
			// 获取实例对象
			var _table = this;
			// 获取表单数据
			var params = _table.$data[formName];
			// 验证表单数据
			_table.validate(formName, function(valid) {
          		if (valid) {// 验证通过
              		if (params.id) {// 更新
              			_table.doPostData(_table.updateUrl, params);
              		} else {// 添加
              			_table.doPostData(_table.addUrl, params);
              		}
	            } else {// 验证失败
	              	return false;
	            }
          	});
      	},
      	handleEnable : function(id) {// 激活
      		// 获取实例对象
      		var _table = this;
      		// 弹出确认框
      		_table.confirm("确认激活" + _table.moduleName + "？", function(){
      			// 发送请求
      			_table.doPostData(_table.toggleUrl, {id : id, status : "Y"});
      		});
      	},
      	handleDisable : function(id) {// 禁用
      		// 获取实例对象
      		var _table = this;
      		// 弹出确认框
      		_table.confirm("确认禁用" + _table.moduleName + "？", function(){
      			// 发送请求
      			_table.doPostData(_table.toggleUrl, {id : id, status : "N"});
      		});
      	},
  		openAddForm : function(formName) {// 打开添加表单
  			// 重置表单
			this.resetForm(formName);
			// 重置表单数据
			this.editFormEntity = Vue.copy(this.defaultEditFormEntity);
			// 表单标题
			this.dialogFormTitle = "添加" + this.moduleName;
			// 显示表单
			this.dialogFormVisible = true;
		},
		openEditForm : function(formName, row) {// 打开修改表单
			// 获取实例对象
			var _this = this;
			// 重置表单
			_this.resetForm(formName);
			// 请求数据
			_this.doGetData(_this.getUrl, {id : row.id}, function(r) {// 请求数据成功回调
				// 初始化表单数据
				_this.editFormEntity = Vue.copy(r.data);
				// 表单标题
				_this.dialogFormTitle = "编辑" + _this.moduleName;
				// 显示表单
				_this.dialogFormVisible = true;
			});
		}
	}
}

//扩展普通表格
var Table = Vue.extend({
	mixins: [BASE_MIXIN, COMMON_MIXIN, TABLE_MIXIN]
});

// 分页表格混合参数
var PAGE_TABLE_MIXIN = {
	data : function() {// data属性
		return {
          	total : 0,// 总记录数
          	pageSize : 10,// 每页默认的记录数
          	pageSizes : [10, 20, 50, 100],// 可选择的每页记录数
          	curPage : 1,// 当前页码
          	/**
          	 * Table组件已挂载事件
          	 * 
          	 */
          	tableMounted : function() {
          		this.pageTableMounted();
          	},
          	/**
          	 * Page Table组件已挂载事件
          	 * 
          	 */
          	pageTableMounted : function() {
          		
          	}
		}
	},
	methods : {
		handleSizeChange : function(size) {// 处理每页记录数改变事件
			// 改变每页记录数
			this.pageSize = size;
			// 刷新表格数据
			this.refreshData();
		},
		handleCurChange : function(cur) {// 处理翻页事件
			// 改变当前页码
			this.curPage = cur;
			// 刷新表格数据
			this.refreshData();
		}
	}
}

// 扩展分页表格
var PageTable = Vue.extend({
	mixins: [BASE_MIXIN, COMMON_MIXIN, TABLE_MIXIN, PAGE_TABLE_MIXIN]
});
