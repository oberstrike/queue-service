package de.ma.util

import io.quarkus.panache.common.Sort
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters
import javax.ws.rs.DefaultValue
import javax.ws.rs.QueryParam

class PagedRequest {
    @DefaultValue("0")
    @QueryParam("page")
    var page: Int = 0

    @DefaultValue("10")
    @QueryParam("pageSize")
    var pageSize: Int = 10
}


class SortedRequest {
    @QueryParam("sort")
    @DefaultValue("id")
    var sort: String = "id"

    @QueryParam("direction")
    @DefaultValue("ASC")
    var dir: Direction = Direction.ASC
}


enum class Direction(val content: Sort.Direction) {
    ASC(Sort.Direction.Ascending), DESC(Sort.Direction.Ascending);
}
