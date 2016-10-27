package org.danielnixon.progressive.facades.dom

import org.danielnixon.progressive.shared.Wart
import org.scalajs.dom.Element

import scala.scalajs.js

// https://developer.mozilla.org/en/docs/Web/API/Element/matches
@js.native
trait ElementMatches extends Element {
  def matches(selector: String): Boolean = js.native
}

object ElementMatches {
  @SuppressWarnings(Array(Wart.AsInstanceOf))
  implicit def element2ElementMatches(element: Element): ElementMatches = element.asInstanceOf[ElementMatches]
}