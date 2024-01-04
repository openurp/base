[#ftl]
<div class="semester-bar">
  [@b.form name=tag.formName action=tag.action! target=tag.target! method="get"]
  <table style="width: 100%;">
    <tbody>
      <tr>
        <td style="width: 220px;padding: 2px 0px 0px 10px;">
          [#assign submit=tag.submit!"bg.form.submit('${tag.formName}')"/]
          [#if tag.value??]
            [@base.semester id=tag.id name=tag.name required=tag.required label="学年学期" value=tag.value onchange=submit/]
          [#else]
            [@base.semester id=tag.id name=tag.name required=tag.required label="学年学期" onchange=submit/]
          [/#if]
        </td>
        <td style="padding: 0px 10px 0px 0px;">
          ${tag.body}
        </td>
      </tr>
    </tbody>
  </table>
  [/@]
</div>
