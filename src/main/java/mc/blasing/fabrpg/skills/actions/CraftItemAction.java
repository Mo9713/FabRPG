package mc.blasing.fabrpg.skills.actions;

import java.util.Collections;
import java.util.List;

public class CraftItemAction extends Action {

    public CraftItemAction(String id, int experience) {
        super(id, "craft_item", Collections.emptyList(), experience);
    }

    // Implement specific logic for crafting items here if needed
    // For example, check if the crafted item matches certain criteria
}
