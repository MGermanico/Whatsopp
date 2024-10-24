import java.io.Serial;
import java.io.Serializable;

public class Mensaje implements Serializable {
    private String message;
    @Serial
    private static final long serialVersionUID = -616102903707371431L;

    public Mensaje(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "message='" + message + '\'' +
                '}';
    }


}
