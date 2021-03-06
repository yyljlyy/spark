package com.yyljlyy.api.ryz.validator;

import static com.yyljlyy.api.common.APIResponse.*;

import com.jfinal.core.Controller;
import com.yyljlyy.base.BaseValidator;


public class IdValidator extends BaseValidator {
	
	private static final String MSG_KEY = "id_msg";

	@Override
	protected void validate(Controller c) {
		validateRequired("id", MSG_KEY, "id is null");
	}

	@Override
	protected void handleError(Controller c) {
		toAPIResponse(c, RESPONSE_CODE_ERROR_IdIsNull, c.getAttrForStr(MSG_KEY), null);
	}

}
