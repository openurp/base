<li>
[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}:</label>[/#if]
[#assign selected=false/]
<select id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}" [#if tag.width??]width="${tag.width}"[/#if] [#if tag.multiple??]multiple="${tag.multiple}"[/#if]${tag.parameterString}>
${tag.body}
[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#rt/]
</select>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
<script type="text/javascript">
  if(sessionStorage.getItem("std.grades") != null){
    beangle.select.fillin("${tag.id}",beangle.data.parseCsv(sessionStorage.getItem("std.grades")),"[#list tag.keys as k]${k}[#sep],[/#list]","${tag.keyName}","${tag.valueName}");
  }else{
      jQuery.ajax({
        url: "${tag.href}",
        headers:{"Accept":"application/json"},
        success:function (obj){
          var rows = beangle.select.fillin("${tag.id}",obj,"[#list tag.keys as k]${k}[#sep],[/#list]","${tag.keyName}","${tag.valueName}");
          sessionStorage.setItem("std.grades",beangle.data.toCsv(rows));
        }
      });
  }
</script>
</li>
