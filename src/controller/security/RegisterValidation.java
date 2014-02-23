package controller.security;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import register.UserInfo;
import tools.CommonValidationTools;

@Component("registerValidator")
public class RegisterValidation implements Validator{
	public boolean supports(Class<?> aClass){
		return UserInfo.class.isAssignableFrom(aClass);
	}
	
	public void validate(Object target, Errors errors){
		//下面三个字符串分别为：field，message.properties里注册的错误码，若给出的错误码没有使用这条message
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.UserInfo.username", "用户名不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.UserInfo.password", "密码不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", 
				"NotEmpty.UserInfo.confirmPassword", "确认密码不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "storeName", "NotEmpty.UserInfo.storeName", "商店名不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cellPhone", "NotEmpty.UserInfo.cellPhone", "手机号码不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.UserInfo.address", "地址不能为空");
		
		UserInfo userInfo = (UserInfo)target;
		
		if(!CommonValidationTools.checkUsernameFormat(userInfo.getUsername())){
			errors.rejectValue("username", "NotValid.UserInfo.username", "该用户名包含非法字符");
		}
		if(!CommonValidationTools.checkUsername(userInfo.getUsername())){
			errors.rejectValue("username", "NotValid.UserInfo.username", "该用户名已被注册");
		}
		if(!CommonValidationTools.checkPassword(userInfo.getPassword(), 
				userInfo.getConfirmPassword())){
			errors.rejectValue("confirmPassword", "NotValid.UserInfo.confirmPassword", "前后输入的密码不一致");
		}
		if(!CommonValidationTools.checkEmail(userInfo.getEmail())){
			errors.rejectValue("email", "NotValid.UserInfo.email", "请输入合格的邮箱格式");
		}
		if(!CommonValidationTools.checkPhone(userInfo.getCellPhone())){
			errors.rejectValue("cellPhone", "NotValid.UserInfo.phone", "请输入合格的号码格式");
		}
		if (!CommonValidationTools.checkLocation(userInfo.getLng(), userInfo.getLat())) {
			errors.rejectValue("address", "NotValid.UserInfo.address", "请选择定位");
		}
	}

	
}
