(function(e){function t(t){for(var r,u,a=t[0],s=t[1],i=t[2],b=0,d=[];b<a.length;b++)u=a[b],Object.prototype.hasOwnProperty.call(c,u)&&c[u]&&d.push(c[u][0]),c[u]=0;for(r in s)Object.prototype.hasOwnProperty.call(s,r)&&(e[r]=s[r]);l&&l(t);while(d.length)d.shift()();return o.push.apply(o,i||[]),n()}function n(){for(var e,t=0;t<o.length;t++){for(var n=o[t],r=!0,a=1;a<n.length;a++){var s=n[a];0!==c[s]&&(r=!1)}r&&(o.splice(t--,1),e=u(u.s=n[0]))}return e}var r={},c={app:0},o=[];function u(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,u),n.l=!0,n.exports}u.m=e,u.c=r,u.d=function(e,t,n){u.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},u.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},u.t=function(e,t){if(1&t&&(e=u(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(u.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)u.d(n,r,function(t){return e[t]}.bind(null,r));return n},u.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return u.d(t,"a",t),t},u.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},u.p="/";var a=window["webpackJsonp"]=window["webpackJsonp"]||[],s=a.push.bind(a);a.push=t,a=a.slice();for(var i=0;i<a.length;i++)t(a[i]);var l=s;o.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("cd49")},"040f":function(e,t,n){},"3b9f":function(e,t,n){"use strict";n("e513")},"426f":function(e,t,n){"use strict";n("040f")},7188:function(e,t,n){"use strict";n("89ba")},"89ba":function(e,t,n){},cd49:function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var r=n("f2bf"),c={id:"navigation"},o=Object(r["h"])("Home"),u=Object(r["h"])("Home"),a=Object(r["g"])("div",{class:"spacer"},null,-1),s=Object(r["h"])("Login");function i(e,t,n,i,l,b){var d=Object(r["y"])("router-link"),f=Object(r["y"])("router-view");return Object(r["r"])(),Object(r["f"])(r["a"],null,[Object(r["g"])("div",c,[null===e.session.user?(Object(r["r"])(),Object(r["d"])(d,{key:0,to:"/"},{default:Object(r["F"])((function(){return[o]})),_:1})):(Object(r["r"])(),Object(r["d"])(d,{key:1,to:"/dashboard"},{default:Object(r["F"])((function(){return[u]})),_:1})),a,null!==e.session.user?(Object(r["r"])(),Object(r["d"])(d,{key:2,to:"/profile"},{default:Object(r["F"])((function(){return[Object(r["h"])(Object(r["A"])(e.session.user.firstName),1)]})),_:1})):Object(r["e"])("",!0),null!==e.session.user?(Object(r["r"])(),Object(r["f"])("a",{key:3,class:"cursor-pointer",onClick:t[0]||(t[0]=function(t){return e.logout()})}," Logout ")):Object(r["e"])("",!0),null===e.session.user?(Object(r["r"])(),Object(r["d"])(d,{key:4,to:"/login"},{default:Object(r["F"])((function(){return[s]})),_:1})):Object(r["e"])("",!0)]),Object(r["i"])(f,{id:"content"})],64)}var l=n("d4ec"),b=n("bee2"),d=n("262e"),f=n("2caf"),j=n("9ab4"),p=n("ce1f"),O=(n("d3b7"),n("e9c4"),function(){function e(t,n,r,c){Object(l["a"])(this,e),this.id=t,this.username=n,this.firstName=r,this.lastName=c}return Object(b["a"])(e,null,[{key:"wrap",value:function(t){return new e(t["id"],t["username"],t["firstName"],t["lastName"])}}]),e}());function h(e){return"/api".concat(e)}var g=function(){function e(){Object(l["a"])(this,e),this.user=null,this.token=null}return Object(b["a"])(e,[{key:"register",value:function(e){return fetch(h("/users/"),{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(e)}).then((function(e){return 200===e.status?e.json().then((function(e){return O.wrap(e)})):e.json().then((function(e){return Promise.reject(e)}))}))}},{key:"login",value:function(e){var t=this;return fetch(h("/session/"),{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(e)}).then((function(e){return 200===e.status?e.json().then((function(e){return t.token=e["token"],t.user=O.wrap(e["user"]),t.user})):e.json().then((function(e){return Promise.reject(e)}))}))}},{key:"logout",value:function(){return this.user=null,this.token=null,Promise.resolve()}}]),e}(),v=Object(r["w"])(new g),m=function(e){Object(d["a"])(n,e);var t=Object(f["a"])(n);function n(){var e;return Object(l["a"])(this,n),e=t.apply(this,arguments),e.session=v,e}return Object(b["a"])(n,[{key:"logout",value:function(){this.$router.push({name:"login"}).then((function(){return v.logout()}))}}]),n}(p["b"]);m=Object(j["a"])([Object(p["a"])({})],m);var y=m,w=(n("7188"),n("6b0d")),k=n.n(w);const C=k()(y,[["render",i]]);var S=C,_=n("6c02"),N=Object(r["g"])("h1",null,"Welcome to Groupie!",-1),P=Object(r["g"])("p",null," Groupie is an app that helps you discover and coordinate fun events to attend with your friends. Simply sign up, create a new event by selecting a handful of activities for invitees to choose from, and share it with your friends! To get started, register or login. ",-1),G=[N,P];function x(e,t){return Object(r["r"])(),Object(r["f"])("div",null,G)}const T={},U=k()(T,[["render",x]]);var F=U,V=function(e){return Object(r["v"])("data-v-5cbccb4a"),e=e(),Object(r["s"])(),e},L={class:"small"},M=V((function(){return Object(r["g"])("h1",null,"Login",-1)})),A={class:"form"},E={class:"group"},J=V((function(){return Object(r["g"])("label",null,"Username",-1)})),D={class:"group"},H=V((function(){return Object(r["g"])("label",null,"Password",-1)})),R={class:"buttons"},$=Object(r["h"])("Register");function I(e,t,n,c,o,u){var a=Object(r["y"])("router-link");return Object(r["r"])(),Object(r["f"])("div",L,[M,Object(r["g"])("div",A,[Object(r["g"])("div",E,[Object(r["G"])(Object(r["g"])("input",{id:"username",type:"text",placeholder:"username","onUpdate:modelValue":t[0]||(t[0]=function(t){return e.credentials.username=t})},null,512),[[r["C"],e.credentials.username]]),J]),Object(r["g"])("div",D,[Object(r["G"])(Object(r["g"])("input",{id:"password",type:"password",placeholder:"password","onUpdate:modelValue":t[1]||(t[1]=function(t){return e.credentials.password=t})},null,512),[[r["C"],e.credentials.password]]),H]),Object(r["G"])(Object(r["g"])("p",{class:"error"},Object(r["A"])(e.error),513),[[r["D"],e.error]]),Object(r["g"])("div",R,[Object(r["g"])("button",{onClick:t[2]||(t[2]=function(){return e.onClickSubmit&&e.onClickSubmit.apply(e,arguments)})},"Submit"),Object(r["i"])(a,{to:"/register",class:"button"},{default:Object(r["F"])((function(){return[$]})),_:1})])])])}var W=function(e){Object(d["a"])(n,e);var t=Object(f["a"])(n);function n(){var e;return Object(l["a"])(this,n),e=t.apply(this,arguments),e.credentials={username:"",password:""},e.error="",e}return Object(b["a"])(n,[{key:"onClickSubmit",value:function(){var e=this;v.login(this.credentials).then((function(){e.$router.push({name:"dashboard"})})).catch((function(t){e.error=t["error"]}))}}]),n}(p["b"]);W=Object(j["a"])([Object(p["a"])({})],W);var Y=W;n("426f");const q=k()(Y,[["render",I],["__scopeId","data-v-5cbccb4a"]]);var z=q,B=function(e){return Object(r["v"])("data-v-c4809cd4"),e=e(),Object(r["s"])(),e},K={class:"small"},Q=B((function(){return Object(r["g"])("h1",null,"Register",-1)})),X={class:"form"},Z={class:"group"},ee=B((function(){return Object(r["g"])("label",null,"Username",-1)})),te={class:"group"},ne=B((function(){return Object(r["g"])("label",null,"Password",-1)})),re={class:"group"},ce=B((function(){return Object(r["g"])("label",null,"Confirm password",-1)})),oe={class:"group"},ue=B((function(){return Object(r["g"])("label",null,"First name",-1)})),ae={class:"group"},se=B((function(){return Object(r["g"])("label",null,"Last name",-1)})),ie={class:"buttons"},le=Object(r["h"])("Log in");function be(e,t,n,c,o,u){var a=Object(r["y"])("router-link");return Object(r["r"])(),Object(r["f"])("div",K,[Q,Object(r["g"])("div",X,[Object(r["g"])("div",Z,[Object(r["G"])(Object(r["g"])("input",{id:"username",type:"text",placeholder:"username","onUpdate:modelValue":t[0]||(t[0]=function(t){return e.credentials.username=t})},null,512),[[r["C"],e.credentials.username]]),ee]),Object(r["g"])("div",te,[Object(r["G"])(Object(r["g"])("input",{id:"password",type:"password",placeholder:"password","onUpdate:modelValue":t[1]||(t[1]=function(t){return e.credentials.password=t})},null,512),[[r["C"],e.credentials.password]]),ne]),Object(r["g"])("div",re,[Object(r["G"])(Object(r["g"])("input",{id:"password2",type:"password",placeholder:"password","onUpdate:modelValue":t[2]||(t[2]=function(t){return e.passwordConfirm=t})},null,512),[[r["C"],e.passwordConfirm]]),ce]),Object(r["g"])("div",oe,[Object(r["G"])(Object(r["g"])("input",{id:"first-name",type:"text",placeholder:"First name","onUpdate:modelValue":t[3]||(t[3]=function(t){return e.credentials.firstName=t})},null,512),[[r["C"],e.credentials.firstName]]),ue]),Object(r["g"])("div",ae,[Object(r["G"])(Object(r["g"])("input",{id:"last-name",type:"text",placeholder:"username","onUpdate:modelValue":t[4]||(t[4]=function(t){return e.credentials.lastName=t})},null,512),[[r["C"],e.credentials.lastName]]),se]),Object(r["G"])(Object(r["g"])("p",{class:"error"},Object(r["A"])(e.error),513),[[r["D"],e.error]]),Object(r["g"])("div",ie,[Object(r["g"])("button",{onClick:t[5]||(t[5]=function(){return e.onClickSubmit&&e.onClickSubmit.apply(e,arguments)})},"Register"),Object(r["i"])(a,{to:"/login",class:"button"},{default:Object(r["F"])((function(){return[le]})),_:1})])])])}var de=function(e){Object(d["a"])(n,e);var t=Object(f["a"])(n);function n(){var e;return Object(l["a"])(this,n),e=t.apply(this,arguments),e.passwordConfirm="",e.error="",e.credentials={username:"",password:"",firstName:"",lastName:""},e}return Object(b["a"])(n,[{key:"onClickSubmit",value:function(){var e=this;v.register(this.credentials).then((function(){return e.$router.push({name:"login"})})).catch((function(t){e.error=t.error}))}}]),n}(p["b"]);de=Object(j["a"])([Object(p["a"])({})],de);var fe=de;n("3b9f");const je=k()(fe,[["render",be],["__scopeId","data-v-c4809cd4"]]);var pe=je,Oe={class:"about"},he=Object(r["g"])("h1",null,"This is an about page",-1),ge=[he];function ve(e,t){return Object(r["r"])(),Object(r["f"])("div",Oe,ge)}const me={},ye=k()(me,[["render",ve]]);var we=ye,ke=Object(r["g"])("p",null," Welcome back to Groupie! To get started, use the below menus to create or manage your events. Don't forget you can have multiple events open at once. ",-1),Ce=Object(r["g"])("div",null,[Object(r["g"])("h2",null,"My Events"),Object(r["g"])("p",null,"You don't have any events yet. Create one below!")],-1),Se=Object(r["g"])("div",null,[Object(r["g"])("h2",null,"New Event")],-1);function _e(e,t,n,c,o,u){return Object(r["r"])(),Object(r["f"])("div",null,[Object(r["g"])("h1",null,"Hi, "+Object(r["A"])(e.session.user.firstName),1),ke,Ce,Se])}var Ne=function(e){Object(d["a"])(n,e);var t=Object(f["a"])(n);function n(){var e;return Object(l["a"])(this,n),e=t.apply(this,arguments),e.session=v,e}return n}(p["b"]);Ne=Object(j["a"])([Object(p["a"])({})],Ne);var Pe=Ne;const Ge=k()(Pe,[["render",_e]]);var xe=Ge;function Te(e,t,n){null===v.user?n({name:"login"}):n()}var Ue=[{path:"/",name:"home",component:F},{path:"/login/",name:"login",component:z},{path:"/profile/",name:"profile",component:we,beforeEnter:Te},{path:"/register/",name:"register",component:pe},{path:"/dashboard/",name:"dashboard",component:xe,beforeEnter:Te}],Fe=Object(_["a"])({history:Object(_["b"])("/"),routes:Ue}),Ve=Fe;Object(r["c"])(S).use(Ve).mount("#app")},e513:function(e,t,n){}});
//# sourceMappingURL=app.f10e876a.js.map