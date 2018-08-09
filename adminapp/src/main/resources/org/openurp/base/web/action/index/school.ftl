[@b.head/]
[#include "/org/openurp/app/nav.ftl"/]
[@displayFrame org=org appName=appName /]
<script>
    jQuery(document).ready(function() {
      jQuery('li>a').each(function (i,e){
        if(e.href && e.href.indexOf('%7Bschool%7D') > -1){
          e.href=e.href.replace('%7Bschool%7D','${Parameters['school']}')
        }
      });
    });
</script>
[@b.foot/]
