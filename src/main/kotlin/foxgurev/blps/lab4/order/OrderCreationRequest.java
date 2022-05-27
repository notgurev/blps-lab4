package foxgurev.blps.lab4.order;

import lombok.Data;

import java.util.List;

@Data
@Deprecated
public class OrderCreationRequest {
    List<Long> products; // product ids
    String promocode;
    String name;
    String surname;
    String phoneNumber;
    String email;
    String city;
}
