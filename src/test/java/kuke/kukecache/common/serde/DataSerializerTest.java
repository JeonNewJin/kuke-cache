package kuke.kukecache.common.serde;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DataSerializerTest {
    @Test
    void serde() {
        MyData myData = new MyData("id", "data");
        String serialized = DataSerializer.serializeOrException(myData);

        MyData deserialized = DataSerializer.deserializeOrException(serialized, MyData.class);
        Assertions.assertThat(deserialized).isEqualTo(myData);
    }

    record MyData(
            String id, String data
    ) {

    }
}