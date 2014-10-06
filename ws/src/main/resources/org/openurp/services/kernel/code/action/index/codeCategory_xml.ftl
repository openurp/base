[#ftl attributes={"content_type":"application/xml"}]
<?xml version="1.0" encoding="UTF-8"?>
<codes>
[#list codes as code]
<code id="${code.id}" name="${code.name}" indexno="${code.indexno}"/>[#t]
[/#list]
</codes>
