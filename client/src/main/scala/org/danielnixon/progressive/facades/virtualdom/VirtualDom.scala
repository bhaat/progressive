package org.danielnixon.progressive.facades.virtualdom

import org.danielnixon.saferdom.Node

import scala.scalajs.js

/**
  * Facade for virtual-dom.
  * @see https://github.com/Matt-Esch/virtual-dom
  */
@js.native
class VirtualDom extends js.Object {
  def diff(previous: VTree, current: VTree): PatchObject = js.native

  def patch(rootNode: Node, patches: PatchObject): Node = js.native
}

@js.native
class VTree extends js.Object

@js.native
class PatchObject extends js.Object
