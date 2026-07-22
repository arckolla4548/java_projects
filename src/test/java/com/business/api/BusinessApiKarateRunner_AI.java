package com.business.api;

import com.intuit.karate.junit5.Karate;

class BusinessApiKarateRunner_AI {

    @Karate.Test
    Karate runBusinessControllerApiTests() {
        return Karate.run("classpath:features/com/business/controllers")
                .relativeTo(getClass());
    }
}
