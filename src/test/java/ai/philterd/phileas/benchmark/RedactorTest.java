package ai.philterd.phileas.benchmark;

import ai.philterd.phileas.model.responses.FilterResponse;
import org.junit.Test;

import static com.mscharhag.oleaster.matcher.Matchers.expect;

/**
 * Functional tests for redactor implementations.
 */
public class RedactorTest {

    @Test
    public void maskAllTest() throws Exception {
        Redactor r = new Redactor("mask_all");
        FilterResponse fr = r.filter("the payment method is 4532613702852251 visa from user rik@resurfacd.io.");
        expect(fr.filteredText()).toEqual("the payment method is **************** visa from user ****************.");

        expect(fr.explanation().identifiedSpans().size()).toEqual(2);
        expect(fr.explanation().identifiedSpans().get(0).getFilterType().toString()).toEqual("email-address");
        expect(fr.explanation().identifiedSpans().get(0).getText()).toEqual("rik@resurfacd.io");
        expect(fr.explanation().identifiedSpans().get(1).getFilterType().toString()).toEqual("credit-card");
        expect(fr.explanation().identifiedSpans().get(1).getText()).toEqual("4532613702852251");
    }

    @Test
    public void maskCreditCardsTest() throws Exception {
        Redactor r = new Redactor("mask_credit_cards");
        FilterResponse fr = r.filter("the payment method is 4532613702852251 visa from user rik@resurfacd.io.");
        expect(fr.filteredText()).toEqual("the payment method is **************** visa from user rik@resurfacd.io.");

        expect(fr.explanation().identifiedSpans().size()).toEqual(1);
        expect(fr.explanation().identifiedSpans().get(0).getFilterType().toString()).toEqual("credit-card");
        expect(fr.explanation().identifiedSpans().get(0).getText()).toEqual("4532613702852251");
    }

    @Test
    public void maskEmailAddressesTest() throws Exception {
        Redactor r = new Redactor("mask_email_addresses");
        FilterResponse fr = r.filter("the payment method is 4532613702852251 visa from user rik@resurfacd.io.");
        expect(fr.filteredText()).toEqual("the payment method is 4532613702852251 visa from user ****************.");

        expect(fr.explanation().identifiedSpans().size()).toEqual(1);
        expect(fr.explanation().identifiedSpans().get(0).getFilterType().toString()).toEqual("email-address");
        expect(fr.explanation().identifiedSpans().get(0).getText()).toEqual("rik@resurfacd.io");
    }

    @Test
    public void skipAllTest() throws Exception {
        Redactor r = new Redactor("skip_all");
        String value = "the quick fox jumped over the lazy dog";
        FilterResponse fr = r.filter(value);
        expect(fr.filteredText()).toEqual(value);
        expect(fr.explanation().identifiedSpans().size()).toEqual(0);
    }

}
