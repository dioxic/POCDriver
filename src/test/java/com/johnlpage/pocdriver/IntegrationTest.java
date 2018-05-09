package com.johnlpage.pocdriver;

import org.apache.commons.cli.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.text.Document;

import static org.junit.Assert.fail;

public class IntegrationTest {

    private final static int THREADS = 5;
    private final static int DURATION = 20;

    @Ignore
    @Test
    public void runTemplateTest() {
        String templateFile = "src/test/resources/template.json";
        runGenericTest(String.format("-i50 -u50 -k50 -r50 -t%d -e -nPOCDB.template -d%d --template %s", THREADS, DURATION, templateFile));
    }

    @Ignore
    @Test
    public void runBasicTest() {
        runGenericTest(String.format("-i50 -u50 -k50 -r50 -t%d -e -nPOCDB.basic -d%d", THREADS, DURATION));
    }

    private void runGenericTest(String args) {
        try {
            POCTestOptions testOpts = new POCTestOptions(args.split(" "));

            POCTestResults testResults = new POCTestResults();
            LoadRunner runner = new LoadRunner(testOpts);
            runner.RunLoad(testOpts, testResults);

        } catch (ParseException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
