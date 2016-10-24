package org.danielnixon.progressive.services

import org.danielnixon.progressive.extensions.dom.NodeListSeq
import org.danielnixon.progressive.shared.Wart
import org.querki.jquery._
import org.scalajs.dom.Window
import org.scalajs.dom.html._
import org.scalajs.dom.raw.HTMLFormElement

class FocusManagementService(window: Window, mainElement: JQuery, userAgentService: UserAgentService) {

  def setFocus(element: JQuery): JQuery = {
    if (element.length > 0) {
      // Calling focus() can cause the page to jump around, even if the element being
      // focused is currently visible. Note the current offset and restore it after focusing.
      val originalX = window.pageXOffset
      val originalY = window.pageYOffset
      element.css("outline", "none").attr("tabindex", "-1").focus()
      window.scrollTo(originalX.toInt, originalY.toInt)

      // Now update the scroll position ourselves, ensuring that the top of the element
      // being focused is scrolled into view.
      val newY = element.offset.top - mainElement.offset.top - 10D
      val top = window.pageYOffset
      val bottom = top + window.innerHeight
      val shouldScroll = top > newY || newY > bottom
      if (shouldScroll) {
        window.scrollTo(0, newY.toInt)
      }
    }
    element
  }

  def anythingHasFocus = Option(window.document.querySelector(":focus")).isDefined

  // Dismiss keyboard on iOS.
  @SuppressWarnings(Array(Wart.AsInstanceOf))
  def dismissKeyboard(formElement: HTMLFormElement): Unit = {
    if (userAgentService.isTouchDevice) {
      val inputs = "textarea, input[type=text], input[type=password], input[type=datetime], input[type=datetime-local], input[type=date], input[type=month], input[type=time], input[type=week], input[type=number], input[type=email], input[type=url], input[type=search], input[type=tel], input[type=color]"
      formElement.querySelectorAll(inputs).foreach(_.asInstanceOf[Element].blur())
    }
  }
}
