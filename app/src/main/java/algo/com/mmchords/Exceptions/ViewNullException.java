package algo.com.mmchords.Exceptions;

import androidx.annotation.Nullable;

public class ViewNullException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "View cannot be null";
    }
}
