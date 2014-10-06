package org.openurp.base

import java.io.File
import java.util.Locale
import org.beangle.data.jpa.hibernate.tool.DdlGenerator
import org.hibernate.dialect.PostgreSQL82Dialect
import org.junit.runner.RunWith
import org.scalatest.{FunSpec, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class OrmTest extends FunSpec with Matchers {
  describe("DDL generator") {
    it("generate") {
      val generator = new DdlGenerator(new PostgreSQL82Dialect(), Locale.SIMPLIFIED_CHINESE)
      val dir = "/tmp"
      generator.gen(dir, null)
      val tables = new File(dir + "/1-tables.sql")
      assert(tables.exists())
      tables.delete()

      val sequences = new File(dir + "/2-sequences.sql")
      assert(sequences.exists())
      sequences.delete()

      val comments = new File(dir + "/3-comments.sql")
      assert(comments.exists())
      comments.delete()
    }
  }

}