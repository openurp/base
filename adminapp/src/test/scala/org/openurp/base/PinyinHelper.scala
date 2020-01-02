/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.base

import java.io.File

import net.sourceforge.pinyin4j.format.{HanyuPinyinCaseType, HanyuPinyinOutputFormat, HanyuPinyinToneType}
import net.sourceforge.pinyin4j.{PinyinHelper => PHelper}
import org.beangle.commons.collection.Collections
import org.beangle.commons.io.Files.writeOpen
import org.beangle.commons.io.{Files, IOs}
import org.beangle.commons.lang.Strings.{capitalize, substringBefore}
import org.beangle.commons.lang.{Charsets, Strings}

object PinyinHelper {

  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      println("Usage:PinyinHelper in.csv out.sql")
      return
    }
    val lines = Files.readLines(new File(args(0)))
    val outfile = new File(args(1))
    val out = writeOpen(outfile)

    val sqls = Collections.newBuffer[String]
    lines foreach { line =>
      val code = Strings.substringBefore(line, ",")
      val name = Strings.substringAfter(line, ",")
      val sql = if (name.contains('(')) {
        toSql(code, name, toPerson(substringBefore(name, "(")))
      } else if (name.contains("（")) {
        toSql(code, name, toPerson(substringBefore(name, "（")))
      } else if (!name.contains("·")) {
        toSql(code, name, toPerson(name))
      } else {
        ""
      }
      if (Strings.isNotBlank(sql)) {
        sqls += sql
      }
      if (sqls.length >= 100) {
        sqls foreach { s =>
          IOs.write(s, out, Charsets.UTF_8)
        }
        sqls.clear()
      }
    }
    if (sqls.nonEmpty) {
      sqls foreach { s =>
        IOs.write(s, out, Charsets.UTF_8)
      }
    }
    IOs.close(out)
  }

  def toPinyin(name: String): String = {
    val builder = new StringBuilder()
    val arr = name.toCharArray
    val defaultFormat = new HanyuPinyinOutputFormat()
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE)
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)
    (0 until arr.length) foreach { i =>
      val pinyinArray = PHelper.toHanyuPinyinStringArray(arr(i), defaultFormat)
      if (null != pinyinArray && pinyinArray.nonEmpty) {
        builder.append(pinyinArray(0))
      } else {
        builder.append(arr(i))
      }
    }
    builder.toString()
  }

  def toSql(code: String, name: String, pinyin: String): String = {
    "update xsxx_t set xm_en='" + pinyin + "' where xh='" + code + "';\n"
  }

  def toPerson(name: String): String = {
    val builder = new StringBuilder()
    val arr = name.toCharArray
    val defaultFormat = new HanyuPinyinOutputFormat()
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE)
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)
    (0 until arr.length) foreach { i =>
      val pinyinArray = PHelper.toHanyuPinyinStringArray(arr(i), defaultFormat)
      if (null != pinyinArray && pinyinArray.nonEmpty) {
        val rs = pinyinArray(0)
        i match {
          case 0 => builder.append(capitalize(rs))
          case 1 => builder.append(" ").append(capitalize(rs))
          case _ => builder.append(rs)
        }
      } else {
        builder.append(arr(i))
      }
    }
    builder.toString()
  }
}
