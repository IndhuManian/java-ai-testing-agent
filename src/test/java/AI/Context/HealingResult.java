package AI.Context;
import org.openqa.selenium.By;
public class HealingResult {

    private By healedBy;
    private String scenarioName;
    private String originalLocator;
    private String healedLocator;
    private String locatorType;
    private String reason;
    private String confidence;
    private boolean healed;
    private boolean validated;

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getOriginalLocator() {
        return originalLocator;
    }

    public void setOriginalLocator(String originalLocator) {
        this.originalLocator = originalLocator;
    }

    public String getHealedLocator() {
        return healedLocator;
    }

    public void setHealedLocator(String healedLocator) {
        this.healedLocator = healedLocator;
    }

    public String getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(String locatorType) {
        this.locatorType = locatorType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public boolean isHealed() {
        return healed;
    }

    public void setHealed(boolean healed) {
        this.healed = healed;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public By getHealedBy() {
        return healedBy;
    }

    public void setHealedBy(By healedBy) {
        this.healedBy = healedBy;
    }
}