package com.tms.v1.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tms.v1.web.rest.TestUtil;

public class TransactionsRecordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionsRecord.class);
        TransactionsRecord transactionsRecord1 = new TransactionsRecord();
        transactionsRecord1.setId(1L);
        TransactionsRecord transactionsRecord2 = new TransactionsRecord();
        transactionsRecord2.setId(transactionsRecord1.getId());
        assertThat(transactionsRecord1).isEqualTo(transactionsRecord2);
        transactionsRecord2.setId(2L);
        assertThat(transactionsRecord1).isNotEqualTo(transactionsRecord2);
        transactionsRecord1.setId(null);
        assertThat(transactionsRecord1).isNotEqualTo(transactionsRecord2);
    }
}
