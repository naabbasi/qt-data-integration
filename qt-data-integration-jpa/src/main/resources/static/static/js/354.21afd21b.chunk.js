"use strict";(self.webpackChunkapp=self.webpackChunkapp||[]).push([[354],{7354:function(e,n,t){t.r(n),t.d(n,{Index:function(){return b},default:function(){return h}});var r=t(2791),a=t(7890),l=t(2062),u=t(5854),o=t(9411);function i(){return i=Object.assign||function(e){for(var n=1;n<arguments.length;n++){var t=arguments[n];for(var r in t)Object.prototype.hasOwnProperty.call(t,r)&&(e[r]=t[r])}return e},i.apply(this,arguments)}function c(e,n){(null==n||n>e.length)&&(n=e.length);for(var t=0,r=new Array(n);t<n;t++)r[t]=e[t];return r}function s(e,n){return function(e){if(Array.isArray(e))return e}(e)||function(e,n){var t=null==e?null:"undefined"!==typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=t){var r,a,l=[],u=!0,o=!1;try{for(t=t.call(e);!(u=(r=t.next()).done)&&(l.push(r.value),!n||l.length!==n);u=!0);}catch(i){o=!0,a=i}finally{try{u||null==t.return||t.return()}finally{if(o)throw a}}return l}}(e,n)||function(e,n){if(e){if("string"===typeof e)return c(e,n);var t=Object.prototype.toString.call(e).slice(8,-1);return"Object"===t&&e.constructor&&(t=e.constructor.name),"Map"===t||"Set"===t?Array.from(e):"Arguments"===t||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t)?c(e,n):void 0}}(e,n)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}var m=r.memo(r.forwardRef((function(e,n){var t=s(r.useState(null),2),a=t[0],i=t[1],c=s((0,l.OR)({type:"click",listener:function(e){n&&n.current&&!n.current.contains(e.target)&&i(null)}}),1)[0],f=function(e,n){n.disabled?e.preventDefault():(n.url||e.preventDefault(),n.command&&n.command({originalEvent:e,item:n}),n.items?i(a&&n===a?null:n):C())},p=function(n,t){var r=n.currentTarget.parentElement;switch(n.which){case 40:e.root?t.items&&v(t,r):h(r),n.preventDefault();break;case 38:!e.root&&y(r),n.preventDefault();break;case 39:if(e.root){var a=g(r);a&&a.children[0].focus()}else t.items&&v(t,r);n.preventDefault();break;case 37:e.root&&y(r),n.preventDefault()}e.onKeyDown&&e.onKeyDown(n,r)},d=function(n,t){e.root?38===n.which&&null==t.previousElementSibling&&b(t):37===n.which&&b(t)},v=function(e,n){i(e),setTimeout((function(){n.children[1].children[0].children[0].focus()}),50)},b=function(e){i(null),e.parentElement.previousElementSibling.focus()},h=function(e){var n=g(e);n&&n.children[0].focus()},y=function(e){var n=E(e);n&&n.children[0].focus()},g=function e(n){var t=n.nextElementSibling;return t?u.p7.hasClass(t,"p-disabled")||!u.p7.hasClass(t,"p-menuitem")?e(t):t:null},E=function e(n){var t=n.previousElementSibling;return t?u.p7.hasClass(t,"p-disabled")||!u.p7.hasClass(t,"p-menuitem")?e(t):t:null},C=function(){i(null),e.onLeafClick&&e.onLeafClick()};(0,l.nw)((function(){c()})),(0,l.rf)((function(){!e.parentActive&&i(null)}),[e.parentActive]);var w=function(n,t){var l=n.label+"_"+t,c=(0,u.AK)("p-menuitem",{"p-menuitem-active":a===n},n.className),s=(0,u.AK)("p-menuitem-link",{"p-disabled":n.disabled}),v=(0,u.AK)("p-menuitem-icon",n.icon),b=(0,u.AK)("p-submenu-icon pi",{"pi-angle-down":e.root,"pi-angle-right":!e.root}),h=u.Cz.getJSXIcon(n.icon,{className:"p-menuitem-icon"},{props:e.menuProps}),y=n.label&&r.createElement("span",{className:"p-menuitem-text"},n.label),g=n.items&&r.createElement("span",{className:b}),E=function(n){return n.items?r.createElement(m,{menuProps:e.menuProps,model:n.items,mobileActive:e.mobileActive,onLeafClick:C,onKeyDown:d,parentActive:n===a}):null}(n),w=r.createElement("a",{href:n.url||"#",role:"menuitem",className:s,target:n.target,"aria-haspopup":null!=n.items,onClick:function(e){return f(e,n)},onKeyDown:function(e){return p(e,n)}},h,y,g,r.createElement(o.H,null));if(n.template){var N={onClick:function(e){return f(e,n)},onKeyDown:function(e){return p(e,n)},className:s,labelClassName:"p-menuitem-text",iconClassName:v,submenuIconClassName:b,element:w,props:e};w=u.gb.getJSXElement(n.template,n,N)}return r.createElement("li",{key:l,role:"none",className:c,style:n.style,onMouseEnter:function(t){return function(n,t){t.disabled||e.mobileActive?n.preventDefault():e.root?(a||e.popup)&&i(t):i(t)}(t,n)}},w,E)},N=function(e,n){return e.separator?function(e){var n="separator_"+e;return r.createElement("li",{key:n,className:"p-menu-separator",role:"separator"})}(n):w(e,n)},k=e.root?"menubar":"menu",A=(0,u.AK)({"p-submenu-list":!e.root,"p-menubar-root-list":e.root}),S=e.model?e.model.map(N):null;return r.createElement("ul",{ref:n,className:A,role:k},S)})));m.displayName="MenubarSub";var f=r.memo(r.forwardRef((function(e,n){var t=s(r.useState(!1),2),o=t[0],c=t[1],p=r.useRef(null),d=r.useRef(null),v=s((0,l.OR)({type:"click",listener:function(e){o&&g(e)&&c(!1)}}),2),b=v[0],h=v[1],y=function(e){e.preventDefault(),c((function(e){return!e}))},g=function(e){return p.current!==e.target&&!p.current.contains(e.target)&&d.current!==e.target&&!d.current.contains(e.target)};(0,l.rf)((function(){o?(u.P9.set("menu",p.current,a.ZP.autoZIndex,a.ZP.zIndex.menu),b()):(h(),u.P9.clear(p.current))}),[o]),(0,l.zq)((function(){u.P9.clear(p.current)})),r.useImperativeHandle(n,(function(){return{toggle:y,useCustomContent:useCustomContent}}));var E=u.gb.findDiffKeys(e,f.defaultProps),C=(0,u.AK)("p-menubar p-component",{"p-menubar-mobile-active":o},e.className),w=function(){if(e.start){var n=u.gb.getJSXElement(e.start,e);return r.createElement("div",{className:"p-menubar-start"},n)}return null}(),N=function(){if(e.end){var n=u.gb.getJSXElement(e.end,e);return r.createElement("div",{className:"p-menubar-end"},n)}return null}(),k=r.createElement("a",{ref:d,href:"#",role:"button",tabIndex:0,className:"p-menubar-button",onClick:y},r.createElement("i",{className:"pi pi-bars"})),A=r.createElement(m,{ref:p,menuProps:e,model:e.model,root:!0,mobileActive:o,onLeafClick:function(){c(!1)}});return r.createElement("div",i({id:e.id,className:C,style:e.style},E),w,k,A,N)})));f.displayName="Menubar",f.defaultProps={__TYPE:"Menubar",id:null,model:null,style:null,className:null,start:null,end:null};var p=t(6871),d=t(184),v=[{label:"QTerminals Integrations Portal",icon:"pi pi-fw pi-home"}],b=function(){var e=(0,d.jsx)("div",{}),n=(0,d.jsx)("div",{});return(0,d.jsxs)("div",{children:[(0,d.jsx)(f,{model:v,start:e,end:n}),(0,d.jsx)(p.j3,{})]})},h=b}}]);
//# sourceMappingURL=354.21afd21b.chunk.js.map