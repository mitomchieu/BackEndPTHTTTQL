package com.backend.template.common.annotations.api;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get OK",
            content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
            }),
        @ApiResponse(responseCode = "400", description = "Bad request",
                content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                }),
        @ApiResponse(responseCode = "404", description = "Not found",
                content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                }),
        @ApiResponse(responseCode = "500", description =  "Internal error",
                content = {
                        @Content(mediaType = MediaType.APPLICATION_JSON_VALUE),
                })
})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiCommonResponse {
}
