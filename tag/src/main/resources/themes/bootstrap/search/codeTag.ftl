<div class="search-item">
<label style="font-weight:inherit" for="${tag.id}">${tag.label}:</label>[#t/]
<select id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}" [#if tag.width??]width="${tag.width}"[/#if] [#if tag.multiple??]multiple="${tag.multiple}"[/#if]${tag.parameterString}>
${tag.body}
[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#rt/]
</select>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
<script type="text/javascript">
  var localCodes = sessionStorage.getItem("code.${tag.type}");
  var selected="";
  [#list tag.values as v]
    selected=[#if v?is_hash]"${v[tag.keyName]!}"[#else]"${v}"[/#if]
  [/#list]
  if(localCodes){
    beangle.select.fillin("${tag.id}",beangle.data.parseCsv(localCodes),selected,"${tag.keyName}","${tag.valueName}");
  }else{
    jQuery.ajax({
      url: "${tag.href}",
      headers:{"Accept":"application/json"},
      success:function (obj){
        var rows = beangle.select.fillin("${tag.id}",obj,selected,"${tag.keyName}","${tag.valueName}");
        sessionStorage.setItem("code.${tag.type}",beangle.data.toCsv(rows));
      }
    });
  }
</script>
</div>
