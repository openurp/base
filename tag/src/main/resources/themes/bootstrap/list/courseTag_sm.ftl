<li>
[#if tag.option??][#assign optionTemplate=tag.option?interpret][/#if]
[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}:</label>[/#if]
[#assign selected=false/]
<select id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}" [#if tag.width??]width="${tag.width}"[/#if] [#if tag.multiple??]multiple="${tag.multiple}"[/#if]${tag.parameterString}>
${tag.body}
[#if tag.empty??]<option value=""></option>[/#if][#rt/]
[#list tag.values as v]
  [#if v?is_hash]
    <option value="${v[tag.keyName]!}" selected="selected">${v[tag.valueName]!}</option>
  [#else]
    <option value="${v}" selected="selected">${v}</option>
  [/#if]
[/#list]
</select>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
<script type="text/javascript">
  beangle.load(["chosen","bui-ajaxchosen"],function(){
    $("#${tag.id}").ajaxchosen(
    { method:"GET",
      url:"${tag.href}"
    },
    function (obj){
      var is_restapi = Array.isArray(obj);
      var datas = is_restapi?obj:obj.data;
      var items=[]
      jQuery.each(datas,function(i,data){
        var title = is_restapi?data.${tag.valueName}:data.attributes.${tag.valueName}
        items.push({"value":data.${tag.keyName},"text":title});
       });
       return items;
    },{placeholder_text_single:"${tag.empty!'...'}",search_contains:true,allow_single_deselect:true[#if tag.width??],width:'${tag.width}'[/#if]}
    );
  });
</script>
</li>
