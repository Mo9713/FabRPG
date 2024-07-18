package mc.blasing.fabrpg.skills;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import mc.blasing.fabrpg.Fabrpg;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class SkillLoader {
    private static final Gson GSON = new Gson();
    private static final Identifier SKILLS_JSON_ID = Identifier.of(Fabrpg.MOD_ID, "skills.json");

    public static void register() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.of(Fabrpg.MOD_ID, "skills");
            }

            @Override
            public void reload(ResourceManager manager) {
                try {
                    List<Resource> resources = manager.getAllResources(SKILLS_JSON_ID);
                    for (Resource res : resources) {
                        try (InputStream stream = res.getInputStream()) {
                            JsonObject json = GSON.fromJson(new InputStreamReader(stream), JsonObject.class);
                            List<SkillDefinition> skillDefinitions = GSON.fromJson(json.get("skills"), new TypeToken<List<SkillDefinition>>(){}.getType());
                            SkillManager.loadSkillDefinitions(skillDefinitions);
                        }
                    }
                } catch (Exception e) {
                    Fabrpg.LOGGER.error("Error occurred while loading skill definitions", e);
                }
            }
        });
    }
}