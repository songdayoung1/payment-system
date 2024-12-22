package kr.co.store.api.store.domain.common;

import java.util.Optional;
import java.util.Set;

public interface IEnumtype {
    String getKey();

    String getValue();

    default Set<IEnumtype> getIgnoreEnum() {
        return null;
    }

    default boolean isIgnoreEnum() {
        return Optional.ofNullable(this.getIgnoreEnum())
                .map(ignoreEnum -> ignoreEnum.contains(this))
                .orElse(false);
    }
}
