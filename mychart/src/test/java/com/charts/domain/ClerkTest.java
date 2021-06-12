package com.charts.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.charts.web.rest.TestUtil;

public class ClerkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clerk.class);
        Clerk clerk1 = new Clerk();
        clerk1.setId("id1");
        Clerk clerk2 = new Clerk();
        clerk2.setId(clerk1.getId());
        assertThat(clerk1).isEqualTo(clerk2);
        clerk2.setId("id2");
        assertThat(clerk1).isNotEqualTo(clerk2);
        clerk1.setId(null);
        assertThat(clerk1).isNotEqualTo(clerk2);
    }
}
