package com.yyljlyy.api.ryz.controller;


import com.yyljlyy.api.base.BaseAPIController;

public class ConsoleController extends BaseAPIController {
	
	public void index(){
		setAttr("visitIp",getRequest().getRemoteHost());
		render("home.html");
	}
	
	public void auth(){
		render("auth.html");
	}
	
	public void home(){
		setAttr("visitIp",getRequest().getRemoteHost());
		render("home.html");
	}
	
	public void apilist(){
		render("list.html");
	}
	
	public void apistat(){
		render("stat.html");
	}
	
	
}
