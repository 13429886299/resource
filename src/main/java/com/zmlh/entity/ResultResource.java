package com.zmlh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultResource {
    private String resourceId;
    private String parentId;
    private String resourceName;
    private String resourceType;
    private Map<String, Object> resourceDesc;
}
