package com.example.let.domain.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectResponseDto {
    private int status;
    private Object data;
}
