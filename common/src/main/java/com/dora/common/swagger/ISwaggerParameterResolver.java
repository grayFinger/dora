package com.dora.common.swagger;

import java.util.List;
import springfox.documentation.service.Parameter;

public interface ISwaggerParameterResolver {
    void addParameter(List<Parameter> params);
}
