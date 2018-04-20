package com.johnlpage.pocdriver;

import org.bson.Document;
import uk.dioxic.faker.resolvable.Resolvable;
import uk.dioxic.mgenerate.DocumentValueCache;
import uk.dioxic.mgenerate.operator.IncBuilder;
import uk.dioxic.mgenerate.util.BsonUtil;

import java.io.IOException;
import java.util.Collection;

public class TemplateTestRecord implements TestRecord {

    private Document doc;
    private int workerId;

    public TemplateTestRecord(String templateFile, int workerId, int highestId) {
        try {
            this.workerId = workerId;
            doc = BsonUtil.parseFile(templateFile);
            doc.put("_id", new PocIdResolvable(highestId));
            DocumentValueCache.mapDocument(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<String> listFields() {
        return BsonUtil.flatMap(doc).keySet();
    }

    @Override
    public Document getDocument() {
        return doc;
    }

    class PocIdResolvable implements Resolvable<Document> {
        private Resolvable<Integer> sequence;

        public PocIdResolvable(int startingId) {
            sequence = new IncBuilder().start(startingId).build();
        }

        @Override
        public Document resolve() {
            Document idDoc = new Document();
            idDoc.put("w", workerId);
            idDoc.put("i", sequence.resolve());
            return idDoc;
        }
    }
}
