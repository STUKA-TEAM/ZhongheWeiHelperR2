package controller.security;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import register.CommonValidationTools;
import register.RegisterInfo;

@Component("registerValidator")
public class RegisterValidation implements Validator{
	public boolean supports(Class<?> aClass){
		return RegisterInfo.class.isAssignableFrom(aClass);
	}
	
	public void validate(Object target, Errors errors){
		//下面三个字符串分别为：field，message.properties里注册的错误码，若给出的错误码没有使用这条message
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.RegisterInfo.name", "用户名不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.RegisterInfo.name", "密码不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "storeName", "NotEmpty.RegisterInfo.name", "商店名不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cellPhone", "NotEmpty.RegisterInfo.name", "手机号码不能为空");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.RegisterInfo.name", "地址不能为空");
		
		RegisterInfo registerInfo = (RegisterInfo)target;
		CommonValidationTools commonValidationTools = new CommonValidationTools();
		
		if(!commonValidationTools.checkEmail(registerInfo.getEmail())){
			errors.rejectValue("email", "NotValid.NewRegister.email", "请输入合格的邮箱格式");
		}
		if(!commonValidationTools.checkPhone(registerInfo.getPhone())){
			errors.rejectValue("email", "NotValid.NewRegister.phone", "请输入合格的电话格式");
		}
	}

	
}
