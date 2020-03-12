package com.tms.v1.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tms.v1.web.rest.TestUtil;

public class AccountsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accounts.class);
        Accounts accounts1 = new Accounts();
        accounts1.setId(1L);
        Accounts accounts2 = new Accounts();
        accounts2.setId(accounts1.getId());
        assertThat(accounts1).isEqualTo(accounts2);
        accounts2.setId(2L);
        assertThat(accounts1).isNotEqualTo(accounts2);
        accounts1.setId(null);
        assertThat(accounts1).isNotEqualTo(accounts2);
    }
}
