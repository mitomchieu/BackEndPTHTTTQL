package com.backend.template.base.common.ParameterObject;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParameter {
    @Parameter(
            description = "Search criteria in format field(:,<,>) value" +
                    ", example name:John",
            name = "search",
            array = @ArraySchema(
                    schema =  @Schema(
                            type = "string"
                    )
            )
    )
    private List<String> search;
}
