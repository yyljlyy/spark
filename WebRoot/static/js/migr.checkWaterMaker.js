(function($){
	$.fn.extend({
		checkWaterMaker:function(option){
			return this.each(function(){
				_op=$.extend({
					width:150,height:150,top:0,left:0,imgUrl:Bird.homePath+'/images/approve.png'
				},option);
				var $this = $(this);
				var $p = $this.parents(".unitBox:first");
				//如果存在水印，则不在加
				var waterMarker = $("#waterMarker",$p);
				if(waterMarker.size() > 0){
					return waterMarker;
				}
				$p.append("<div id='waterMarker'></div>");
				waterMarker = $("#waterMarker",$p);
				var css = {'position':'absolute','zIndex':'9627','top':_op.top+'px'
						,'left':_op.left+'px','width':_op.width+'px','height':_op.height+'px'
						,'backgroundImage':'url('+_op.imgUrl+')','filter':'alpha(opacity=50)'}
				waterMarker.css(css);
			});
		}
	});
	
})(jQuery);