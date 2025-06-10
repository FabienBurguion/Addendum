package org.epita.service;

import org.epita.models.Blueprint;
import org.epita.service.io.BlueprintParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BlueprintService {
    public List<Blueprint> getBlueprints() throws IOException {
        return BlueprintParser.parseInput();
    }
}
