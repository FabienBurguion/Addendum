import org.epita.models.Blueprint;
import org.epita.models.Material;
import org.epita.models.ResourceType;
import org.epita.models.RobotProductionCost;
import org.epita.solver.Solver;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.util.Tuple;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolverTest {

    private static Tuple<int[], Integer> getIntegerTuple(Blueprint blueprint1) throws IOException {
        Blueprint blueprint2 = new Blueprint(List.of(
                new RobotProductionCost(List.of(new Material(2, ResourceType.ORE))),
                new RobotProductionCost(List.of(new Material(3, ResourceType.ORE))),
                new RobotProductionCost(List.of(
                        new Material(3, ResourceType.ORE),
                        new Material(8, ResourceType.CLAY))),
                new RobotProductionCost(List.of(
                        new Material(3, ResourceType.ORE),
                        new Material(12, ResourceType.OBSIDIAN))),
                new RobotProductionCost(List.of(
                        new Material(1, ResourceType.GEODE),
                        new Material(8, ResourceType.CLAY),
                        new Material(7, ResourceType.OBSIDIAN))
                )
        ));

        List<Blueprint> blueprints = List.of(blueprint1, blueprint2);

        BiFunction<Integer, Integer, Integer> scoreFn = (i, diamonds) -> (i + 1) * diamonds;
        IntBinaryOperator sum = Integer::sum;

        Solver solver = new Solver(blueprints, 24, blueprints.size(), scoreFn, 0, sum);

        return solver.run();
    }

    @Test
    void testRun_withTwoBlueprints_returnsCorrectValuesAndTotal() throws Exception {
        Blueprint blueprint1 = new Blueprint(List.of(
                new RobotProductionCost(List.of(new Material(4, ResourceType.ORE))),                 // ore robot
                new RobotProductionCost(List.of(new Material(2, ResourceType.ORE))),                 // clay robot
                new RobotProductionCost(List.of(
                        new Material(3, ResourceType.ORE),
                        new Material(14, ResourceType.CLAY))),                                      // obsidian robot
                new RobotProductionCost(List.of(
                        new Material(2, ResourceType.ORE),
                        new Material(7, ResourceType.OBSIDIAN))),                                   // geode robot
                new RobotProductionCost(List.of(
                        new Material(1, ResourceType.GEODE),
                        new Material(8, ResourceType.CLAY),
                        new Material(7, ResourceType.OBSIDIAN))                                     // diamond robot
                )
        ));

        Tuple<int[], Integer> result = getIntegerTuple(blueprint1);

        int[] values = result._1();
        int total = result._2();

        assertEquals(2, values.length);
        assertEquals((values[0]) + (2 * values[1]), total);
        assertEquals(23, total);
    }
}
