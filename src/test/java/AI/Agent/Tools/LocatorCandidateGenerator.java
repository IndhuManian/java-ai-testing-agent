
package AI.Agent.Tools;

import org.openqa.selenium.By;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LocatorCandidateGenerator {

    public List<By> generateCandidates(
            String pageDOM,
            String expectedText,
            String tagName) {

        System.out.println(
                "Locator Generator: Generating candidates...");

        Set<By> candidates = new LinkedHashSet<>();

        Document document = Jsoup.parse(pageDOM);

        Elements elements = document.getAllElements();
        System.out.println("---- DOM MATCH DIAGNOSTICS ----");

        int matchCount = 0;

        for (Element element : elements) {

            String text = element.ownText().trim();
            String ariaLabel = element.attr("aria-label");
            String value = element.attr("value");
            String id = element.id();
            String name = element.attr("name");

            boolean matches =
                    text.toLowerCase().contains(expectedText.toLowerCase()) ||
                            ariaLabel.toLowerCase().contains(expectedText.toLowerCase()) ||
                            value.toLowerCase().contains(expectedText.toLowerCase()) ||
                            id.toLowerCase().contains(expectedText.toLowerCase()) ||
                            name.toLowerCase().contains(expectedText.toLowerCase());

            if (matches && matchCount < 20) {

                System.out.println("Tag: " + element.tagName());
                System.out.println("Text: " + text);
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Aria-label: " + ariaLabel);
                System.out.println("Value: " + value);
                System.out.println("HTML: " + element.outerHtml());
                System.out.println("-----------------------------");

                matchCount++;
            }
        }

        System.out.println("Total diagnostic matches: " + matchCount);
        System.out.println("---- END DOM DIAGNOSTICS ----");
        for (Element element : elements) {

            // Match expected element type
            if (tagName != null &&
                    !tagName.isEmpty() &&
                    !element.tagName().equalsIgnoreCase(tagName)) {
                continue;
            }

            // Read element attributes
            String text = element.text().trim();
            String id = element.id();
            String name = element.attr("name");
            String testId = element.attr("data-testid");
            String ariaLabel = element.attr("aria-label");
            String placeholder = element.attr("placeholder");
            String value = element.attr("value");

            // Match expected text or relevant attributes
            if (expectedText != null &&
                    !expectedText.isEmpty()) {

                boolean matches =
                        text.equalsIgnoreCase(expectedText) ||
                                id.equalsIgnoreCase(expectedText) ||
                                name.equalsIgnoreCase(expectedText) ||
                                testId.equalsIgnoreCase(expectedText) ||
                                ariaLabel.equalsIgnoreCase(expectedText) ||
                                placeholder.equalsIgnoreCase(expectedText) ||
                                value.equalsIgnoreCase(expectedText);

                if (!matches) {
                    continue;
                }
            }

            // Generate ID locator
            if (!id.isEmpty()) {
                candidates.add(By.id(id));
            }

            // Generate name locator
            if (!name.isEmpty()) {
                candidates.add(By.name(name));
            }

            // Generate data-testid locator
            if (!testId.isEmpty()) {
                candidates.add(By.cssSelector(
                        "[data-testid='" + testId + "']"));
            }

            // Generate aria-label locator
            if (!ariaLabel.isEmpty()) {
                candidates.add(By.cssSelector(
                        "[aria-label='" + ariaLabel + "']"));
            }

            // Generate placeholder locator
            if (!placeholder.isEmpty()) {
                candidates.add(By.cssSelector(
                        "[placeholder='" + placeholder + "']"));
            }

            // Generate value locator
            if (!value.isEmpty()) {
                candidates.add(By.cssSelector(
                        "[value='" + value + "']"));
            }
        }

        List<By> candidateList =
                new ArrayList<>(candidates);

        System.out.println(
                "Generated candidates: " + candidateList.size());

        return candidateList;
    }
}
