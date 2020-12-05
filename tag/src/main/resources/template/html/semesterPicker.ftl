[#ftl]
  [#if tag.label??]<label for="${tag.id}" class="form-check-label">${tag.label}:</label>[/#if]
  <input id="${tag.id}" class="sp-text sp-text-state-default" type="text" value="..." readonly="true" ${tag.parameterString}/>
  <div class="semester-picker"  tabIndex="-1">
    <input id="${tag.id}_target" type="hidden" name="${tag.name}" value="[#if (tag.value)??]${tag.value}[/#if]"/>
    <input id="${tag.id}_year" class="semester-picker-input" type="text" index="" value="" maxLength="10" size="10" readonly="true" style="display:none"/>
    <input id="${tag.id}_term" class="semester-picker-input" type="text" index="" value="" size="4" readonly="true" style="display:none"/>
    <table id="${tag.id}_yearTb" class="school_yearTb"><tbody></tbody></table>
    <div></div>
    <table id="${tag.id}_termTb" class="termTb"><tbody></tbody></table>
  </div>
  <script type="text/javascript">
    bg.requireCss("semester.css","${b.static_url('openurp-edu','css/')}");
    function initSelect${tag.id}() {
      var semesterOptions={empty:"${(tag.required!='true')?c}"[#if tag.onchange??],onChange:"${tag.onchange}"[/#if][#if (tag.value)??],value:"${tag.value}"[/#if]}
      semesterOptions.url="${tag.url}";
      jQuery("#${tag.id}").semester_picker(semesterOptions[#if tag.initCallback??],"${tag.initCallback}"[/#if]);
    }
    bg.require("${b.static_url('openurp-edu','js/semester.js')}",initSelect${tag.id});
  </script>
