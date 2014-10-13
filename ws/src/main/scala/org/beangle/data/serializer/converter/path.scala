package org.beangle.data.serializer.converter

import org.beangle.commons.lang.reflect.BeanManifest
import org.beangle.data.serializer.io.StreamWriter
import org.beangle.data.serializer.mapper.Mapper
import org.beangle.data.serializer.marshal.MarshallingContext
import Type.Type
import java.beans.Transient
import java.lang.reflect.Modifier
import org.beangle.commons.bean.PropertyUtils

class BeanConverter(val mapper: Mapper) extends Converter[Object] {

  def marshal(source: Object, writer: StreamWriter, context: MarshallingContext): Unit = {
    val sourceType = source.getClass
    BeanManifest.get(sourceType).getters foreach {
      case (name, getter) =>
        if (Modifier.isPublic(getter.method.getModifiers()) && !name.contains("lone") && !getter.method.isAnnotationPresent(classOf[Transient])) {
          val value = extractOption(getter.method.invoke(source))
          if (null != value) {
            if (value.getClass().getName().contains("$$")) {
              writer.startNode(mapper.serializedMember(source.getClass(), name), value.getClass)
              writer.addAttribute("id",PropertyUtils.getProperty(value, "id").toString)
              writer.endNode()
            } else {
              writer.startNode(mapper.serializedMember(source.getClass(), name), value.getClass)
              context.convert(value, writer)
              writer.endNode()
            }

          }
        }
    }
  }

  override def support(clazz: Class[_]): Boolean = {
    !(clazz.getName.startsWith("java.") || clazz.getName.startsWith("scala.") || clazz.isArray)
  }

  override def targetType: Type = {
    Type.Object
  }
}

