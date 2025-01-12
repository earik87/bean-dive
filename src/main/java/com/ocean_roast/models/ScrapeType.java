package com.ocean_roast.models;

public enum ScrapeType {
    HTML,       // e.g., standard JSoup selectors
    JSON_LD,    // e.g., parse <script type="application/ld+json">
    INLINE_JSON, // e.g., parse a <script> var meta = {...}
}