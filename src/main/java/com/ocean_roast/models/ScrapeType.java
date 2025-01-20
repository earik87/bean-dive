package com.ocean_roast.models;

public enum ScrapeType {
    HTML,       // e.g., standard JSoup selectors
    JSON_LD, // e.g., parse <script type="application/ld+json">
    JSON_LD_AND_HTML, // e.g., parse both JSON-LD and HTML
    INLINE_JSON, // e.g., parse a <script> var meta = {...}
    PARTIAL_CLASS_MATCH, // e.g., parse a <div class="product"> with a <span class="price">
    PRODUCT_CARD, // e.g., parse a <div class="product-card"> with a <span class="price">
    SELECTOR_BASED // e.g., parse a <div class="product"> with a <span class="price">
}