package csci310.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDateUpdateForm extends Form {
    private final boolean live;
    private final boolean finalized;

    @JsonCreator
    public GroupDateUpdateForm(
            @JsonProperty(value = "live", required = true) boolean live,
            @JsonProperty(value = "finalized", required = true) boolean finalized
    ) {
        this.live = live;
        this.finalized = finalized;
    }

    public boolean getLive() {
        return live;
    }

    public boolean getFinalized() {
        return finalized;
    }
}
