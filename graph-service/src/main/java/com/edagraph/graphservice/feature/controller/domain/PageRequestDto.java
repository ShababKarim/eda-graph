package com.edagraph.graphservice.feature.controller.domain;

public record PageRequestDto<T>(Integer pageNumber, Integer pageSize, T example) {
}
