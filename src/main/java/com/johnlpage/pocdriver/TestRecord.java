package com.johnlpage.pocdriver;

import org.bson.Document;

import java.util.Collection;

public interface TestRecord {

    Collection<String> listFields();

    Document getDocument();
}
