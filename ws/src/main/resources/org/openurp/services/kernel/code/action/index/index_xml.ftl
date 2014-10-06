[#ftl attributes={"content_type":"application/xml"}]
<?xml version="1.0" encoding="UTF-8"?>
<codes>
[#list codes as code]
<code id="${code.id}" code = "${code.code}" name="${code.name}" enName="${code.enName!}"/>[#t]
[/#list]
</codes>