package org.openurp.base.web.action

import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.view.View

class CodeAction extends ActionSupport {

  def index: View = {
    forward()
  }
}
