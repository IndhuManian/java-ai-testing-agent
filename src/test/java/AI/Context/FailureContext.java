
package AI.Context;

public class FailureContext {

    // Actual execution details
    private String scenarioName;
    private String status;
    private String exceptionType;
    private String errorMessage;
    private String stackTrace;
    private String failedStep;
    // Default constructor
    public FailureContext() {
    }

    // Parameterized constructor
    public FailureContext(
            String scenarioName,
            String failedStep,
            String status,
            String exceptionType,
            String errorMessage,
            String stackTrace) {

        this.scenarioName = scenarioName;
        this.failedStep = failedStep;
        this.status = status;
        this.exceptionType = exceptionType;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
    }

    // Getters and Setters

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getFailedStep() {
        return failedStep;
    }

    public void setFailedStep(String failedStep) {
        this.failedStep = failedStep;
    }
}
