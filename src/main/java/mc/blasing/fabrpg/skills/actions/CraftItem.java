package mc.blasing.fabrpg.skills.actions;

import java.util.Collections;

public class CraftItem extends Action {
    public CraftItem(String id, int experience) {
        super(id, "craft_item", Collections.emptyList(), experience, Collections.emptyList(), Collections.emptyList(), 0.0);
    }

    // Implement specific logic for crafting items here if needed
}
