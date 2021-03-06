(function($){$.fn.pjax=function(container,options){options=optionsFor(container,options)
return this.live('click',function(event){return handleClick(event,options)})}
function handleClick(event,container,options){options=optionsFor(container,options)
var link=event.currentTarget
if(event.which>1||event.metaKey)
return
if(location.protocol!==link.protocol||location.host!==link.host)
return
if(link.hash&&link.href.replace(link.hash,'')===location.href.replace(location.hash,''))
return
var defaults={url:link.href,container:$(link).attr('data-pjax'),clickedElement:$(link),fragment:null}
$.pjax($.extend({},defaults,options))
event.preventDefault()
return false}
function stripPjaxParam(url){return url.replace(/\?_pjax=true&?/,'?').replace(/_pjax=true&?/,'').replace(/\?$/,'')}
function parseURL(url){var a=document.createElement('a')
a.href=url
return a}
var pjax=$.pjax=function(options){options=$.extend(true,{},$.ajaxSettings,pjax.defaults,options)
if($.isFunction(options.url)){options.url=options.url()}
var url=options.url
var hash=parseURL(url).hash
var oldBeforeSend=options.beforeSend,oldComplete=options.complete,oldSuccess=options.success,oldError=options.error
options.context=findContainerFor(options.container)
var timeoutTimer
options.beforeSend=function(xhr,settings){var context=this
url=stripPjaxParam(settings.url)
if(settings.timeout>0){timeoutTimer=setTimeout(function(){var event=$.Event('pjax:timeout')
context.trigger(event,[xhr,options])
if(event.result!==false)
xhr.abort('timeout')},settings.timeout)
settings.timeout=0}
xhr.setRequestHeader('X-PJAX','true')
var result
if(oldBeforeSend){result=oldBeforeSend.apply(this,arguments)
if(result===false)return false}
var event=$.Event('pjax:beforeSend')
this.trigger(event,[xhr,settings])
result=event.result
if(result===false)return false
this.trigger('pjax:start',[xhr,options])
this.trigger('start.pjax',[xhr,options])}
options.complete=function(xhr,textStatus){if(timeoutTimer)
clearTimeout(timeoutTimer)
if(oldComplete)oldComplete.apply(this,arguments)
this.trigger('pjax:complete',[xhr,textStatus,options])
this.trigger('pjax:end',[xhr,options])
this.trigger('end.pjax',[xhr,options])}
options.error=function(xhr,textStatus,errorThrown){var respUrl=xhr.getResponseHeader('X-PJAX-URL')
if(respUrl)url=stripPjaxParam(respUrl)
if(oldError)oldError.apply(this,arguments)
var event=$.Event('pjax:error')
this.trigger(event,[xhr,textStatus,errorThrown,options])
if(textStatus!=='abort'&&event.result!==false)
window.location=url}
options.success=function(data,status,xhr){var respUrl=xhr.getResponseHeader('X-PJAX-URL')
if(respUrl)url=stripPjaxParam(respUrl)
var title,oldTitle=document.title
if(options.fragment){var html=$('<html>').html(data)
var $fragment=html.find(options.fragment)
if($fragment.length){this.html($fragment.contents())
title=html.find('title').text()||$fragment.attr('title')||$fragment.data('title')}else{return window.location=url}}else{if(!$.trim(data)||/<html/i.test(data))
return window.location=url
this.html(data)
title=this.find('title').remove().text()}
if(title)document.title=$.trim(title)
var state={url:url,pjax:this.selector,fragment:options.fragment,timeout:options.timeout}
if(options.replace){pjax.active=true
window.history.replaceState(state,document.title,url)}else if(options.push){if(!pjax.active){window.history.replaceState($.extend({},state,{url:null}),oldTitle)
pjax.active=true}
window.history.pushState(state,document.title,url)}
if((options.replace||options.push)&&window._gaq)
_gaq.push(['_trackPageview'])
if(hash!==''){window.location.href=hash}
if(oldSuccess)oldSuccess.apply(this,arguments)
this.trigger('pjax:success',[data,status,xhr,options])}
var xhr=pjax.xhr
if(xhr&&xhr.readyState<4){xhr.onreadystatechange=$.noop
xhr.abort()}
pjax.options=options
pjax.xhr=$.ajax(options)
$(document).trigger('pjax',[pjax.xhr,options])
return pjax.xhr}
function optionsFor(container,options){if(container&&options)
options.container=container
else if($.isPlainObject(container))
options=container
else
options={container:container}
if(options.container)
options.container=findContainerFor(options.container)
return options}
function findContainerFor(container){container=$(container)
if(!container.length){throw"no pjax container for "+ container.selector}else if(container.selector!==''&&container.context===document){return container}else if(container.attr('id')){return $('#'+ container.attr('id'))}else{throw"cant get selector for pjax container!"}}
pjax.defaults={timeout:650,push:true,replace:false,data:{_pjax:true},type:'GET',dataType:'html'}
pjax.click=handleClick
var popped=('state'in window.history),initialURL=location.href
$(window).bind('popstate',function(event){var initialPop=!popped&&location.href==initialURL
popped=true
if(initialPop)return
var state=event.state
if(state&&state.pjax){var container=state.pjax
if($(container+'').length)
$.pjax({url:state.url||location.href,fragment:state.fragment,container:container,push:false,timeout:state.timeout})
else
window.location=location.href}})
if($.inArray('state',$.event.props)<0)
$.event.props.push('state')
$.support.pjax=window.history&&window.history.pushState&&window.history.replaceState&&!navigator.userAgent.match(/((iPod|iPhone|iPad).+\bOS\s+[1-4]|WebApps\/.+CFNetwork)/)
if(!$.support.pjax){$.pjax=function(options){var url=$.isFunction(options.url)?options.url():options.url,method=options.type?options.type.toUpperCase():'GET'
var form=$('<form>',{method:method==='GET'?'GET':'POST',action:url,style:'display:none'})
if(method!=='GET'&&method!=='POST'){form.append($('<input>',{type:'hidden',name:'_method',value:method.toLowerCase()}))}
var data=options.data
if(typeof data==='string'){$.each(data.split('&'),function(index,value){var pair=value.split('=')
form.append($('<input>',{type:'hidden',name:pair[0],value:pair[1]}))})}else if(typeof data==='object'){for(key in data)
form.append($('<input>',{type:'hidden',name:key,value:data[key]}))}
$(document.body).append(form)
form.submit()}
$.pjax.click=$.noop
$.fn.pjax=function(){return this}}})(jQuery);