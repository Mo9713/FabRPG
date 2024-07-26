package mc.blasing.fabrpg.skills.actions;

import java.util.Collections;
import java.util.List;

public class BreakBlock extends Action {
    public BreakBlock(String id, List<String> blocks, int experience) {
        super(id, "break_block", Collections.emptyList(), experience, Collections.emptyList(), Collections.emptyList(), 0.0);
    }

    // Implement specific logic for breaking blocks here if needed
}
