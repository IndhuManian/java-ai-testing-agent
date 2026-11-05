# Java Selenium AI Automation Framework

A Java-based UI automation framework that combines a traditional
Selenium automation foundation with AI-agent-oriented failure analysis
and runtime self-healing locator capabilities.

The project is being developed incrementally on top of an existing
**Selenium + Cucumber BDD + TestNG + Page Object Model (POM)**
framework. The goal is to evolve an established automation framework
into an agent-assisted testing framework without replacing its working
foundation.

> **Project status:** The failure collection and structured-context
> foundation, Java agent tools, simulated decision loop, and initial
> runtime self-healing locator flow have been implemented. Real
> LLM-based decision-making is a future phase and is not currently
> required for the demonstrated flows.

------------------------------------------------------------------------

## Table of Contents

-   [Project Objectives](#project-objectives)
-   [Technology Stack](#technology-stack)
-   [Existing Automation Foundation](#existing-automation-foundation)
-   [Framework Architecture](#framework-architecture)
-   [AI Agent and Failure Analysis](#ai-agent-and-failure-analysis)
-   [Runtime Self-Healing Locators](#runtime-self-healing-locators)
-   [Self-Healing Workflow](#self-healing-workflow)
-   [Amazon.in Demonstration](#amazonin-demonstration)
-   [Project Structure](#project-structure)
-   [Execution and Reports](#execution-and-reports)
-   [Implementation Roadmap](#implementation-roadmap)
-   [Current Scope and Limitations](#current-scope-and-limitations)
-   [Future Enhancements](#future-enhancements)

------------------------------------------------------------------------

## Project Objectives

The project explores how an existing UI automation framework can be
enhanced with agent-oriented capabilities.

Key objectives:

-   Collect useful context when an automated test fails.
-   Represent failure information in a structured format.
-   Provide Java tools that an agent workflow can invoke to investigate
    failures.
-   Simulate a decision loop that selects tools based on the available
    failure information.
-   Inspect the live page DOM when a locator cannot be used.
-   Generate and validate alternative Selenium locators.
-   Retry supported browser actions using a validated locator.
-   Keep the QA engineer involved in reviewing recommendations and
    controlling future automated actions.

------------------------------------------------------------------------

## Technology Stack

  -----------------------------------------------------------------------
  Technology                          Purpose
  ----------------------------------- -----------------------------------
  Java                                Main programming language

  Maven                               Dependency and build management

  Selenium WebDriver                  Browser automation

  Cucumber BDD                        Feature files and step definitions

  TestNG                              Test execution

  Page Object Model                   Page-level organization and
                                      reusable actions

  Jsoup                               Parsing the captured HTML DOM

  Jackson / JSON utilities            Structured failure-context
                                      handling, as configured in the
                                      project

  Extent Reports / Cucumber reports   Test execution reporting

  OpenAI Java SDK                     Dependency added for a possible
                                      future LLM integration; not
                                      required for the current simulated
                                      workflow
  -----------------------------------------------------------------------

The project has been developed using Java 20 and an IntelliJ IDEA Maven
environment. Refer to the `pom.xml` in the repository for the exact
dependency versions.

------------------------------------------------------------------------

## Existing Automation Foundation

The AI-oriented capabilities are being added to an existing Java
automation framework rather than replacing it.

The existing framework uses:

-   Cucumber feature files to describe scenarios.
-   Step definition classes to connect Gherkin steps to Java methods.
-   Page classes to hold page locators and browser actions.
-   A shared browser setup through `BaseClass` and `DriverFactory`.
-   Hooks for scenario lifecycle handling and screenshot attachment.
-   Maven and TestNG for execution.

This structure remains the foundation for the newer agent and
self-healing components.

------------------------------------------------------------------------

## Framework Architecture

The framework has two related areas of functionality.

### 1. Test failure analysis

``` text
Cucumber / TestNG Test Execution
              |
              v
       Failure Collector
              |
              v
       Failure Context
              |
              v
       JSON Failure Data
              |
              v
       Failure Report Reader
              |
              v
       Simulated Agent Decision Loop
              |
              v
       Java Agent Tools
              |
              v
       QA Analysis Report
```

### 2. Runtime self-healing locator flow

``` text
Page Object action
       |
       v
Try original Selenium locator
       |
       +---- Locator works ----> Perform action
       |
       v
Original locator cannot be used
       |
       v
Inspect current page DOM
       |
       v
Generate alternative locator candidates
       |
       v
Validate candidate locators
       |
       +---- No valid candidate ----> Report failure
       |
       v
Use validated locator
       |
       v
Retry supported browser action
```

The failure-analysis workflow and runtime self-healing workflow are
complementary, but they are not the same process. The current decision
loop is simulated, while the initial self-healing locator selection is
rule-based.

------------------------------------------------------------------------

## AI Agent and Failure Analysis

The failure-analysis foundation collects information from failed test
scenarios and makes it available to Java-based investigation tools.

### Structured failure context

The framework captures information such as:

-   Scenario name
-   Exception type
-   Error message
-   Stack trace
-   Relevant test or scenario details

The collected information is represented as structured JSON and can be
read back into Java objects for further processing.

### Agent tools

The implemented tool work includes:

-   **Failure report reader:** Reads generated failure reports.
-   **Locator inspection tool:** Extracts locator-related information
    from a failure.
-   **Scenario details tool:** Retrieves the complete structured failure
    details for a scenario.
-   **DOM inspection tool:** Captures the current webpage DOM for
    locator analysis.
-   **Locator candidate generator:** Generates possible Selenium
    locators from DOM elements.
-   **Locator validator:** Checks whether a candidate identifies exactly
    one visible and enabled element.

### Simulated decision loop

A simulated decision-maker evaluates the failure context and selects a
tool to execute. It can evaluate the tool result and decide whether
additional information is needed.

This provides a foundation for understanding the agent tool-calling
pattern before connecting a real language model.

**Important:** The current simulated decision-maker is not an LLM
response. The current failure categorization and locator candidate
generation are rule-based.

------------------------------------------------------------------------

## Runtime Self-Healing Locators

The framework now includes an initial runtime self-healing mechanism for
Selenium locators.

When an original locator cannot be used, the framework can inspect the
current DOM, generate alternatives, validate them, and retry a supported
action using a validated locator.

### Main components

  -----------------------------------------------------------------------
  Component                           Responsibility
  ----------------------------------- -----------------------------------
  `DOMInspector`                      Uses JavaScript to capture the
                                      current document HTML

  `LocatorCandidateGenerator`         Parses the DOM with Jsoup and
                                      generates alternative Selenium `By`
                                      candidates

  `LocatorValidator`                  Checks candidate uniqueness,
                                      visibility, and enabled status

  `SelfHealingLocatorTool`            Coordinates DOM inspection,
                                      candidate generation, and
                                      validation

  `HealingWebDriverActions`           Provides reusable self-healing
                                      browser actions

  `HealingResult`                     Holds the original and selected
                                      locator details, validation state,
                                      reason, and confidence information
  -----------------------------------------------------------------------

### Supported browser actions

The current `HealingWebDriverActions` implementation supports:

-   **Click:** Attempts the original locator first; if it cannot be
    used, it invokes the self-healing flow and retries with the
    validated locator.
-   **Type:** Attempts to use the original input locator; if it cannot
    be used, it invokes the self-healing flow, enters the requested text
    with the recovered locator, and returns the input element for a
    subsequent action such as pressing Enter.

### Candidate validation

The current validator checks that a candidate:

1.  Finds exactly one element.
2.  Identifies an element that is displayed.
3.  Identifies an element that is enabled.

These checks help reject ambiguous or unusable candidates. They do not,
by themselves, prove that the candidate represents the intended business
control. Semantic verification and post-action verification are planned
improvements.

------------------------------------------------------------------------

## Self-Healing Workflow

The runtime flow is designed as follows:

1.  A Page Object invokes a reusable action through
    `HealingWebDriverActions`.
2.  The framework attempts to locate and use the element with the
    original locator.
3.  If the original locator cannot be used, the self-healing tool is
    invoked.
4.  `DOMInspector` captures the current document HTML.
5.  `LocatorCandidateGenerator` parses the HTML and creates possible
    locators.
6.  `LocatorValidator` checks the candidates.
7.  If a valid candidate is found, the action is retried using that
    locator.
8.  If no candidate passes validation, the framework reports that
    locator recovery was unsuccessful.

The mechanism does not silently assume that every generated candidate is
correct. Candidate validation is required before retrying the action.

------------------------------------------------------------------------

## Amazon.in Demonstration

A sample scenario was added to demonstrate runtime locator recovery
using the Amazon.in search field.

### Scenario objective

Search for a product using an intentionally incorrect locator and allow
the self-healing flow to identify an alternative locator.

### Example locator details

  Item                                            Value
  ----------------------------------------------- -------------------------------
  Intentionally incorrect locator                 `By.id("incorrectSearchBox")`
  Expected search-field identifier                `twotabsearchtextbox`
  Element type supplied to candidate generation   `input`
  Sample search text                              `Laptop`

### Example Gherkin scenario

``` gherkin
Scenario: Search Amazon using self-healing locator
    Given Launch URL
    When Search for Amazon product "Laptop"
```

### Execution flow

1.  The existing browser launch step opens the configured application
    URL.
2.  The Cucumber step passes the product name to the existing `HomePage`
    object.
3.  `HomePage.searchAmazonProduct()` calls
    `HealingWebDriverActions.type()`.
4.  The intentionally incorrect locator is attempted first.
5.  The DOM is inspected and alternative candidates are generated.
6.  The candidate corresponding to the Amazon search input is validated.
7.  The recovered input receives the search text.
8.  The search is submitted using the returned `WebElement`.

This sample demonstrates the self-healing type flow. The Amazon page
markup and availability can change, so the example should be revalidated
against the live page when it is run.

------------------------------------------------------------------------

## Project Structure

The following is a conceptual view of the main project areas. Exact
package and file names should be checked against the current repository.

``` text
src
├── main
│   └── java
│       └── AI
│           ├── Agent
│           │   ├── Decision
│           │   └── Tools
│           ├── Collector
│           └── Context
│
└── test
    ├── java
    │   ├── BasePackage
    │   ├── Hooks
    │   ├── POM
    │   ├── StepDefinitions
    │   └── TestRunner
    │
    └── resources
        └── Features

target
├── ai-failure-reports
├── ai-analysis-reports
└── cucumber-reports.html
```

Some projects may place AI classes under `src/test/java` rather than
`src/main/java`; use the actual source layout in the repository as the
source of truth.

------------------------------------------------------------------------

## Execution and Reports

The project is a Maven-based Cucumber/TestNG framework.

Typical Maven commands:

``` bash
mvn clean test-compile
```

To execute the configured test suite:

``` bash
mvn test
```

The actual test selection and execution configuration are controlled by
the project's TestNG runner and Cucumber options.

Generated output can include:

-   Cucumber HTML report
-   Scenario screenshots attached by the Cucumber hooks
-   Structured failure-context JSON
-   Consolidated failure information
-   AI-agent QA analysis text reports

Generated files under `target/` are build outputs and may be cleared by
a clean build or by the framework's report-cleanup logic.

------------------------------------------------------------------------

## Implementation Roadmap

  -----------------------------------------------------------------------
  Stage                   Description             Status
  ----------------------- ----------------------- -----------------------
  1                       Failure analysis        Completed
                          foundation: collect     
                          execution failures and  
                          generate reports        

  2                       Structured failure      Completed
                          context: capture        
                          scenario, exception,    
                          message, and stack      
                          trace in JSON           

  3                       Agent tools: read       Completed
                          reports, inspect        
                          locators, and retrieve  
                          scenario details        

  4                       Simulated agent         Completed
                          decision loop: select   
                          and execute tools based 
                          on failure context      

  5                       Real LLM integration    Future phase
                          and model-driven tool   
                          calling                 

  6                       Controlled actions,     Planned
                          reruns, result          
                          comparison, and         
                          approval before         
                          consequential changes   

  7                       Expand to API testing   Future phase
                          and additional QA       
                          workflows               

  ---                     Runtime self-healing    Initial implementation
                          locator: DOM            completed
                          inspection, candidate   
                          generation, validation, 
                          click and type          
  -----------------------------------------------------------------------

------------------------------------------------------------------------

## Current Scope and Limitations

This is an evolving learning and engineering project. The following
limitations are known and should be considered when interpreting the
current behavior:

-   The simulated agent decision loop does not yet use a live LLM.
-   Locator candidate generation is rule-based and depends on the
    attributes present in the captured DOM.
-   The DOM inspector currently captures the current document HTML;
    iframe and shadow DOM handling are not yet covered by the described
    implementation.
-   Candidate validation checks uniqueness, visibility, and enabled
    state, but does not establish semantic equivalence with the original
    control.
-   A successful click or typing operation does not automatically prove
    that the intended application state change occurred.
-   Dynamic pages may render different markup across sessions, regions,
    or page states.
-   The Amazon.in example depends on the live page retaining a
    compatible search input.
-   Self-healing should not be treated as a substitute for meaningful
    assertions, test design, or QA review.

------------------------------------------------------------------------

## Future Enhancements

Potential next steps include:

-   Add post-action verification to confirm that the intended UI
    behavior occurred.
-   Improve candidate ranking using element context and semantic
    attributes.
-   Add safer handling for transient interaction errors, stale elements,
    and intercepted clicks.
-   Add iframe and shadow DOM support where required.
-   Record healing history and expose it in test reports.
-   Add approval-controlled scenario reruns and result comparisons.
-   Integrate a real LLM for model-driven tool selection and reasoning
    when ready.
-   Extend the agent tools to API testing and broader QA workflows.

------------------------------------------------------------------------

## Project Philosophy

The objective is not to replace traditional automation, but to build on
it.

A reliable automation foundation remains essential. Agent-oriented
capabilities can add structured investigation, tool-based reasoning, and
controlled recovery around that foundation.

This repository documents the incremental development of those
capabilities, from traditional Selenium automation toward an AI-assisted
testing framework.
