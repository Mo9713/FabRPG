package mc.blasing.fabrpg.skills.experience;

public class DefaultExperienceCalculator implements ExperienceCalculator {
    private static final double EXPONENTIAL_GROWTH_FACTOR = 1.00282543;
    private static final int EXPONENTIAL_REFERENCE_LEVEL = 1000;
    private static final double EXPONENTIAL_REFERENCE_EXP = 1.0E7;
    private static final int LEVEL_BEGIN_LINEAR = 1000;
    private static final double LINEAR_EXP_PER_LEVEL = 30000.0;

    @Override
    public int getExperienceForNextLevel(int currentLevel) {
        if (currentLevel < LEVEL_BEGIN_LINEAR) {
            return (int) (EXPONENTIAL_REFERENCE_EXP * Math.pow(EXPONENTIAL_GROWTH_FACTOR, currentLevel - EXPONENTIAL_REFERENCE_LEVEL));
        } else {
            return (int) (EXPONENTIAL_REFERENCE_EXP * Math.pow(EXPONENTIAL_GROWTH_FACTOR, LEVEL_BEGIN_LINEAR - EXPONENTIAL_REFERENCE_LEVEL) +
                    LINEAR_EXP_PER_LEVEL * (currentLevel - LEVEL_BEGIN_LINEAR));
        }
    }
}