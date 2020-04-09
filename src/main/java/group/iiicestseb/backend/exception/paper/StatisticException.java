package group.iiicestseb.backend.exception.paper;

import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/4/5
 */
@NoArgsConstructor
public class StatisticException extends RuntimeException {

    public StatisticException(String msg) {
        super(msg);
    }
}
