package com.tms.v1.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tms.v1.web.rest.TestUtil;

public class InvoiceReportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceReport.class);
        InvoiceReport invoiceReport1 = new InvoiceReport();
        invoiceReport1.setId(1L);
        InvoiceReport invoiceReport2 = new InvoiceReport();
        invoiceReport2.setId(invoiceReport1.getId());
        assertThat(invoiceReport1).isEqualTo(invoiceReport2);
        invoiceReport2.setId(2L);
        assertThat(invoiceReport1).isNotEqualTo(invoiceReport2);
        invoiceReport1.setId(null);
        assertThat(invoiceReport1).isNotEqualTo(invoiceReport2);
    }
}
