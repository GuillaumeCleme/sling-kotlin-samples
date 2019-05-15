package com.github.guillaumec91.core.models

import org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE

import com.day.cq.wcm.api.PageManager
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.models.annotations.Default
import org.apache.sling.models.annotations.Model
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy
import org.apache.sling.models.annotations.injectorspecific.OSGiService
import org.apache.sling.models.annotations.injectorspecific.SlingObject
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue
import org.apache.sling.settings.SlingSettingsService
import javax.annotation.PostConstruct

@Model(adaptables = [Resource::class])
class HelloWorldKotlinModel {

    private var message: String = ""

    @ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = ["No resourceType"])
    protected var resourceType: String? = null

    @OSGiService
    private val settings: SlingSettingsService? = null

    @SlingObject
    private val currentResource: Resource? = null

    @SlingObject
    private val resourceResolver: ResourceResolver? = null

    @PostConstruct
    protected fun init() {
        val pageManager = resourceResolver?.adaptTo(PageManager::class.java)
        val currentPage = pageManager?.getContainingPage(currentResource)

        message = ("\tHello World!\n" +
                "\tThis is instance: ${settings?.slingId} \n" +
                "\tResource type is: $resourceType \n" +
                "\tCurrent page is:  ${currentPage?.path} \n")
    }

}