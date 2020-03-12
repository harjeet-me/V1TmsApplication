package com.tms.v1.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tms.v1.web.rest.TestUtil;

public class FileSystemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileSystem.class);
        FileSystem fileSystem1 = new FileSystem();
        fileSystem1.setId(1L);
        FileSystem fileSystem2 = new FileSystem();
        fileSystem2.setId(fileSystem1.getId());
        assertThat(fileSystem1).isEqualTo(fileSystem2);
        fileSystem2.setId(2L);
        assertThat(fileSystem1).isNotEqualTo(fileSystem2);
        fileSystem1.setId(null);
        assertThat(fileSystem1).isNotEqualTo(fileSystem2);
    }
}
