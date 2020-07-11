package com.buf.sys.controller;

import com.buf.common.utils.ServletUtils;
import com.buf.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller公共组件
 *
 *
 *
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}

	/*返回结果集*/
	protected String renderResult(String result, String message, Object data) {
		return ServletUtils.renderResult(result, message, data);
	}

	protected String renderResult(HttpServletResponse response, String result, String message, Object data) {
		return ServletUtils.renderResult(response, result, message, data);
	}

	protected String renderResult(String result, String message) {
		return this.renderResult((String)result, message, (Object)null);
	}

	protected String renderResult(String result, StringBuilder message) {
		return this.renderResult(result, message != null?message.toString():"");
	}

	protected String renderResult(HttpServletResponse response, String result, String message) {
		return this.renderResult(response, result, message, (Object)null);
	}


}
