package com.yyljlyy.common;

import cn.yyljlyy.db.AutoGenController;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.yyljlyy.api.common.ModelConfigRyz;
import com.yyljlyy.api.common.RouteConfigRyz;
import com.yyljlyy.ext.MyBeetlRenderFactory;
import com.yyljlyy.sys.controller.UserinfoController;
import com.yyljlyy.sys.model.Userinfo;
import com.yyljlyy.wms.common.ModelConfigWms;
import com.yyljlyy.wms.common.RouteConfigWms;


/**
 * API引导式配置
 */
public class JFinalWebConfig extends JFinalConfig {
	/**
	 * 供Shiro插件使用。
	 */
	private Routes routes;
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		//loadPropertyFile("classes/config.properties");
		loadPropertyFile("dev.txt");
		me.setDevMode(getPropertyToBoolean("devMode", true));
		
		//me.setViewType(ViewType.FREE_MARKER);
		
		me.setError401View("/html/401.html");//没登录
		me.setError403View("/html/403.html");//没权限
		me.setError404View("/html/404.html");
		me.setError500View("/html/500.html");
		me.setMainRenderFactory(new MyBeetlRenderFactory());
		//me.setBaseViewPath("/WEB-INF/pages/common");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		this.routes = me;
		//new ControllerPlugin(me).start();
		me.add("/jhtml",HtmlController.class);
		me.add("/", IndexController.class);
		me.add("/gencode",AutoGenController.class);
		me.add("/sys/userinfo", UserinfoController.class,"/sys");
		RouteConfigRyz.config(me);
		RouteConfigWms.config(me);
		
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		DruidPlugin druidPlugin = new DruidPlugin(
				getProperty("jdbc.url"),
				getProperty("jdbc.username"),
				getProperty("jdbc.password"),
				getProperty("jdbc.driver"));
		WallFilter wall = new WallFilter();
		wall.setDbType(getProperty("jdbc.dbType"));
		druidPlugin.addFilter(wall);
		druidPlugin.addFilter(new StatFilter());
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		me.add(arp);
		arp.addMapping("sys_userinfo",Userinfo.class);
		//配置models
		ModelConfigRyz.config(arp);
		ModelConfigWms.config(arp);
		//配置shiro插件
		me.add(new ShiroPlugin(this.routes));
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//shiro权限拦截器配置
        me.add(new ShiroInterceptor());
        //让freemarker可以使用session
        me.add(new SessionInViewInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler());
		DruidStatViewHandler dvh =  new DruidStatViewHandler("/druid");
		me.add(dvh);
		me.add(new HtmlHandler());
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
	
}
