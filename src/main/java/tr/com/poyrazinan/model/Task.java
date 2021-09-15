package tr.com.poyrazinan.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class Task {

    // Task name
    private String name;

    // Contact phone number with
    // country code. Example: 905555555 (without +)
    private List<String> numbers;

    private String message;

    private Date date;

    // Is task executed boolean.
    @Builder.Default
    private boolean executed = false;

    // Its everyone annotation for sending message to everyone
    @Builder.Default
    private boolean everyone = false;

    public float getUnix() {
        return date.getTime() / 1000L;
    }

}
