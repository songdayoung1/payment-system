package kr.co.store.api.store.domain.common;

public enum YNType implements IEnumtype {
    N("미사용"),
    Y("사용");
    private final String value;

    YNType(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
