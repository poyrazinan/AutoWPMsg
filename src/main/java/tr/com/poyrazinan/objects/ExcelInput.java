package tr.com.poyrazinan.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ExcelInput {

    // Contact name
    private String name;

    // Contact phone number with
    // country code. Example: 905555555 (without +)
    private String number;

    // Message which will send it to contact.
    private String Message;

    // Date which is schedule time.
    private Date date;

    // Is task executed boolean.
    @Builder.Default
    private boolean executed = false;

    // Its everyone annotion for sending message to everyone
    @Builder.Default
    private boolean everyone = false;

}
