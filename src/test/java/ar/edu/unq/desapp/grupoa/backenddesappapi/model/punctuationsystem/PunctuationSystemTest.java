package ar.edu.unq.desapp.grupoa.backenddesappapi.model.punctuationsystem;

import ar.edu.unq.desapp.grupoa.backenddesappapi.model.interfaces.IRule;
import ar.edu.unq.desapp.grupoa.backenddesappapi.model.proyect.Donation;
import ar.edu.unq.desapp.grupoa.backenddesappapi.model.punctuationsystem.rule.InvertedCash;
import ar.edu.unq.desapp.grupoa.backenddesappapi.model.punctuationsystem.rule.InvertedLocality;
import ar.edu.unq.desapp.grupoa.backenddesappapi.model.punctuationsystem.rule.TimesInTheMonth;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PunctuationSystemTest {

    private PunctuationSystem punctuationSystem;
    private List<IRule> listOfRules = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    private Donation aDonation = mock(Donation.class);
    private Donation otherDonation = mock(Donation.class);

    @BeforeEach
    void setUp() {
        punctuationSystem = new PunctuationSystem(listOfRules, productList);
    }

    @AfterEach
    void tearDown() {
        punctuationSystem = null;
    }

    @Test
    public void test01WhenAPunctuationSystemReceivesTheMessageGetRulesRespondsWithItsListOfRules(){
        assertEquals(punctuationSystem.getRules(), listOfRules);
    }

    @Test
    public void test02WhenAPunctuationSystemReceivesTheMessageSetRulesChangesItListOfRules(){
        List<IRule> newListOfRules = new ArrayList<>();

        punctuationSystem.setRules(newListOfRules);

        assertEquals(punctuationSystem.getRules(), newListOfRules);
    }

    @Test
    public void test03WhenAPunctuationSystemReceivesTheMessageAddRuleThatIsInvertedCashItAddsTheRuleToTheListOfRules(){
        InvertedCash newRule = mock(InvertedCash.class);
        punctuationSystem.addRule(newRule);

        assertEquals(punctuationSystem.getRules().get(0), newRule);
    }

    @Test
    public void test04WhenAPunctuationSystemReceivesTheMessageAddRuleThatIsInvertedLocalityItAddsTheRuleToTheListOfRules(){
        InvertedLocality newRule = mock(InvertedLocality.class);
        punctuationSystem.addRule(newRule);

        assertEquals(punctuationSystem.getRules().get(0), newRule);
    }

    @Test
    public void test05WhenAPunctuationSystemReceivesTheMessageAddRuleThatIsTimeInMonthItAddsTheRuleToTheListOfRules(){
        TimesInTheMonth newRule = mock(TimesInTheMonth.class);
        punctuationSystem.addRule(newRule);

        assertEquals(punctuationSystem.getRules().get(0), newRule);
    }

    @Test
    public void test06WhenAPunctuationSystemReceivesTheMessageGetProductsRespondsWithItsListOfProducts(){
        assertEquals(punctuationSystem.getProducts(), productList);
    }

    @Test
    public void test07WhenAPunctuationSystemReceivesTheMessageAddProductsItAddTheProductToHisListOfProducts(){
        Product newProduct = mock(Product.class);
        punctuationSystem.addProduct(newProduct);

        assertEquals(punctuationSystem.getProducts().get(0), newProduct);
    }

    @Test
    public void test08WhenAPunctuationSystemReceivesTheMessageSetProductListHisListOfProductsChange(){
        List<Product> newListOfProduct = new ArrayList<>();

        punctuationSystem.setProductList(newListOfProduct);

        assertEquals(punctuationSystem.getProducts(), newListOfProduct);
    }

    @Test
    public void test09WhenAPunctuationSystemReceivesTheMessageDeleteRuleHeRemovesTheRulePassAsParameter(){
        TimesInTheMonth newRule = mock(TimesInTheMonth.class);
        punctuationSystem.addRule(newRule);

        assertEquals(punctuationSystem.getRules().get(0), newRule);

        punctuationSystem.deleteRule(newRule);

        assertTrue(punctuationSystem.getRules().isEmpty());
    }

    @Test
    public void test10WhenAPunctuationSystemReceivesTheMessageDeleteProductHeRemovesTheProductPassAsParameter(){
        Product newProduct = mock(Product.class);
        punctuationSystem.addProduct(newProduct);

        assertEquals(punctuationSystem.getProducts().get(0), newProduct);

        punctuationSystem.deleteProduct(newProduct);

        assertTrue(punctuationSystem.getProducts().isEmpty());
    }

    @Test
    public void test11WhenAPunctuationSystemReceivesTheMessageRulesApplicableToDonationHisRespondsWithAListOfRulesApplicableToTheDonation(){
        InvertedCash forCash = mock(InvertedCash.class);
        InvertedLocality forLocality = mock(InvertedLocality.class);

        when(forCash.isApplicable(aDonation)).thenReturn(true);
        when(forLocality.isApplicable(aDonation)).thenReturn(true);

        punctuationSystem.addRule(forCash);
        punctuationSystem.addRule(forLocality);

        assertEquals(punctuationSystem.rulesApplicableToDonation(aDonation).size(), 2);
    }

    @Test
    public void test12WhenAPunctuationSystemReceivesTheMessageRulesApplicableToDonationHisRespondsWithAListOfRulesApplicableToTheDonation(){
        InvertedCash forCash = mock(InvertedCash.class);
        InvertedLocality forLocality = mock(InvertedLocality.class);

        when(forCash.isApplicable(aDonation)).thenReturn(false);
        when(forLocality.isApplicable(aDonation)).thenReturn(false);

        punctuationSystem.addRule(forCash);
        punctuationSystem.addRule(forLocality);

        assertTrue(punctuationSystem.rulesApplicableToDonation(aDonation).isEmpty());
    }

    @Test
    public void test13WhenAPunctuationSystemReceivesTheMessagePointsGainForListOfDonationHisRespondsWithAAmountOfPointsGainedFrom(){
        InvertedCash forCash = mock(InvertedCash.class);
        InvertedLocality forLocality = mock(InvertedLocality.class);

        when(forCash.pointsForDonation(aDonation)).thenReturn(2000.0);
        when(forLocality.pointsForDonation(aDonation)).thenReturn(2000.0);

        when(forCash.isApplicable(aDonation)).thenReturn(true);
        when(forLocality.isApplicable(aDonation)).thenReturn(true);

        punctuationSystem.addRule(forCash);
        punctuationSystem.addRule(forLocality);

        assertEquals(punctuationSystem.pointsGainForDonation(aDonation), 4000.0);
    }

    @Test
    public void test14WhenAPunctuationSystemReceivesTheMessageamountOfPointsForDonationsHisRespondsIsTheAmountOfPoints(){
        InvertedCash forCash = mock(InvertedCash.class);
        InvertedLocality forLocality = mock(InvertedLocality.class);

        when(forCash.pointsForDonation(aDonation)).thenReturn(2000.0);
        when(forLocality.pointsForDonation(aDonation)).thenReturn(2000.0);

        when(forCash.isApplicable(aDonation)).thenReturn(true);
        when(forLocality.isApplicable(aDonation)).thenReturn(true);

        when(forCash.isApplicable(otherDonation)).thenReturn(true);

        when(forCash.pointsForDonation(otherDonation)).thenReturn(1000.0);

        punctuationSystem.addRule(forCash);
        punctuationSystem.addRule(forLocality);

        List <Donation> donations = new ArrayList<>();
        donations.add(aDonation);
        donations.add(otherDonation);

        assertEquals(5000.0, punctuationSystem.amountOfPointsForDonations(donations));
    }

}