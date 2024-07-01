package hanghae99.JJimGGongMall.common.util;

import hanghae99.JJimGGongMall.common.BaseEntity;

import java.time.LocalDateTime;

public interface DateTimeHolder {

    LocalDateTime getTimeNow();

    LocalDateTime getCreateTime(BaseEntity entity);

    LocalDateTime getUpdateTime(BaseEntity entity);

    LocalDateTime getDeleteTime(BaseEntity entity);
}
