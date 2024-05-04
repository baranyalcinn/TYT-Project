package tyt.record.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponse {
    private String message;
    private HttpStatus statusCode;

    public RecordResponse(String message) {
        this.message = message;
    }

}
