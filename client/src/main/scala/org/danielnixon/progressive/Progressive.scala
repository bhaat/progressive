package org.danielnixon.progressive

import org.danielnixon.progressive.services._
import org.scalajs.dom
import org.querki.jquery._

class Progressive {
  def initialize(
    views: Views,
    elements: KeyElements,
    eventHandlers: EventHandlers
  ): Unit = {

    val historyService = new HistoryService(dom.window)
    val ajaxService = new AjaxService
    val eventHandlerSetupService = new EventHandlerSetupService($, eventHandlers.additionalSetupInitial, eventHandlers.additionalSetup)
    val refreshService = new RefreshService(Global.virtualDom, Global.vdomParser, eventHandlerSetupService, ajaxService, eventHandlers.applyDiff)
    val userAgentService = new UserAgentService(dom.window)

    val hijaxService = new HijaxService(
      dom.window,
      new QueryStringService(eventHandlers.includeInQueryString),
      historyService,
      userAgentService,
      new TransitionsService(dom.window, elements.announcementsElement, elements.errorElement, new AnimationService, views),
      new FocusManagementService(dom.window, elements.mainElement, userAgentService),
      refreshService,
      new EnableDisableService,
      ajaxService,
      eventHandlerSetupService,
      eventHandlers.preFormSubmit,
      eventHandlers.postFormSubmit
    )

    historyService.initializeHistory()
    dom.window.onpopstate = historyService.onPopState _

    eventHandlerSetupService.setupInitial($("body"), refreshService, hijaxService)
  }
}
