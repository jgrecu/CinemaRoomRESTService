package cinema.dtos;

import javax.validation.constraints.Pattern;
import java.util.UUID;

public class TockenRequest {
    // @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-4[a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}")
    private final UUID token;

    public TockenRequest(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

}
