package com.eventhub.service.impl;

import com.eventhub.dto.ai.FaqEntryResponse;
import com.eventhub.dto.ai.FaqSearchResponse;
import com.eventhub.model.FaqEntry;
import com.eventhub.service.FaqVectorService;
import com.eventhub.util.PromptSanitizer;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FaqVectorServiceImpl implements FaqVectorService {

    private final PromptSanitizer promptSanitizer;
    private final VectorStore vectorStore;

    private boolean loaded = false;

    public FaqVectorServiceImpl(EmbeddingModel embeddingModel, PromptSanitizer promptSanitizer) {
        this.promptSanitizer = promptSanitizer;
        this.vectorStore = SimpleVectorStore.builder(embeddingModel).build();
    }

    @Override
    public int loadDefaultFaqs() {
        List<FaqEntry> faqEntries = buildDefaultFaqEntries();
        List<Document> documents = new ArrayList<>();

        for (FaqEntry entry : faqEntries) {
            Map<String, Object> metadata = new LinkedHashMap<>();
            metadata.put("id", entry.getId());
            metadata.put("category", entry.getCategory());
            metadata.put("question", entry.getQuestion());
            metadata.put("answer", entry.getAnswer());

            String text = """
                    FAQ ID: %s
                    Category: %s
                    Question: %s
                    Answer: %s
                    """.formatted(
                    entry.getId(),
                    entry.getCategory(),
                    entry.getQuestion(),
                    entry.getAnswer()
            );

            documents.add(new Document(entry.getId(), text, metadata));
        }

        vectorStore.add(documents);
        loaded = true;

        return faqEntries.size();
    }

    @Override
    public FaqSearchResponse searchFaqs(String query, int topK, double minSimilarity) {
        if (!loaded) {
            int count = loadDefaultFaqs();
            if (count == 0) {
                return new FaqSearchResponse("No FAQ entries are available.", List.of());
            }
        }

        List<FaqEntryResponse> results = findRelevantFaqs(query, topK, minSimilarity);

        if (results.isEmpty()) {
            return new FaqSearchResponse(
                    "No FAQ entries matched your query closely enough. Please contact support for more help.",
                    List.of()
            );
        }

        return new FaqSearchResponse("Relevant FAQ entries found.", results);
    }

    @Override
    public List<FaqEntryResponse> findRelevantFaqs(String query, int topK, double minSimilarity) {
        if (!loaded) {
            loadDefaultFaqs();
        }

        String sanitizedQuery = promptSanitizer.sanitize(query);

        SearchRequest searchRequest = SearchRequest.builder()
                .query(sanitizedQuery)
                .topK(topK)
                .similarityThreshold(minSimilarity)
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        List<FaqEntryResponse> results = new ArrayList<>();

        if (documents.isEmpty()) {
            return results;
        }

        for (Document document : documents) {
            Map<String, Object> metadata = document.getMetadata();
            results.add(new FaqEntryResponse(
                    asString(metadata.get("id")),
                    asString(metadata.get("category")),
                    asString(metadata.get("question")),
                    asString(metadata.get("answer")),
                    document.getScore()
            ));
        }

        return results;
    }

    private List<FaqEntry> buildDefaultFaqEntries() {
        List<FaqEntry> entries = new ArrayList<>();

        entries.add(new FaqEntry(
                "faq-001",
                "Registration",
                "How do I register for an event?",
                "You can register by opening the event details page and selecting the registration or ticket option before checkout."
        ));

        entries.add(new FaqEntry(
                "faq-002",
                "Ticketing",
                "Will I receive a ticket confirmation after registering?",
                "Yes. After a successful registration, a confirmation will be sent and your ticket details will appear in your account."
        ));

        entries.add(new FaqEntry(
                "faq-003",
                "Refunds",
                "What is the refund and cancellation policy?",
                "Refund eligibility depends on the event organizer's policy. Check the event page for deadlines and cancellation terms before requesting a refund."
        ));

        entries.add(new FaqEntry(
                "faq-004",
                "Venue",
                "Is parking available at the venue?",
                "Parking availability depends on the venue. Event pages should list on-site parking details, nearby lots, or public transit guidance when available."
        ));

        entries.add(new FaqEntry(
                "faq-005",
                "Accessibility",
                "Are events accessible for guests with mobility needs?",
                "Many venues offer accessibility features such as ramps, elevators, or accessible seating. Check the event details or contact support for venue-specific information."
        ));

        entries.add(new FaqEntry(
                "faq-006",
                "Schedule",
                "Where can I find the event schedule or agenda?",
                "The latest schedule or agenda is typically posted on the event details page. Some events may update session times closer to the event date."
        ));

        entries.add(new FaqEntry(
                "faq-007",
                "Dress Code",
                "Is there a dress code or recommended attire?",
                "Dress code requirements vary by event. Review the event description for recommendations such as casual, business, or formal attire."
        ));

        entries.add(new FaqEntry(
                "faq-008",
                "What To Bring",
                "What should I bring to the event?",
                "Bring your ticket or registration confirmation and any event-specific items listed on the event page, such as identification, notebooks, or required materials."
        ));

        entries.add(new FaqEntry(
                "faq-009",
                "Support",
                "How can I contact support if I have an issue?",
                "You can contact support through the platform's help or contact page for assistance with registration, ticketing, or event information."
        ));

        entries.add(new FaqEntry(
                "faq-010",
                "Food and Beverage",
                "Will food and drinks be available?",
                "Some events provide food or beverage options, while others do not. Check the event page for catering, concession, or vendor information."
        ));

        entries.add(new FaqEntry(
                "faq-011",
                "Age Restrictions",
                "Are there age restrictions for events?",
                "Some events have minimum age requirements. Review the event details carefully to confirm whether the event is all-ages, adult-only, or family-friendly."
        ));

        entries.add(new FaqEntry(
                "faq-012",
                "Photography",
                "Can I take photos or record video at the event?",
                "Photography and recording policies vary by organizer and venue. Review the event page or posted venue rules before recording."
        ));

        return entries;
    }

    private String asString(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}