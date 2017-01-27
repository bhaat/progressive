package org.danielnixon.progressive

import org.danielnixon.saferdom.html.Element

/**
  * Document elements that Progressive requires.
  * @param body The document body.
  * @param announcementsElement A screen reader only element that Progressive can use to announce async page updates to
  *                             users of assistive technology.
  * @param errorElement An element that Progressive can use to display errors. Typically near the top of the page.
  */
final case class KeyElements(
  body: Element,
  announcementsElement: Element,
  errorElement: Element
)
