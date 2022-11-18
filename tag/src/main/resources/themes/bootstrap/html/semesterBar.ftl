[#ftl]
<div class="semester-bar">
  [@b.form name=tag.formName action=tag.action! target=tag.target! method="get"]
  <div style="margin-left:4px;margin-top:0px;float:left;height:22px;">
    ${tag.body}
  </div>
  <div style="margin-left:10px;margin-top:0px;float:left;height:22px;">
  [#assign submit=tag.submit!"bg.form.submit('${tag.formName}')"/]
  [#if tag.value??]
    [@urp_base.semester id=tag.id name=tag.name required=tag.required label="学年学期" value=tag.value onchange=submit/]
  [#else]
    [@urp_base.semester id=tag.id name=tag.name required=tag.required label="学年学期" onchange=submit/]
  [/#if]
  </div>
  [/@]
</div>
