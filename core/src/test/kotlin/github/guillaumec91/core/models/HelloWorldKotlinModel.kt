package github.guillaumec91.core.models

import com.github.guillaumec91.core.models.HelloWorldKotlinModel

import org.apache.commons.lang3.StringUtils
import org.apache.sling.api.resource.Resource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.day.cq.wcm.api.Page
import io.wcm.testing.mock.aem.junit5.AemContext
import io.wcm.testing.mock.aem.junit5.AemContextExtension

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue

/**
 * Simple JUnit test verifying the HelloWorldModel
 */
@ExtendWith(AemContextExtension::class)
internal class HelloWorldKotlinModelTest {

    private var hello: HelloWorldKotlinModel? = null

    private var page: Page? = null
    private var resource: Resource? = null

    @BeforeEach
    @Throws(Exception::class)
    fun setup(context: AemContext) {

        // prepare a page with a test resource
        page = context.create().page("/content/mypage")
        resource = context.create().resource(page!!, "hello",
                "sling:resourceType", "kotlinsamples/components/content/helloworld")

        // create sling model
        hello = resource!!.adaptTo(HelloWorldKotlinModel::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun testGetMessage() {
        // some very basic junit tests
        val msg = hello!!.message
        assertNotNull(msg)
        assertTrue(StringUtils.contains(msg, resource!!.resourceType))
        assertTrue(StringUtils.contains(msg, page!!.path))
    }

}
