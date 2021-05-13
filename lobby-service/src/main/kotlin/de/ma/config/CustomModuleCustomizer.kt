package de.ma.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.ma.util.PagedResponse
import io.quarkus.jackson.ObjectMapperCustomizer
import javax.inject.Singleton

@Singleton
class CustomModuleCustomizer : ObjectMapperCustomizer {

    override fun customize(objectMapper: ObjectMapper) {
        objectMapper.registerKotlinModule()
    }
}

class PagedResponseDeserializer: JsonDeserializer<PagedResponse<*>>(), ContextualDeserializer{

    var valueType: JavaType? = null

    override fun createContextual(ctxt: DeserializationContext?, property: BeanProperty?): JsonDeserializer<*> {
        val wrapperType: JavaType = property!!.type
        val valueType: JavaType = wrapperType.containedType(0)
        val deserializer = PagedResponseDeserializer()
        deserializer.valueType = valueType
        return deserializer
    }

    override fun deserialize(p0: JsonParser?, ctxt: DeserializationContext): PagedResponse<*> {
        val pagedResponse = PagedResponse<Any>()
        pagedResponse.content = ctxt.readValue(p0, valueType)
        return pagedResponse

    }

}
