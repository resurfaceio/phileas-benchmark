package ai.philterd.phileas.benchmark;

import ai.philterd.phileas.model.configuration.PhileasConfiguration;
import ai.philterd.phileas.model.enums.MimeType;
import ai.philterd.phileas.model.policy.Identifiers;
import ai.philterd.phileas.model.policy.Policy;
import ai.philterd.phileas.model.policy.filters.CreditCard;
import ai.philterd.phileas.model.policy.filters.EmailAddress;
import ai.philterd.phileas.model.policy.filters.strategies.rules.CreditCardFilterStrategy;
import ai.philterd.phileas.model.policy.filters.strategies.rules.EmailAddressFilterStrategy;
import ai.philterd.phileas.model.responses.FilterResponse;
import ai.philterd.phileas.services.PhileasFilterService;

import java.util.List;
import java.util.Properties;

/**
 * Single-threaded redactor using Phileas PII engine.
 */
public class Redactor {

    public Redactor(String name) throws Exception {
        boolean all = "mask_all".equals(name);
        boolean valid = "skip_all".equals(name);
        Identifiers identifiers = new Identifiers();

        if (all || "mask_credit_cards".equals(name)) {
            CreditCardFilterStrategy ccfs = new CreditCardFilterStrategy();
            ccfs.setStrategy("MASK");
            ccfs.setMaskCharacter("*");
            ccfs.setMaskLength("same");
            CreditCard cc = new CreditCard();
            cc.setCreditCardFilterStrategies(List.of(ccfs));
            identifiers.setCreditCard(cc);
            valid = true;
        }

        if (all || "mask_email_addresses".equals(name)) {
            EmailAddressFilterStrategy eafs = new EmailAddressFilterStrategy();
            eafs.setStrategy("MASK");
            eafs.setMaskCharacter("*");
            eafs.setMaskLength("same");
            EmailAddress ea = new EmailAddress();
            ea.setEmailAddressFilterStrategies(List.of(eafs));
            identifiers.setEmailAddress(ea);
            valid = true;
        }

        // todo add other identifiers

        // quit if name parameter didn't match
        if (!valid) throw new IllegalArgumentException("Invalid redactor: " + name);

        // create filter service
        this.policy = new Policy();
        policy.setName("default");
        policy.setIdentifiers(identifiers);
        Properties properties = new Properties();
        PhileasConfiguration configuration = new PhileasConfiguration(properties, "phileas");
        this.filterService = new PhileasFilterService(configuration);
    }

    private final PhileasFilterService filterService;
    private final Policy policy;

    public FilterResponse filter(String s) throws Exception {
        return filterService.filter(policy, "context", "documentid", s, MimeType.TEXT_PLAIN);
    }

}
