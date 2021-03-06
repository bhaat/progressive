package org.danielnixon.progressive.services

import org.danielnixon.progressive.extensions.dom.NodeListSeq
import org.scalajs.dom.Window
import org.scalajs.dom.html.Element
import org.scalajs.dom.raw.HTMLFormElement
import org.danielnixon.saferdom.implicits._

class FocusManagementService(window: Window, scrollOffset: () => Int, userAgentService: UserAgentService) {

  def setFocus(element: Element): Unit = {
    // Calling focus() can cause the page to jump around, even if the element being
    // focused is currently visible. Note the current offset and restore it after focusing.
    val originalX = window.pageXOffset
    val originalY = window.pageYOffset
    element.tabIndex = -1
    element.focus()
    window.scrollTo(originalX.toInt, originalY.toInt)

    // Now update the scroll position ourselves, ensuring that the top of the element
    // being focused is scrolled into view.
    val newY = {
      val rect = element.getBoundingClientRect
      val scrollTop = window.pageYOffset
      val clientTop = window.document.bodyOpt.map(_.clientTop).getOrElse(0)

      rect.top + scrollTop - clientTop - scrollOffset()
    }

    val shouldScroll = {
      val top = window.pageYOffset
      val bottom = top + window.innerHeight
      top > newY || newY > bottom
    }

    if (shouldScroll) {
      window.scrollTo(0, newY.toInt)
    }
  }

  def anythingHasFocus: Boolean = {
    window.document.querySelectorOpt(":focus").isDefined
  }

  // Dismiss keyboard on iOS.
  def dismissKeyboard(formElement: HTMLFormElement): Unit = {
    if (userAgentService.isTouchDevice) {
      val inputs = "textarea, input[type=text], input[type=password], input[type=datetime], input[type=datetime-local], input[type=date], input[type=month], input[type=time], input[type=week], input[type=number], input[type=email], input[type=url], input[type=search], input[type=tel], input[type=color]"
      formElement.querySelectorAll(inputs).collect({ case e: Element => e }).foreach(_.blur())
    }
  }
}
