webpackJsonp([35],{"5Ee/":function(t,e,i){"use strict";function n(t){i("YCE2")}Object.defineProperty(e,"__esModule",{value:!0});var o=i("hRKE"),s=i.n(o),a=i("PHrY"),r={data:function(){return{treeInfo:[{name:"模版根目录",path:"",child:[],id:0}],defaultProps:{children:"child",label:"name",id:"id"},root:""}},methods:{getTemplateTree:function(){var t=this;a.m({root:this.root}).then(function(e){t.treeInfo[0].path=e.body.rootPath,t.treeInfo[0].child=e.body.resources,setTimeout(function(){$("#refresh").removeClass("an-circle")},500)})},refresh:function(){$("#refresh").addClass("an-circle"),this.getTemplateTree()},settingTpl:function(t){this.$router.push({path:"/templatesetting",query:{mobile:t,noceStr:Math.round(10*Math.random())}})},handleNodeClick:function(t){var e=s()(t.child);if($(window).scrollTop(0),"object"==e){var i=t.path;this.$router.push({path:"/templatelist",query:{name:i,noceStr:Math.round(10*Math.random())}})}else this.$router.push({path:"/templateedit",query:{id:t.path,root:t.root,type:"edit"}})},viewHeight:function(){var t=$(window).height();t=parseInt(t-170),$(".js-height").css("minHeight",t+"px")}},mounted:function(){var t=document.body.clientHeight;$(".cms-body").css("minHeight",t-110+"px")},created:function(){this.$router.push({path:"/templatelist"}),this.getTemplateTree()}},c=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("section",{staticClass:"cms-body flex"},[i("div",{staticClass:"tree-layout"},[i("section",{staticClass:"file-tree-items"},[i("div",{staticClass:"reflash",on:{click:t.refresh}},[i("span",{staticClass:"el-icon-refresh"},[t._v("刷新")]),t._v(" "),i("div",[i("a",{attrs:{href:"javascript:void(0)",title:"PC端"},on:{click:function(e){t.settingTpl(!1)}}},[i("i",{staticClass:"iconfont icon-iconfontdesktop",staticStyle:{"font-size":"20px"}})]),t._v(" "),i("a",{attrs:{href:"javascript:void(0)",title:"移动端"},on:{click:function(e){t.settingTpl(!0)}}},[i("i",{staticClass:"iconfont icon-shouji",staticStyle:{"font-size":"20px","margin-left":"10px"}})])])]),t._v(" "),i("el-tree",{attrs:{data:t.treeInfo,props:t.defaultProps,accordion:"","highlight-current":!0,"node-key":"id","default-expanded-keys":[0]},on:{"node-click":t.handleNodeClick}})],1)]),t._v(" "),i("router-view",{staticClass:"cms-content-right"})],1)},l=[],h={render:c,staticRenderFns:l},d=h,p=i("Z0/y"),u=n,f=p(r,d,!1,u,"data-v-e704719a",null);e.default=f.exports},CmFV:function(t,e,i){e=t.exports=i("UTlt")(!1),e.push([t.i,"\n.bbs-iconfontdesktop[data-v-e704719a] {\n  top: 0;\n  margin-right: 0;\n}\n",""])},YCE2:function(t,e,i){var n=i("CmFV");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);i("FIqI")("06aedaa0",n,!0,{})}});