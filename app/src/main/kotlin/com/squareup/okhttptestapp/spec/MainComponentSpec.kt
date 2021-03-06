package com.squareup.okhttptestapp.spec

import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.sections.widget.RecyclerCollectionEventsController
import com.facebook.yoga.YogaEdge
import com.makeramen.litho.children
import com.makeramen.litho.column
import com.makeramen.litho.component
import com.makeramen.litho.layout
import com.squareup.okhttptestapp.model.AppEvent
import com.squareup.okhttptestapp.model.ClientOptions
import com.squareup.okhttptestapp.model.RequestOptions

@LayoutSpec
object MainComponentSpec {
  @OnCreateLayout
  fun onCreateLayout(
      c: ComponentContext,
      @Prop initialRequestOptions: RequestOptions,
      @Prop initialClientOptions: ClientOptions,
      @Prop executeListener: (RequestOptions) -> Unit,
      @Prop configListener: (ClientOptions) -> Unit,
      @Prop gmsAvailable: Boolean,
      @Prop results: List<AppEvent>,
      @Prop scrollController: RecyclerCollectionEventsController): ComponentLayout = layout {
    column(c) {
      paddingDip(YogaEdge.ALL, 8f)
      children {
        component(c, QueryComponent::create) {
          initialRequestOptions(initialRequestOptions)
          executeListener(executeListener)
          flexGrow(0f)
        }
        component(c, ClientConfigComponent::create) {
          initialClientOptions(initialClientOptions)
          gmsAvailable(gmsAvailable)
          configListener(configListener)
          flexGrow(0f)
        }
        component(c, RecyclerCollectionComponent::create) {
          section(ResultsListSection.create(SectionContext(c)).results(results))
          disablePTR(true)
          flexGrow(1f)
          eventsController(scrollController)
        }
      }
    }
  }
}