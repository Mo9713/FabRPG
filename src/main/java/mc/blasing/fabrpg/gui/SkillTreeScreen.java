package mc.blasing.fabrpg.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import mc.blasing.fabrpg.skills.SkillTree;
import mc.blasing.fabrpg.skills.SkillTreeNode;
import net.minecraft.util.Identifier;

public class SkillTreeScreen extends Screen {
    private final SkillTree skillTree;
    private final Screen parent;
    private static final int NODE_SIZE = 20;
    private static final int NODE_SPACING = 40;
    private static final Identifier TEXTURE = Identifier.of("fabrpg", "textures/gui/skill_tree.png");

    public SkillTreeScreen(Screen parent, SkillTree skillTree) {
        super(Text.literal("Skill Tree: " + skillTree.getSkillId()));
        this.parent = parent;
        this.skillTree = skillTree;
    }

    @Override
    protected void init() {
        super.init();
        for (SkillTreeNode node : skillTree.getNodes()) {
            int x = this.width / 2 + node.getX() * NODE_SPACING - NODE_SIZE / 2;
            int y = 50 + node.getY() * NODE_SPACING - NODE_SIZE / 2;

            this.addDrawableChild(ButtonWidget.builder(Text.literal(node.getAbility().getName()), button -> {
                // Handle node click
                if (canUnlockNode(node)) {
                    unlockNode(node);
                }
            }).dimensions(x, y, NODE_SIZE, NODE_SIZE).build());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        // Draw lines between connected nodes
        for (SkillTreeNode node : skillTree.getNodes()) {
            for (String prereqId : node.getPrerequisites()) {
                SkillTreeNode prereq = skillTree.getNode(prereqId);
                if (prereq != null) {
                    int startX = this.width / 2 + prereq.getX() * NODE_SPACING;
                    int startY = 50 + prereq.getY() * NODE_SPACING;
                    int endX = this.width / 2 + node.getX() * NODE_SPACING;
                    int endY = 50 + node.getY() * NODE_SPACING;

                    // Draw a line using fill method
                    if (startX == endX) {
                        // Vertical line
                        context.fill(startX, startY, startX + 1, endY, 0xFFFFFFFF);
                    } else if (startY == endY) {
                        // Horizontal line
                        context.fill(startX, startY, endX, startY + 1, 0xFFFFFFFF);
                    } else {
                        // Diagonal line (approximation)
                        int dx = Math.abs(endX - startX);
                        int dy = Math.abs(endY - startY);
                        int sx = startX < endX ? 1 : -1;
                        int sy = startY < endY ? 1 : -1;
                        int err = dx - dy;

                        while (true) {
                            context.fill(startX, startY, startX + 1, startY + 1, 0xFFFFFFFF);
                            if (startX == endX && startY == endY) break;
                            int e2 = 2 * err;
                            if (e2 > -dy) { err -= dy; startX += sx; }
                            if (e2 < dx) { err += dx; startY += sy; }
                        }
                    }
                }
            }
        }

        // Draw nodes
        for (SkillTreeNode node : skillTree.getNodes()) {
            int x = this.width / 2 + node.getX() * NODE_SPACING - NODE_SIZE / 2;
            int y = 50 + node.getY() * NODE_SPACING - NODE_SIZE / 2;
            context.drawTexture(TEXTURE, x, y, 0, 0, NODE_SIZE, NODE_SIZE, NODE_SIZE, NODE_SIZE);
        }

        // Draw node tooltips
        for (SkillTreeNode node : skillTree.getNodes()) {
            int x = this.width / 2 + node.getX() * NODE_SPACING - NODE_SIZE / 2;
            int y = 50 + node.getY() * NODE_SPACING - NODE_SIZE / 2;
            if (mouseX >= x && mouseX < x + NODE_SIZE && mouseY >= y && mouseY < y + NODE_SIZE) {
                context.drawTooltip(textRenderer, Text.literal(node.getAbility().getDescription()), mouseX, mouseY);
            }
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(TEXTURE, 0, 0, 0, 0, this.width, this.height, 32, 32);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private boolean canUnlockNode(SkillTreeNode node) {
        // Check if all prerequisites are unlocked
        for (String prereqId : node.getPrerequisites()) {
            SkillTreeNode prereq = skillTree.getNode(prereqId);
            if (prereq == null || !prereq.isUnlocked()) {
                return false;
            }
        }
        // Check if the player has enough skill points
        // This is a placeholder, replace with actual skill point logic
        return true;
    }

    private void unlockNode(SkillTreeNode node) {
        if (canUnlockNode(node)) {
            node.setUnlocked(true);
            // Deduct skill points
            // Apply the ability effect
            // Update the player's skill data
        }
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }
}