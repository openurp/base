/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.base.web.helper

import org.beangle.commons.bean.Properties
import org.beangle.commons.bean.orderings.PropertyOrdering
import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.model.Entity

object Matrix {

  case class Row(keys: Seq[Any], counters: Array[Double])

  case class Dimension(name: String, title: String, values: Map[Any, String]) {
    def keys: Iterable[Any] = {
      values.keys
    }

    def get(key: Any): Option[String] = values.get(key)
  }

}

class Matrix(val dimensions: Seq[Matrix.Dimension], val datas: collection.Seq[Matrix.Row]) {

  def getDimension(name: String): Matrix.Dimension = {
    dimensions.find(_.name == name).head
  }

  def groupBy(dimensionNames: String): Matrix = {
    val names = Strings.split(dimensionNames)
    val newDimensions = Collections.newBuffer[Matrix.Dimension]
    names foreach { n => newDimensions.addAll(dimensions.find(x => x.name == n)) }
    groupBy(newDimensions.toSeq)
  }

  def groupBy(newDimensions: Seq[Matrix.Dimension]): Matrix = {
    val indices = newDimensions.map(x => dimensions.indexOf(x))
    val newRows = Collections.newBuffer[Matrix.Row]
    datas.groupBy(d => indices.map(x => d.keys(x))) foreach { d =>
      val counters = d._2.map(_.counters)
      val rs = new Array[Double](datas.head.counters.length)
      for (i <- counters.indices; j <- counters(i).indices) {
        rs(j) += counters(i)(j)
      }
      newRows.addOne(Matrix.Row(d._1, rs))
    }
    new Matrix(newDimensions, newRows)
  }

  def split(dimensionName: String): Map[Any, Matrix] = {
    val dimension = getDimension(dimensionName)
    val dIdx = dimensions.indexOf(dimension)
    datas.groupBy(d => d.keys(dIdx)).map { d =>
      (d._1, new Matrix(this.dimensions, d._2))
    }
  }

  def getCounter(keys: AnyRef*): Option[Any] = {
    val convertedKeys = keys.map {
      case e: Entity[_] => e.id
      case x => x
    }
    datas.find(x => x.keys == convertedKeys).map(_.counters)
  }

  def sum: Array[Double] = {
    val rs = new Array[Double](datas.head.counters.length)
    for (i <- datas.indices; j <- datas(i).counters.indices) {
      rs(j) += datas(i).counters(j)
    }
    rs
  }
}
