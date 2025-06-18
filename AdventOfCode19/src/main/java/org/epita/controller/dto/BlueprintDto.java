package org.epita.controller.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BlueprintDto {
    public Integer bestBlueprint;
    public List<BlueprintOutput> blueprints;
}
