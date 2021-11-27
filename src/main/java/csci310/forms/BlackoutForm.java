package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlackoutForm extends Form {
    private final long start;
    private final long end;

    @JsonCreator
    public BlackoutForm(
            @JsonProperty(value = "start", required = true) long start,
            @JsonProperty(value = "end", required = true) long end
    ) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
