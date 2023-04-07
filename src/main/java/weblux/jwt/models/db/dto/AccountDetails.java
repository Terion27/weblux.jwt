package weblux.jwt.models.db.dto;

import java.io.Serializable;
import java.time.LocalDate;

public interface AccountDetails extends Serializable {

    Long getUserId();

    LocalDate getStartDate();

    boolean isEnabled();

    boolean isVisibled();
}
